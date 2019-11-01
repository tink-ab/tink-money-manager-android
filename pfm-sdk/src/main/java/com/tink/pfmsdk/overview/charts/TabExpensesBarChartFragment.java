package com.tink.pfmsdk.overview.charts;

import android.os.Bundle;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import com.google.common.collect.Lists;
import com.tink.pfmsdk.BaseFragment;
import com.tink.pfmsdk.BuildConfig;
import com.tink.pfmsdk.R;
import com.tink.pfmsdk.TimezoneManager;
import com.tink.pfmsdk.TinkApp;
import com.tink.pfmsdk.analytics.AnalyticsScreen;
import com.tink.pfmsdk.collections.Categories;
import com.tink.pfmsdk.collections.Periods;
import com.tink.pfmsdk.configuration.SuitableLocaleFinder;
import com.tink.pfmsdk.util.CurrencyUtils;
import com.tink.pfmsdk.util.ModelMapperManager;
import com.tink.pfmsdk.view.TinkIcon;
import com.tink.pfmsdk.view.TinkTextView;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.jetbrains.annotations.Nullable;
import se.tink.Charts;
import se.tink.Constants;
import se.tink.barchart.VerticalBarChartArea;
import se.tink.core.models.Labels;
import se.tink.core.models.PeriodBalance;
import se.tink.core.models.VerticalBarChart;
import se.tink.core.models.misc.Period;
import se.tink.core.models.statistic.Statistic;
import se.tink.core.models.statistic.Statistic.Type;
import se.tink.core.models.statistic.StatisticTree;
import se.tink.core.models.user.UserConfiguration;
import se.tink.piechart.Category;
import se.tink.repository.ExceptionTracker;
import se.tink.repository.ObjectChangeObserver;
import se.tink.repository.service.PeriodService;
import se.tink.repository.service.StatisticService;
import se.tink.repository.service.StreamingService;
import se.tink.repository.service.UserConfigurationService;
import se.tink.utils.PeriodBalances;
import se.tink.utils.ScreenUtils;

