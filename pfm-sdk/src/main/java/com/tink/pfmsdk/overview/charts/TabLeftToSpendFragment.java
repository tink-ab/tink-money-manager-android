package com.tink.pfmsdk.overview.charts;


import android.graphics.Point;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import butterknife.BindView;
import com.google.common.collect.Lists;
import com.tink.pfmsdk.BaseFragment;
import com.tink.pfmsdk.FragmentAnimationFlags;
import com.tink.pfmsdk.R;
import com.tink.pfmsdk.TimezoneManager;
import com.tink.pfmsdk.TinkApp;
import com.tink.pfmsdk.analytics.AnalyticsScreen;
import com.tink.pfmsdk.collections.Periods;
import com.tink.pfmsdk.configuration.SuitableLocaleFinder;
import com.tink.pfmsdk.util.CurrencyUtils;
import com.tink.pfmsdk.util.ModelMapperManager;
import com.tink.pfmsdk.view.PeriodPicker;
import com.tink.pfmsdk.view.TinkIcon;
import com.tink.pfmsdk.view.TinkTextView;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import kotlin.Unit;
import kotlin.text.StringsKt;
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
import se.tink.linechart.BalanceLineChartArea;
import se.tink.repository.ObjectChangeObserver;
import se.tink.repository.service.PeriodService;
import se.tink.repository.service.StatisticService;
import se.tink.repository.service.StreamingService;
import se.tink.repository.service.UserConfigurationService;
import se.tink.utils.DateUtils;
import se.tink.utils.PeriodBalances;
import se.tink.utils.ScreenUtils;
import timber.log.Timber;

public class TabLeftToSpendFragment extends BaseFragment implements ObjectChangeObserver<StatisticTree>, PeriodProvider {
	public static final String LEFT_TO_SPEND_ACTUAL = "actual";
	public static final String LEFT_TO_SPEND_AVERAGE = "average";

	@Inject
	Theme theme;

	@Inject
	StreamingService streamingService;

	@Inject
	StatisticService statisticService;

	@Inject
	UserConfigurationService userConfigurationService;

	@Inject
	PeriodService periodService;

	@Inject
	SuitableLocaleFinder suitableLocaleFinder;

	RelativeLayout headerContainer;

	BalanceLineChartArea lineChartContainer;

	PeriodPicker periodPicker;

	VerticalBarChartArea barChartView6Months;

	VerticalBarChartArea barChartView12Months;

	Button tutorialMarker;

	private static final String ARG_POSITION = "arg_position";
	private static final String TAG = "LeftToSpend";

	private Map<String, Statistic> leftToSpend;
	private int screenWidth;
	private int screenHeight;
	private List<PeriodBalance> itemsFor1Year;
	private Period chosenPeriod;
	private TabsEnum index;
	private UserConfiguration userConfiguration;
	private boolean currentMothChartSetup;
	private DateUtils dateUtils;

	@Override
	public int getLayoutId() {
		return R.layout.tab_left_to_spend_fragment;
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
	protected String getTitle() {
		return getString(R.string.left_to_spend_title);
	}

	@Override
	protected boolean doNotRecreateView() {
		return true;
	}

	public static TabLeftToSpendFragment newInstance(int position) {
		TabLeftToSpendFragment fragment = new TabLeftToSpendFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_POSITION, position);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void authorizedOnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		startPostponedEnterTransition();
		applyTheme(theme);

		if (!isFirstCreation()) {
			return;
		}

		// TODO: PFMSDK: Don't use butterknife and using data binding after making this fragment kotlinized
		headerContainer = view.findViewById(R.id.header_container);
		lineChartContainer = view.findViewById(R.id.line_chart_current_month);
		periodPicker = view.findViewById(R.id.period_picker);
		barChartView6Months = view.findViewById(R.id.vertical_bar_chart_6_months);
		barChartView12Months = view.findViewById(R.id.vertical_bar_chart_12_months);
		tutorialMarker = view.findViewById(R.id.tutorial_marker);

		setupWidthAndHeight();

		index = TabsEnum.getTabsEnumByIndex(getArguments().getInt(ARG_POSITION));

		List<Type> types = Lists.newArrayList();
		types.add(Type.TYPE_LEFT_TO_SPEND);
		types.add(Type.TYPE_LEFT_TO_SPEND_AVERAGE);

		periodService.subscribe(periodChangeObserver);
		userConfigurationService.subscribe(userConfigurationSubscription);
		statisticService.subscribe(this, types);

		setupPieChart();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		statisticService.unsubscribe(this);
		userConfigurationService.unsubscribe(userConfigurationSubscription);
	}

	private void setupPieChart() {
		if (index == TabsEnum.CURRENT_MONTH_PAGE) {
			setupCurrentMonthChart();
			setupTutorialView();
		}
	}

