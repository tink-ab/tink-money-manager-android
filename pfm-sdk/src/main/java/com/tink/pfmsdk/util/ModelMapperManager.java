package com.tink.pfmsdk.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tink.pfmsdk.TimezoneManager;
import com.tink.pfmsdk.charts.models.PeriodBalance;
import com.tink.pfmsdk.collections.Currencies;
import com.tink.pfmsdk.collections.StatisticsToMap;
import com.tink.pfmsdk.configuration.SuitableLocaleFinder;
import com.tink.pfmsdk.mapper.ModelConverterImplementation;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.joda.time.DateTime;
import se.tink.converter.ModelConverter;
import se.tink.core.models.misc.Amount;
import se.tink.core.models.misc.ExactNumber;
import se.tink.core.models.misc.Period;
import se.tink.core.models.statistic.Statistic;
import se.tink.utils.DateUtils;

public class ModelMapperManager implements ModelConverter {

	public <KR, VR, KI, VI> Map<KR, VR> map(Map<KI, VI> source, Class<KR> destinationKeyType,
		Class<VR> destinationValueType) {
		Map<KR, VR> returnMap = Maps.newHashMap();
		Set<KI> keySet = source.keySet();
		for (KI key : keySet) {
			VI value = source.get(key);
			returnMap.put(map(key, destinationKeyType), map(value, destinationValueType));
		}
		return returnMap;
	}

	@Override
	public <S, D> List<D> map(Collection<S> source, Class<D> destinationType) {
		ArrayList<D> destinationList = new ArrayList<>();
		for (S sourceObject : source) {
			destinationList.add(map(sourceObject, destinationType));
		}
		return destinationList;
	}

	@Override
	public <S, D> D map(S source, Class<D> destinationType) {
		return mapper.map(source, destinationType);
	}

	private static final ModelMapperManager sharedInstance = new ModelMapperManager();

	public static ModelMapperManager getInstance() {
		return sharedInstance;
	}

	private ModelMapperManager() {
	}

	private ModelConverterImplementation mapper = new ModelConverterImplementation();

	public List<PeriodBalance> mapLeftToSpendToPeriodBalanceForCurrentMonth(Map<String,
		Statistic> statistics, Period period, Map<String, Period> periodMap) {
		StatisticsToMap statisticsToMap = new StatisticsToMap();
		statisticsToMap.setStatistics(statistics);
		statisticsToMap.setPeriod(period);
		statisticsToMap.setPeriods(periodMap);
		statisticsToMap.setLeftToSpendData(true);
		statisticsToMap.setCurrentMonth(true);

		ArrayList<PeriodBalance> mappedItems = convertToPeriodBalances(statisticsToMap);

		Collections.sort(mappedItems, (t1, t2) -> Long.compare(
			t1.getPeriod().getStart().getMillis(), t2.getPeriod().getStart().getMillis()));

		return mappedItems;
	}

	public List<PeriodBalance> mapLeftToSpendStatisticsToPeriodBalanceFor1Year(Map<String,
		Statistic> statistics, final Period endPeriod, Map<String, Period> periodMap) {

		StatisticsToMap statisticsToMap = new StatisticsToMap();
		statisticsToMap.setStatistics(statistics);
		statisticsToMap.setPeriod(endPeriod);
		statisticsToMap.setLeftToSpendData(true);
		statisticsToMap.setPeriods(periodMap);

		ArrayList<PeriodBalance> items = convertToPeriodBalances(statisticsToMap);

		return items;
	}

	public List<PeriodBalance> mapStatisticsToPeriodBalanceFor1Year(
		Map<String, Statistic> statistics,
		final Period endPeriod, Map<String, Period> periodMap) {

		StatisticsToMap statisticsToMap = new StatisticsToMap();
		statisticsToMap.setStatistics(statistics);
		statisticsToMap.setPeriod(endPeriod);
		statisticsToMap.setPeriods(periodMap);

		ArrayList<PeriodBalance> items = convertToPeriodBalances(statisticsToMap);

		for (PeriodBalance item : items) {
			item.setAmount(Math.abs(item.getAmount()));
		}

		return items;
	}

	public List<PeriodBalance> mapStatisticsToPeriodBalanceFor1YearByCategoryCode(
		Map<String, Statistic> statistics,
		final Period endPeriod,
		Map<String, Period> periodMap,
		String categoryCode) {

		List<String> codesToRemove = Lists.newArrayList();
		for (String key : statistics.keySet()) {
			if (!key.contains(categoryCode)) {
				codesToRemove.add(key);
			}
		}

		Map<String, Statistic> statisticsCopy = Maps.newHashMap(statistics);
		for (String key : codesToRemove) {
			statisticsCopy.remove(key);
		}

		return mapStatisticsToPeriodBalanceFor1Year(statisticsCopy, endPeriod, periodMap);
	}

