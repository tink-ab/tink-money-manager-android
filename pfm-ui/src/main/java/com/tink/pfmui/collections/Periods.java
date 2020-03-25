package com.tink.pfmui.collections;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import com.google.common.collect.Maps;
import com.tink.model.time.Period;
import com.tink.pfmui.repository.StatisticsRepository;
import com.tink.service.observer.ChangeObserver;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.joda.time.DateTime;
import se.tink.commons.extensions.PeriodUtil;
import se.tink.privacy.Clearable;
import se.tink.privacy.DataWipeManager;

/**
 * For @deprecated methods - use StatisticRepository for getting periods
 * Just static methods here still can be used (and moved to appropriate place later)
 */
public class Periods implements ChangeObserver<Map<String, Period>>, Clearable {

	private static Periods instance;

	private Map<String, Period> map = new HashMap<>();

	@Deprecated
	public static Periods getSharedInstance() {
		if (instance == null) {
			instance = new Periods();
		}
		return instance;
	}

	private Periods() {
		DataWipeManager.sharedInstance().register(this);
	}

	/**
	 * @Deprecated Use getPeriod when supplying the period map too.
	 */
	@Deprecated
	public Period getPeriod(String key) {
		if (map.containsKey(key)) {
			return map.get(key);
		}

		return null;
	}

	/**
	 * @Deprecated Use getPeriod when supplying the period map too.
	 */
	@Deprecated
	public Period getPeriod(DateTime date) {
		for (Period period : map.values()) {
			if (PeriodUtil.isInPeriod(date, period)) {
				return period;
			}
		}
		return null;
	}

	/**
	 * @Deprecated Use getPeriod when supplying the period map too.
	 */
	@Deprecated
	public Period getPeriod(Date date) {
		return getPeriod(new DateTime(date));
	}

	@Deprecated
	public static Period getPeriod(Date date, @NonNull Map<String, Period> periodMap) {
		return getPeriod(new DateTime(date), periodMap);
	}

	@Deprecated
	public static Period getPeriod(String date, Map<String, Period> periodMap) {
		if (periodMap.containsKey(date)) {
			return periodMap.get(date);
		}
		return null;
	}

	@Deprecated
	public static Period getPeriod(DateTime date, @NonNull Map<String, Period> periodMap) {
		for (Period period : periodMap.values()) {
			if (PeriodUtil.isInPeriod(date, period)) {
				return period;
			}
		}
		return null;
	}

	private Observer<Map<String, Period>> periodObserver = stringPeriodMap -> map = stringPeriodMap;


	public void removeListener(StatisticsRepository statisticsRepository) {
		statisticsRepository.getPeriodMap().removeObserver(periodObserver);
	}

	@Override
	public void onCreate(Map<String, Period> item) {
		map = addOrUpdate(map, item);
	}

	@Override
	public void onRead(Map<String, Period> item) {
		map = item;
	}

	@Override
	public void onUpdate(Map<String, Period> item) {
		map = addOrUpdate(map, item);
	}

	@Override
	public void onDelete(Map<String, Period> item) {
		map = delete(map, item);
	}

	public void attatchListener(StatisticsRepository statisticsRepository) {
		statisticsRepository.getPeriodMap().observeForever(periodObserver);
	}

	@Deprecated
	public Map<String, Period> getPeriodMap() {
		return map;
	}

	@Deprecated
	public boolean any() {
		return map != null && map.size() > 0;
	}

	public static Map<String, Period> addOrUpdate(Map<String, Period> currentPeriods,
		Map<String, Period> newPeriods) {
		Map<String, Period> modifiedPeriods = copyMap(currentPeriods);
		modifiedPeriods.putAll(newPeriods);
		return modifiedPeriods;
	}

	public static Map<String, Period> delete(Map<String, Period> currentPeriods,
		Map<String, Period> deletedPeriods) {
		Map<String, Period> modifiedPeriods = copyMap(currentPeriods);
		modifiedPeriods.keySet().removeAll(deletedPeriods.keySet());
		return modifiedPeriods;
	}

	private static Map<String, Period> copyMap(Map<String, Period> periodsToCopy) {
		Map<String, Period> modifiedPeriods;
		if (periodsToCopy != null) {
			modifiedPeriods = Maps.newHashMap(periodsToCopy);
		} else {
			modifiedPeriods = Maps.newHashMap();
		}
		return modifiedPeriods;
	}

	@Override
	@Deprecated
	public void clear() {
		map.clear();
	}
}