	private void setupViews() {
		if (leftToSpend == null || chosenPeriod == null) {
			//TODO: empty state view
			Timber.e("setupViews: Error: left to spend is empty. No data to show!");
			return;
		}

		itemsFor1Year = ModelMapperManager.getInstance()
			.mapLeftToSpendStatisticsToPeriodBalanceFor1Year(
				leftToSpend.get(LEFT_TO_SPEND_ACTUAL).getChildren(),
				chosenPeriod, periods);

		switch (index) {

			case CURRENT_MONTH_PAGE: {
				setupPeriodPicker();
				updateChartsWithData();
				break;
			}

			case SIX_MONTH_PAGE: {
				setup6MonthsChart();
				break;
			}

			case TWELVE_MONTH_PAGE: {
				setup1YearChart();
				break;
			}

			default: {
				break;
			}
		}
	}

	private void setupTutorialView() {
		tutorialMarker.setVisibility(View.VISIBLE);
		tutorialMarker.setOnClickListener(v -> fragmentCoordinator.replace(
			new LeftToSpendTutorialFragment(), true, FragmentAnimationFlags.NONE));
		tutorialMarker.setText(TinkIcon.INFO);
	}

	private void setupCurrentMonthChart() {
		if (currentMothChartSetup || userConfiguration == null) {
			return;
		}
		currentMothChartSetup = true;

		lineChartContainer.setVisibility(View.VISIBLE);

		boolean showXLabels = true;
		boolean showYLabels = true;

		Charts.sharedInstance().setupLineChart(
			lineChartContainer,
			TinkApp.sharedInstance().getApplicationContext(),
			userConfiguration.getI18nConfiguration().getCurrencyCode(),
			new SuitableLocaleFinder().findLocale(),
			TimezoneManager.defaultTimezone,
			showXLabels,
			showYLabels,
			true,
			theme.getChartBackgroundColor(),
			theme.getCenterDateMarkColor(),
			theme.getLineChartZeroLine(),
			theme.getAreaGradientBottomColor(),
			theme.getAreaGradientTopColor(),
			theme.getLinePaintColor(),
			theme.getLineChartAmountLabels().getFont(),
			theme.getLineChartAmountLabels().getTextColor(),
			theme.getLineChartAmountLabels().getTextSize(),
			theme.getDateMarkerGradientBottom(),
			theme.getDateMarkerGradientTop50(),
			theme.getDateMarkerGradientTop80());
		lineChartContainer.setupXLabelPaint(theme.getXLabelTheme().getTextColor(),
			theme.getXLabelTheme().getTextSize(), theme.getXLabelTheme().getFont());

		lineChartContainer.setSecondaryLineColor(theme.getAverageLineColor());
		lineChartContainer.setVariant(BalanceLineChartArea.VARIANT_AREA_BELOW_ACTUAL
			| BalanceLineChartArea.VARIANT_DASHED_AVERAGE);

		updateChartsWithData();
	}


	private void setupPeriodPicker() {
		if (index != TabsEnum.CURRENT_MONTH_PAGE) {
			return;
		}
		if (itemsFor1Year == null) {
			return;
		}

		dateUtils = DateUtils.getInstance(suitableLocaleFinder.findLocale(), TimezoneManager.defaultTimezone);

		periodPicker.setVisibility(View.VISIBLE);
        periodPicker.formatter =
                period -> StringsKt.capitalize(dateUtils.getMonthNameAndMaybeYearOfPeriod(period));

		periodPicker.setOnItemSelected(period -> {
			chosenPeriod = period;
			updateChartsWithData();
			return Unit.INSTANCE;
		});
		List<Period> periods = dateUtils.getYearMonthStringFor1YearByEndYearMonth(
			chosenPeriod,
			Periods.getSharedInstance().getPeriodMap(),
			false
		);

		periodPicker.setItems(periods);
		periodPicker.setCurrentItem(chosenPeriod);
	}

