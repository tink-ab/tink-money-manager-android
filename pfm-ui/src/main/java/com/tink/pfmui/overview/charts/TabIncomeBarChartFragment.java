package com.tink.pfmui.overview.charts;

import android.os.Bundle;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tink.pfmui.BaseFragment;
import com.tink.pfmui.R;
import com.tink.pfmui.TimezoneManager;
import com.tink.pfmui.charts.Charts;
import com.tink.pfmui.charts.Constants;
import com.tink.pfmui.charts.VerticalBarChartArea;
import com.tink.pfmui.charts.models.Labels;
import com.tink.pfmui.charts.models.PeriodBalance;
import com.tink.pfmui.charts.models.VerticalBarChart;
import com.tink.pfmui.tracking.ScreenEvent;
import com.tink.pfmui.collections.Categories;
import com.tink.pfmui.collections.Periods;
import com.tink.pfmui.configuration.SuitableLocaleFinder;
import com.tink.pfmui.repository.StatisticsRepository;
import com.tink.pfmui.util.CurrencyUtils;
import com.tink.pfmui.mapper.ModelMapperManager;
import com.tink.pfmui.util.ScreenUtils;
import com.tink.pfmui.util.extensions.PeriodBalances;
import com.tink.pfmui.view.TinkTextView;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import se.tink.core.models.Category;
import se.tink.core.models.misc.Period;
import se.tink.core.models.statistic.Statistic;
import se.tink.core.models.statistic.StatisticTree;
import se.tink.core.models.user.UserConfiguration;
import se.tink.repository.ObjectChangeObserver;
import se.tink.repository.service.StatisticService;
import se.tink.repository.service.StreamingService;
import se.tink.repository.service.UserConfigurationService;