public class TabExpensesBarChartFragment extends BaseFragment implements TransitionAwareFragment,
	PeriodProvider {

	private static final String TAG = "TabBarChartFragment";
	public static final String ARG_INDEX = "ARG_INDEX";

	@Inject
	StreamingService streamingService;

	@Inject
	StatisticService statisticService;

	@Inject
	UserConfigurationService userConfigurationService;

	@Inject
	PeriodService periodService;

	@Inject
	Theme theme;

	@Inject
	ExceptionTracker exceptionTracker;

	VerticalBarChartArea verticalBarChartArea;

	RelativeLayout headerContainer;

	private List<PeriodBalance> expensesItemsFor1Year;

	private Map<String, Statistic> expenses;

	private Map<String, Period> periods = new HashMap<>();
	private Category activeCategory;
	private Period endPeriod;
	private TabsEnum index;
	private UserConfiguration userConfiguration;

	@Override
	public int getLayoutId() {
		return R.layout.tab_expenses_bar_chart_fragment;
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
	protected AnalyticsScreen getAnalyticsScreen() {
		return AnalyticsScreen.TRACKING_ERROR;
	}

	@Override
	protected boolean shouldTrackScreen() {
		return false;
	}

	@Override
	protected boolean doNotRecreateView() {
		return false;
	}

	public static TabExpensesBarChartFragment newInstance(int index) {
		Bundle args = new Bundle();
		TabExpensesBarChartFragment fragment = new TabExpensesBarChartFragment();
		args.putInt(ARG_INDEX, index);
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
		viewModel.getCategory().observe(getViewLifecycle(), category -> {
			Category cat = new Category();
			cat.setCode(category.getCode());
			cat.setIcon(TinkIcon.fromCategoryCode(category.getCode()));
			cat.setName(category.getName());
			categorySelected(cat);
		});

		index = TabsEnum.getTabsEnumByIndex(
			getArguments().getInt(ARG_INDEX, TabsEnum.SIX_MONTH_PAGE.getIndex()));

		List<Type> types = Lists.newArrayList();
		types.add(Type.TYPE_BY_CATEGORY);

		userConfigurationService.subscribe(userConfigurationSubscription);
		statisticService.subscribe(statisticChangeObserver, types);
		periodService.subscribe(periodChangeObserver);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		statisticService.unsubscribe(statisticChangeObserver);
		userConfigurationService.unsubscribe(userConfigurationSubscription);
	}

	private void updateUi() {
		if (expenses != null && !periods.isEmpty() && endPeriod != null) {
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
			se.tink.core.models.Category category = Categories.getSharedInstance()
				.getParentExpenseCategory();
			if (category != null) {
				categoryCode = Categories.getSharedInstance().getParentExpenseCategory().getCode();
			} else {
				return;
			}
		}

		if (endPeriod != null || BuildConfig.DEBUG) {
			expensesItemsFor1Year = ModelMapperManager.getInstance()
				.mapStatisticsToPeriodBalanceFor1YearByCategoryCode(
					expenses, endPeriod, periods, categoryCode);

			setup6MonthsChart();
		} else {
			exceptionTracker.exceptionThrown(new IllegalArgumentException(
				"endperiod is null in TabExpensesBarChartFragment"));
		}
	}

	private void handleTwelveMonthPage() {
		verticalBarChartArea.setVisibility(View.GONE);
		verticalBarChartArea.clearAllData();

		String categoryCode;
		if (activeCategory != null) {
			categoryCode = activeCategory.getCode();
		} else {
			se.tink.core.models.Category category = Categories.getSharedInstance()
				.getParentExpenseCategory();
			if (category != null) {
				categoryCode = Categories.getSharedInstance().getParentExpenseCategory().getCode();
			} else {
				return;
			}
		}

		Map<String, Period> periodMap = Periods.getSharedInstance().getPeriodMap();
		if (endPeriod != null) {
			expensesItemsFor1Year = ModelMapperManager.getInstance()
				.mapStatisticsToPeriodBalanceFor1YearByCategoryCode(
					expenses, endPeriod, periodMap, categoryCode);

			setup1YearChart();
		}
	}

	private void setup6MonthsChart() {
		verticalBarChartArea.setVisibility(View.VISIBLE);

		List<PeriodBalance> items = ModelMapperManager.getInstance()
			.getLatest6MonthsFrom12MonthsItems(expensesItemsFor1Year);

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

		Charts.sharedInstance()
			.setupBarChart(TinkApp.sharedInstance().getApplicationContext(),
				getCurrencyCode(),
				new SuitableLocaleFinder().findLocale(),
				TimezoneManager.defaultTimezone, barChart);
	}

	private void setup1YearChart() {
		verticalBarChartArea.setVisibility(View.VISIBLE);

		setupHeaders(expensesItemsFor1Year);

		VerticalBarChart barChart = new VerticalBarChart();
		barChart.setBarChartView(verticalBarChartArea);
		barChart.setItems(expensesItemsFor1Year);
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

		Charts.sharedInstance()
			.setupBarChart(TinkApp.sharedInstance().getApplicationContext(),
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
                        R.string.expenses_header_description_average,
                        CurrencyUtils.formatAmountRoundWithCurrencySymbol(
                                PeriodBalances.getAverageIgnoreLast(items))));

		labels.setTexts(texts);

		int heightOfTabLayout = ScreenUtils.dpToPixels(getContext(), 72);
		int paddingTop = ScreenUtils.dpToPixels(getContext(), 20);
		int padding = heightOfTabLayout + paddingTop;

		int labelTwoPadding = ScreenUtils.dpToPixels(getContext(), Constants.LABEL_PADDING_DP);

		int screenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
		Charts.sharedInstance()
			.setupLabels(getContext(), headerContainer, labels, screenWidth, padding,
				labelTwoPadding);
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

	@Nullable
	@Override
	public Period getPeriod() {
		return getCurrentPeriod();
	}

	//TODO: Get the actual items from the stream instead of creating them
	private Period getCurrentPeriod() {
		switch (index) {
			case SIX_MONTH_PAGE:
				return Period.createPeriodWithWholeMonths(endPeriod.getStop().minusMonths(6), endPeriod.getStop());
			case TWELVE_MONTH_PAGE:
				return Period.createPeriodWithWholeMonths(endPeriod.getStop().minusMonths(12), endPeriod.getStop());
			default:
				return null;
		}
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

	public void categorySelected(Category category) {
		activeCategory = category;
		runUiDependant(this::updateUi);
	}

	private ObjectChangeObserver<StatisticTree> statisticChangeObserver = new ObjectChangeObserver<StatisticTree>() {
		@Override
		public void onCreate(StatisticTree items) {
			expenses = StatisticTree.addOrUpdate(expenses, items.getExpensesByCategoryCode());
			updatePeriods();
			runUiDependant(TabExpensesBarChartFragment.this::updateUi);
		}

		@Override
		public void onRead(final StatisticTree items) {
			expenses = items.getExpensesByCategoryCode();
			updatePeriods();
			runUiDependant(TabExpensesBarChartFragment.this::updateUi);
		}

		@Override
		public void onUpdate(StatisticTree items) {
			StatisticTree.mergeMapUpdate(expenses, items.getExpensesByCategoryCode());
			updatePeriods();
			runUiDependant(TabExpensesBarChartFragment.this::updateUi);
		}

		@Override
		public void onDelete(StatisticTree items) {
			expenses = StatisticTree.delete(expenses, items.getExpensesByCategoryCode());
			runUiDependant(TabExpensesBarChartFragment.this::updateUi);
		}
	};

	private ObjectChangeObserver<Map<String, Period>> periodChangeObserver = new ObjectChangeObserver<Map<String, Period>>() {
		@Override
		public void onCreate(Map<String, Period> item) {
			periods = Periods.addOrUpdate(periods, item);
			updatePeriods();
			runUiDependant(TabExpensesBarChartFragment.this::updateUi);
		}

		@Override
		public void onRead(Map<String, Period> item) {
			periods = item;
			updatePeriods();
			runUiDependant(TabExpensesBarChartFragment.this::updateUi);
		}

		@Override
		public void onUpdate(Map<String, Period> item) {
			periods = Periods.addOrUpdate(periods, item);
			updatePeriods();
			runUiDependant(TabExpensesBarChartFragment.this::updateUi);
		}

		@Override
		public void onDelete(Map<String, Period> item) {
			periods = Periods.delete(periods, item);
			updatePeriods();
			runUiDependant(TabExpensesBarChartFragment.this::updateUi);
		}
	};

	private void updatePeriods() {
		endPeriod = Periods.getSharedInstance()
			.getPeriod(streamingService.getLatestStreamingDate(), periods);
		if (endPeriod == null && expenses != null && !expenses.isEmpty()) {
			endPeriod = findLatestPeriodFallback(expenses);
		}
	}

	/**
	 * Should only be run as a last fallback to find period if the backend stream with it didn't appear yet.
	 */
	private Period findLatestPeriodFallback(Map<String, Statistic> expensesByCategoryId) {
		if (expensesByCategoryId == null || expensesByCategoryId.isEmpty()) {
			return null;
		}
		Collection<Statistic> firstLevelStatistics = expensesByCategoryId.values();
		Period latestPeriod = null;
		for (Statistic statistic : firstLevelStatistics) {
			if (!statistic.hasChildren()) {
				continue;
			}
			for (Statistic secondLevelStatistic : statistic.getChildren().values()) {
				if (latestPeriod == null || secondLevelStatistic.getPeriod()
					.isAfter(latestPeriod)) {
					latestPeriod = secondLevelStatistic.getPeriod();
				}
			}
		}
		return latestPeriod;
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