	private void setup6MonthsChart() {
		barChartView6Months.setVisibility(View.VISIBLE);

		List<PeriodBalance> items = Lists.newArrayList();
		for (int counter = itemsFor1Year.size() - 1; counter > (itemsFor1Year.size() / 2 - 1);
			counter--) {
			items.add(0, itemsFor1Year.get(counter));
		}

		setupHeaders(items, false);

		VerticalBarChart barChart = new VerticalBarChart();
		barChart.setBarChartView(barChartView6Months);
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
		barChartView12Months.setVisibility(View.VISIBLE);

		setupHeaders(itemsFor1Year, false);

		VerticalBarChart barChart = new VerticalBarChart();
		barChart.setBarChartView(barChartView12Months);
		barChart.setItems(itemsFor1Year);
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

	private void setupHeaders(List<PeriodBalance> items, boolean isCurrentMonth) {

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

		if (isCurrentMonth) {

			PeriodBalance currentlyLeftToSpend = null;

			double currentlyLeftToSpendAmount = 0;
			if (items.size() > 0) {
				currentlyLeftToSpend = items.get(items.size() - 1);
				currentlyLeftToSpendAmount = currentlyLeftToSpend.getAmount();
			}

			texts
				.add(CurrencyUtils.formatAmountRoundWithCurrencySymbol(currentlyLeftToSpendAmount));

			double averageLeftToSpendAmount = 0;

			if (currentlyLeftToSpend != null) {

				Map<String, Period> periodMap = Periods.getSharedInstance().getPeriodMap();
				List<PeriodBalance> averageDataForAMonth = ModelMapperManager.getInstance()
					.mapLeftToSpendToPeriodBalanceForCurrentMonth(
						leftToSpend.get(LEFT_TO_SPEND_AVERAGE)
							.getChildren(),
						chosenPeriod, periodMap);

				for (PeriodBalance periodBalance : averageDataForAMonth) {
					if (periodBalance.getPeriod() != null && periodBalance.getPeriod()
						.equals(currentlyLeftToSpend.getPeriod())) {
						averageLeftToSpendAmount = periodBalance.getAmount();
						break;
					}
				}


			}

			int result = (int) Math
				.round(Math.abs(currentlyLeftToSpendAmount - averageLeftToSpendAmount));
			String r = CurrencyUtils.formatAmountRoundWithCurrencySymbol(result);

			String text;
			if (currentlyLeftToSpendAmount > averageLeftToSpendAmount) {
				text = getString(R.string.left_to_spend_header_description_more, r);
			} else {
				text = getString(R.string.left_to_spend_header_description_less, r);
			}
			texts.add(text);

		} else {

			double total = 0;
			for (PeriodBalance item : items) {
				total += item.getAmount();
			}

            texts.add(CurrencyUtils.formatAmountRoundWithCurrencySymbol(total));
            texts.add(
                    String.format(
                            getString(R.string.left_to_spend_header_description_average),
                            CurrencyUtils.formatAmountRoundWithCurrencySymbol(
                                    PeriodBalances.getAverageIgnoreLast(items))));
		}

		labels.setTexts(texts);

		int heightOfTabLayout = ScreenUtils.dpToPixels(getContext(), 72);
		int paddingTop = ScreenUtils.dpToPixels(getContext(), 20);
		int padding = heightOfTabLayout + paddingTop;

		int labelTwoPadding = ScreenUtils.dpToPixels(getContext(), Constants.LABEL_PADDING_DP);

		Charts.sharedInstance()
			.setupLabels(getContext(), headerContainer, labels, screenWidth, padding,
				labelTwoPadding);
	}

	private void updateChartsWithData() {
		Map<String, Period> periodMap = periods;

		if (leftToSpend == null || leftToSpend.isEmpty() || chosenPeriod == null
			|| index != TabsEnum.CURRENT_MONTH_PAGE || !currentMothChartSetup) {
			//TODO: empty state view
			Timber.e("updateChartsWithData: Error: left to spend is empty. No data to show!");
			return;
		}

		List<PeriodBalance> items = ModelMapperManager.getInstance()
			.mapLeftToSpendToPeriodBalanceForCurrentMonth(
				leftToSpend.get(LEFT_TO_SPEND_ACTUAL).getChildren(),
				chosenPeriod, periodMap);

		List<PeriodBalance> averageDataForAMonth = ModelMapperManager.getInstance()
			.mapLeftToSpendToPeriodBalanceForCurrentMonth(
				leftToSpend.get(LEFT_TO_SPEND_AVERAGE).getChildren(),
				chosenPeriod, periodMap);

		Charts.sharedInstance()
				.updateStatisticsForLineChart(lineChartContainer, items, averageDataForAMonth);

		setupHeaders(items, true);
	}

	private void setupWidthAndHeight() {
		int toolbarHeight = 0;
		TypedValue tv = new TypedValue();
		if (getActivity() != null) {
			if (getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
				toolbarHeight = TypedValue
					.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
			}
		}

		int marginTop = ScreenUtils
			.dpToPixels(TinkApp.sharedInstance().getApplicationContext(), 25);
		if (getActivity() != null) {
			Display display = getActivity().getWindowManager().getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			screenWidth = size.x;
			screenHeight = size.y - (toolbarHeight + marginTop);
		}
	}

	public String getCurrencyCode() {
		if (userConfiguration == null) {
			return "";
		}
		return userConfiguration.getI18nConfiguration().getCurrencyCode();
	}

	@Nullable
	@Override
	public Period getPeriod() {
		if (index == TabsEnum.CURRENT_MONTH_PAGE) {
			return ModelMapperManager.getInstance().map(chosenPeriod, Period.class);
		} else if (index == TabsEnum.SIX_MONTH_PAGE) {
			return Period.createPeriodWithWholeMonths(chosenPeriod.getStart().minusMonths(6), chosenPeriod.getStop());
		} else if (index == TabsEnum.TWELVE_MONTH_PAGE) {
			return Period.createPeriodWithWholeMonths(chosenPeriod.getStart().minusMonths(12), chosenPeriod.getStop());
		}
		return null;
	}

	public interface Theme extends BaseFragment.Theme {

		TinkTextView.Theme getTotalAmountTitle();

		TinkTextView.Theme getPageInformation();

		TinkTextView.Theme getBarChartsAmountLabels();

		TinkTextView.Theme getBarChartsDateLabels();

		TinkTextView.Theme getLineChartAmountLabels();

		TinkTextView.Theme getBarYLabel();

		int getChartBackgroundColor();

		int getCenterDateMarkColor();

		int getLineChartZeroLine();

		int getZeroLineColor();

		int getAreaGradientBottomColor();

		int getAreaGradientTopColor();

		int getLinePaintColor();

		int getDateMarkerGradientBottom();

		int getDateMarkerGradientTop50();

		int getDateMarkerGradientTop80();

		int get6MonthBarColor();

		int get6MonthNegativeBarColor();

		int getMeanValueColor();

		int get1YearBarColor();

		int get1YearNegativeBarColor();

		TinkTextView.Theme getXLabelTheme();

		//TinkIconView.Theme getTutorialMarkerTheme();

		int getAverageLineColor();
	}

	// TODO: PFMSDK: Remove this after fixing the marker button
//	@Override
//	protected void applyTheme(se.tink.android.fragments.base.BaseFragment.Theme theme) {
//		super.applyTheme(theme);
//
//
//		//tutorialMarker.setTheme(this.theme.getTutorialMarkerTheme());
//	}

	private void updateLeftToSpend() {
		updatePeriods(); //might find new fallback period
		runUiDependant(this::setupViews);
	}

	@Override
	public void onCreate(StatisticTree items) {
		leftToSpend = StatisticTree.addOrUpdate(leftToSpend, items.getLeftToSpend());
		updateLeftToSpend();
	}

	@Override
	public void onRead(final StatisticTree items) {
		leftToSpend = items.getLeftToSpend();
		updateLeftToSpend();
	}

	@Override
	public void onUpdate(StatisticTree items) {
		StatisticTree.mergeMapUpdate(leftToSpend, items.getLeftToSpend());
		updateLeftToSpend();
	}

	@Override
	public void onDelete(StatisticTree items) {
		leftToSpend = StatisticTree.delete(leftToSpend, items.getLeftToSpend());
		updateLeftToSpend();
	}


	private ObjectChangeObserver<UserConfiguration> userConfigurationSubscription = new ObjectChangeObserver<UserConfiguration>() {

		@Override
		public void onCreate(UserConfiguration item) {
			//No valid operation
		}

		@Override
		public void onRead(UserConfiguration item) {
			userConfiguration = item;
			runUiDependant(() -> setupCurrentMonthChart());
		}

		@Override
		public void onUpdate(UserConfiguration item) {
			userConfiguration = item;
			runUiDependant(() -> setupCurrentMonthChart());
		}

		@Override
		public void onDelete(UserConfiguration item) {
			//No valid operation.
		}
	};

	private Map<String, Period> periods = new HashMap<>();


	private void updatePeriods() {
		chosenPeriod = Periods
			.getPeriod(streamingService.getLatestStreamingDate(), periods);
		if (chosenPeriod == null && leftToSpend != null && !leftToSpend.isEmpty()) {
			chosenPeriod = findLatestPeriodFallback(leftToSpend);
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


	private ObjectChangeObserver<Map<String, Period>> periodChangeObserver = new ObjectChangeObserver<Map<String, Period>>() {

		private void update() {
			updatePeriods();
			runUiDependant(TabLeftToSpendFragment.this::setupViews);
		}

		@Override
		public void onCreate(Map<String, Period> item) {
			periods = Periods.addOrUpdate(periods, item);
			update();
		}

		@Override
		public void onRead(Map<String, Period> item) {
			periods = item;
			update();
		}

		@Override
		public void onUpdate(Map<String, Period> item) {
			periods = Periods.addOrUpdate(periods, item);
			update();
		}

		@Override
		public void onDelete(Map<String, Period> item) {
			periods = Periods.delete(periods, item);
			update();
		}
	};

}