public class TabIncomeBarChartFragment extends BaseFragment implements
	TransitionAwareFragment {

	private static final String ARG_POSITION = "arg_position";
	private static final String TAG = "TabIncomeBarChart";

	@Inject
	Theme theme;

	@Inject
	StreamingService streamingService;

	@Inject
	StatisticService statisticService;

	@Inject
	StatisticsRepository statisticsRepository;

	@Inject
	UserConfigurationService userConfigurationService;

	RelativeLayout headerContainer;

	VerticalBarChartArea verticalBarChartArea;

	private TabsEnum index;
	private Category activeCategory;
	private List<PeriodBalance> incomeItemsFor1Year;
	private Map<String, Statistic> income;
	private Period endPeriod;
	private UserConfiguration userConfiguration;
	private Map<String, Period> periods;

	@Override
	public int getLayoutId() {
		return R.layout.tab_income_bar_chart_fragment;
	}

	@Override
	public boolean needsLoginToBeAuthorized() {
		return true;
	}

	@Override
	protected Theme getTheme() {
		return theme;
	}

	@Override
	protected ScreenEvent getScreenEvent() {
		return ScreenEvent.TRACKING_ERROR;
	}

	@Override
	protected boolean shouldTrackScreen() {
		return false;
	}

	@Override
	protected boolean doNotRecreateView() {
		return false;
	}

	public static TabIncomeBarChartFragment newInstance(int index) {

		Bundle args = new Bundle();
		TabIncomeBarChartFragment fragment = new TabIncomeBarChartFragment();
		args.putInt(ARG_POSITION, index);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void authorizedOnCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		super.authorizedOnCreateView(inflater, container, savedInstanceState);

		// TODO: PFMSDK: Don't use butterknife and using data binding after making this fragment kotlinized
		verticalBarChartArea = view.findViewById(R.id.vertical_bar_chart);
		headerContainer = view.findViewById(R.id.header_container);

		ChartDetailsViewModel viewModel = ViewModelProviders.of(getRootFragment(), viewModelFactory).get(ChartDetailsViewModel.class);
		viewModel.getCategory().observe(getViewLifecycle(), this::categorySelected);

		income = Maps.newHashMap();

		index = TabsEnum.getTabsEnumByIndex(getArguments().getInt(ARG_POSITION));

		userConfigurationService.subscribe(userConfigurationSubscription);
		statisticService.subscribe(statisticChangeObserver);
		statisticsRepository.getPeriodMap().observe(this, periodMap -> {
			periods = periodMap;
			updatePeriods();
		});
	}

	public void categorySelected(Category category) {
		activeCategory = category;
		runUiDependant(this::updateUi);
	}

	//TODO: Get the actual periods from the stream instead of creating them
	private Period getCurrentPeriod() {
		switch (index) {
			case SIX_MONTH_PAGE:
				return Period.createPeriodWithWholeMonths(endPeriod.getStop().minusMonths(6),
					endPeriod.getStop());
			case TWELVE_MONTH_PAGE:
				return Period
					.createPeriodWithWholeMonths(endPeriod.getStop().minusMonths(12),
						endPeriod.getStop());
			default:
				return null;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		statisticService.unsubscribe(statisticChangeObserver);
		userConfigurationService.unsubscribe(userConfigurationSubscription);
	}

	private void updateUi() {
		if(income != null && periods != null && endPeriod != null) {
			switch (index) {
				case SIX_MONTH_PAGE:
					handleSixMonthPage();
					break;
				case TWELVE_MONTH_PAGE:
					handleTwelveMonthPage();
					break;
			}
		}
	}

	private void handleSixMonthPage() {

		verticalBarChartArea.setVisibility(View.GONE);
		verticalBarChartArea.clearAllData();

		String categoryCode;
		if (activeCategory != null) {
			categoryCode = activeCategory.getCode();
		} else {
			categoryCode = Categories.getSharedInstance().getParentIncomeCategory().getCode();
		}


		incomeItemsFor1Year = ModelMapperManager
			.mapStatisticsToPeriodBalanceFor1YearByCategoryCode(
				income, endPeriod, periods, categoryCode);

		setup6MonthsChart();
	}

	private void handleTwelveMonthPage() {
		if (index != TabsEnum.TWELVE_MONTH_PAGE) {
			return;
		}

		verticalBarChartArea.setVisibility(View.GONE);
		verticalBarChartArea.clearAllData();

		String categoryCode;
		if (activeCategory != null) {
			categoryCode = activeCategory.getCode();
		} else {
			categoryCode = Categories.getSharedInstance().getParentIncomeCategory().getCode();
		}

		Map<String, Period> periodMap = Periods.getSharedInstance().getPeriodMap();

		incomeItemsFor1Year = ModelMapperManager
			.mapStatisticsToPeriodBalanceFor1YearByCategoryCode(
				income, endPeriod, periodMap, categoryCode);

		setup1YearChart();
	}

	private void setup6MonthsChart() {
		verticalBarChartArea.setVisibility(View.VISIBLE);

		List<PeriodBalance> items = ModelMapperManager
			.getLatest6MonthsFrom12MonthsItems(incomeItemsFor1Year);

		setupHeaders(items);

		VerticalBarChart barChart = new VerticalBarChart();
		barChart.setBarChartView(verticalBarChartArea);
		barChart.setItems(items);
		barChart.setBackgroundColor(theme.getChartBackgroundColor());
		barChart.setBarColor(theme.get6MonthBarColor());
		barChart.setNegativeBarColor(theme.get6MonthNegativeBarColor());
		barChart.setZerolineColor(theme.getZeroLineColor());
		barChart.setMeanValueColor(theme.getMeanValueColor());
		barChart.setPaddingBetweenBars(Constants.BAR_CHART_PADDING_BETWEEN_6_MONTHS_DP);
		barChart.setTypefaceAmountLabel(theme.getBarChartsAmountLabels().getFont());
		barChart.setTextColorAmountLabel(theme.getBarChartsAmountLabels().getTextColor());
		barChart.setTextSizeAmountLabel(theme.getBarChartsAmountLabels().getTextSize());
		barChart.setTextLineHeightAmountLabel(theme.getBarChartsAmountLabels().getLineHeight());
		barChart.setTextSpacingAmountLabel(theme.getBarChartsAmountLabels().getSpacing());
		barChart.setTypefaceDateLabel(theme.getBarChartsDateLabels().getFont());
		barChart.setTextColorDateLabel(theme.getBarChartsDateLabels().getTextColor());
		barChart.setTextSizeDateLabel(theme.getBarChartsDateLabels().getTextSize());
		barChart.setTextLineHeightDateLabel(theme.getBarChartsDateLabels().getLineHeight());
		barChart.setTextSpacingDateLabel(theme.getBarChartsDateLabels().getSpacing());
		barChart.setYLabelTextColor(theme.getBarYLabel().getTextColor());
		barChart.setSpacingYLabel(theme.getBarYLabel().getSpacing());
		barChart.setTextSizeYLabel(theme.getBarYLabel().getTextSize());
		barChart.setTypefaceYLabel(theme.getBarYLabel().getFont());

		barChart.setShowXLines(false);
		barChart.setShowZeroLine(true);
		barChart.setShowMeanLine(true);
		barChart.setShowAmountLabels(true);
		barChart.setShowPeriodLabels(true);
		barChart.setIncludeVerticalPadding(true);
		barChart.setAmountLabelsAboveBars(true);
		barChart.setCornerRadii(Constants.BAR_CHART_CORNER_RADII_DP);

		Charts
			.setupBarChart(getContext(),
				getCurrencyCode(),
				new SuitableLocaleFinder().findLocale(),
				TimezoneManager.defaultTimezone, barChart);
	}

	private void setup1YearChart() {
		verticalBarChartArea.setVisibility(View.VISIBLE);

		setupHeaders(incomeItemsFor1Year);

		VerticalBarChart barChart = new VerticalBarChart();
		barChart.setBarChartView(verticalBarChartArea);
		barChart.setItems(incomeItemsFor1Year);
		barChart.setShowAmountLabels(false);
		barChart.setBackgroundColor(theme.getChartBackgroundColor());
		barChart.setBarColor(theme.get1YearBarColor());
		barChart.setNegativeBarColor(theme.get1YearNegativeBarColor());
		barChart.setZerolineColor(theme.getZeroLineColor());
		barChart.setMeanValueColor(theme.getMeanValueColor());
		barChart.setPaddingBetweenBars(Constants.BAR_CHART_PADDING_BETWEEN_12_MONTHS_DP);
		barChart.setTypefaceDateLabel(theme.getBarChartsDateLabels().getFont());
		barChart.setTextColorDateLabel(theme.getBarChartsDateLabels().getTextColor());
		barChart.setTextSizeDateLabel(theme.getBarChartsDateLabels().getTextSize());
		barChart.setTextLineHeightDateLabel(theme.getBarChartsDateLabels().getLineHeight());
		barChart.setTextSpacingDateLabel(theme.getBarChartsDateLabels().getSpacing());
		barChart.setYLabelTextColor(theme.getBarYLabel().getTextColor());
		barChart.setSpacingYLabel(theme.getBarYLabel().getSpacing());
		barChart.setTextSizeYLabel(theme.getBarYLabel().getTextSize());
		barChart.setTypefaceYLabel(theme.getBarYLabel().getFont());

		barChart.setShowXLines(false);
		barChart.setShowZeroLine(true);
		barChart.setShowMeanLine(true);
		barChart.setShowAmountLabels(false);
		barChart.setShowPeriodLabels(true);
		barChart.setIncludeVerticalPadding(true);
		barChart.setAmountLabelsAboveBars(true);
		barChart.setCornerRadii(Constants.BAR_CHART_CORNER_RADII_DP);

		Charts
			.setupBarChart(getContext(),
				getCurrencyCode(),
				new SuitableLocaleFinder().findLocale(),
				TimezoneManager.defaultTimezone, barChart);
	}

	private void setupHeaders(List<PeriodBalance> items) {
		double total = 0;
		for (PeriodBalance item : items) {
			total += item.getAmount();
		}

		Labels labels = new Labels();

		labels.setText1Style(
			theme.getTotalAmountTitle().getFont(),
			theme.getTotalAmountTitle().getTextColor(),
			theme.getTotalAmountTitle().getTextSize(),
			theme.getTotalAmountTitle().getSpacing());

		labels.setText2Style(
			theme.getPageInformation().getFont(),
			theme.getPageInformation().getTextColor(),
			theme.getPageInformation().getTextSize(),
			theme.getPageInformation().getSpacing());

		List<String> texts = Lists.newArrayList();
		texts.add(CurrencyUtils.formatAmountRoundWithCurrencySymbol(total));

        texts.add(
                getString(
                        R.string.tink_income_header_description_average,
                        CurrencyUtils.formatAmountRoundWithCurrencySymbol(
                                PeriodBalances.getAverageIgnoreLast(items))));

		labels.setTexts(texts);

		int heightOfTabLayout = ScreenUtils.dpToPixels(getContext(), 72);
		int paddingTop = ScreenUtils.dpToPixels(getContext(), 20);
		int padding = heightOfTabLayout + paddingTop;

		int labelTwoPadding = ScreenUtils.dpToPixels(getContext(), Constants.LABEL_PADDING_DP);

		int screenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
		Charts
			.setupLabels(getContext(), headerContainer, labels, screenWidth, padding,
				labelTwoPadding);
	}


	@Override
	protected void applyTheme(BaseFragment.Theme theme) {
		super.applyTheme(theme);

	}

	public String getCurrencyCode() {
		if (userConfiguration == null) {
			return "";
		}
		return userConfiguration.getI18nConfiguration().getCurrencyCode();
	}

	@Override
	public void prepareToExit(@NonNull BaseFragment fragment) {
		fragment.setSharedElementReturnTransition(null);
		fragment.setExitTransition(new Fade());
	}

	public interface Theme extends BaseFragment.Theme {

		TinkTextView.Theme getTotalAmountTitle();

		TinkTextView.Theme getPageInformation();

		TinkTextView.Theme getBarChartsAmountLabels();

		TinkTextView.Theme getBarChartsDateLabels();

		TinkTextView.Theme getBarYLabel();

		int getChartBackgroundColor();

		int getZeroLineColor();

		int get6MonthBarColor();

		int get6MonthNegativeBarColor();

		int getMeanValueColor();

		int get1YearBarColor();

		int get1YearNegativeBarColor();

		//TinkIconView.Theme getIconTheme();

	}

	private ObjectChangeObserver<StatisticTree> statisticChangeObserver = new ObjectChangeObserver<StatisticTree>() {
		@Override
		public void onCreate(StatisticTree items) {
			income = StatisticTree.addOrUpdate(income, items.getIncomeByCategoryCode());
			runUiDependant(() -> updateUi());
		}

		@Override
		public void onRead(final StatisticTree items) {
			income = items.getIncomeByCategoryCode();
			runUiDependant(() -> updateUi());
		}

		@Override
		public void onUpdate(StatisticTree items) {
			StatisticTree.mergeMapUpdate(income, items.getIncomeByCategoryCode());
			runUiDependant(() -> updateUi());
		}

		@Override
		public void onDelete(StatisticTree items) {
			income = StatisticTree.delete(income, items.getIncomeByCategoryCode());
			runUiDependant(() -> updateUi());
		}
	};

	private void updatePeriods() {
		endPeriod = Periods.getSharedInstance()
			.getPeriod(streamingService.getLatestStreamingDate(), periods);
		runUiDependant(this::updateUi);
	}

	private ObjectChangeObserver<UserConfiguration> userConfigurationSubscription = new ObjectChangeObserver<UserConfiguration>() {

		@Override
		public void onCreate(UserConfiguration item) {
			//No valid operation
		}

		@Override
		public void onRead(UserConfiguration item) {
			userConfiguration = item;
			runUiDependant(() -> updateUi());
		}

		@Override
		public void onUpdate(UserConfiguration item) {
			userConfiguration = item;
			runUiDependant(() -> updateUi());
		}

		@Override
		public void onDelete(UserConfiguration item) {
			//No valid operation.
		}
	};

}