	public List<PeriodBalance> getLatest6MonthsFrom12MonthsItems(
		List<PeriodBalance> itemsFor1Year) {
		List<PeriodBalance> items = Lists.newArrayList();
		int lastIndexOfItems = itemsFor1Year.size() - 1;
		for (int i = 0; i < (itemsFor1Year.size() / 2); i++) {
			int index = lastIndexOfItems - i;
			items.add(0, itemsFor1Year.get(index));
		}
		return items;
	}

	public Map<String, Amount> mapAveragePerCategoryFromStatistics(
		Map<String, Statistic> statisticMap) {
		Map<String, Amount> average = Maps.newHashMap();

		for (String categoryCode : statisticMap.keySet()) {

			Statistic statistic = statisticMap.get(categoryCode);
			Map<String, Statistic> children = statistic.getChildren();

			DateTime oneYearAgo = DateTime.now().minusYears(1);

			Map<String, Statistic> currentYearChildren = Maps.newHashMap();

			for (Entry<String, Statistic> monthStatistic : children.entrySet()) {
				DateTime periodEnd = monthStatistic.getValue().getPeriod().getStop();

				// We're interested in statistics from one year back
				if (oneYearAgo.isBefore(periodEnd)) {
					currentYearChildren.put(monthStatistic.getKey(), monthStatistic.getValue());
				}
			}

			if (currentYearChildren.isEmpty()) {
				continue;
			}

			double totalForAllPeriods = 0;
			for (String period : currentYearChildren.keySet()) {
				Statistic statisticPeriod = currentYearChildren.get(period);
				totalForAllPeriods += statisticPeriod.getAmount().getValue().doubleValue();
			}

			double nrOfPeriods = currentYearChildren.size();
			double averageForCategory = totalForAllPeriods / nrOfPeriods;

			ExactNumber afc = new ExactNumber(new BigDecimal(Math.abs(averageForCategory)));

			average.put(categoryCode,
				new Amount(afc, Currencies.getSharedInstance().getDefaultCurrencyCode()));
		}

		return average;
	}

	protected ArrayList<PeriodBalance> convertToPeriodBalances(StatisticsToMap source) {
		ArrayList<PeriodBalance> items = Lists.newArrayList();

		if (source.isLeftToSpendData() && source.isCurrentMonth()) {
			// Daily for a period

			Statistic monthly = source.getStatistics().get(source.getPeriod().toString());

			if (monthly != null && monthly.hasChildren()) {
				for (String key : monthly.getChildren().keySet()) {
					Statistic daily = monthly.getChildren().get(key);

					double total = mapper.map(daily.getAmount(), Double.class);
					Period p = daily.getPeriod();

					PeriodBalance pb = new PeriodBalance(p, total);
					items.add(pb);
				}
			}

		} else {
			ArrayList<Period> periods = DateUtils
				.getInstance(new SuitableLocaleFinder().findLocale(),
					TimezoneManager.defaultTimezone)
				.getYearMonthStringFor1YearByEndYearMonth(
					source.getPeriod(), source.getPeriods(), true);

			items = addPeriodsToItems(periods);

			for (PeriodBalance item : items) {
				if (source.isLeftToSpendData()) {
					Statistic monthly = source.getStatistics()
						.get(item.getPeriod().toMonthString());
					handleLeftToSpendMonthly(item, monthly);
				} else {
					for (String key : source.getStatistics().keySet()) {
						Statistic statistic = source.getStatistics().get(key);

						Statistic monthly = statistic.getChildren()
							.get(item.getPeriod().toString());
						if (monthly == null) {
							continue;
						}

						double total = mapper.map(monthly.getAmount(), Double.class);
						double value = item.getAmount() + total;
						item.setAmount(value);
					}
				}
			}
		}

		return items;
	}

	private void handleLeftToSpendMonthly(PeriodBalance item, Statistic monthly) {
		if (monthly == null) {
			return;
		}

		item.setAmount(monthly.getAmount().getValue().doubleValue());
	}

	private ArrayList<PeriodBalance> addPeriodsToItems(ArrayList<Period> periods) {
		ArrayList<PeriodBalance> items = Lists.newArrayList();
		for (Period period : periods) {
			PeriodBalance pb = new PeriodBalance(
				period, 0);
			items.add(pb);
		}
		return items;
	}
}
