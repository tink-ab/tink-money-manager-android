package se.tink.linechart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import se.tink.android.charts.R;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import se.tink.ChartArea;
import se.tink.core.models.PeriodBalance;
import se.tink.enums.XLabelAlignment;
import se.tink.enums.YLabelAlignment;
import se.tink.managers.Manager;
import se.tink.utils.CurrencyUtils;
import se.tink.utils.DateUtils;
import se.tink.utils.MathUtils;
import se.tink.utils.PortableChartUtils;
import se.tink.utils.ScreenUtils;

public class BalanceLineChartArea extends ChartArea {

	protected Context context;
	private Paint pathPaint;
	private Paint areaPaint;
	private Paint linePaint;
	private List<PointF> points;
	private List<PointF> linePoints;
	private List<PointF> curveAdaptedPoints;
	private int areaGradientTopColor;
	private int areaGradientBottomColor;
	protected Locale locale;
	protected String timezoneCode;
	protected String currencyCode;

	private List<PeriodBalance> items;
	private List<PeriodBalance> averageItems;
	private Paint zeroLinePaint;
	private Paint averageLinePaint;
	private boolean extremeValueBasedPadding;
	private Paint markedDatePaint;
	private Paint markedDatePaint2;
	private Paint markedDatePaint3;
	private Paint markedDateTickPaint;
	private Paint markedDateTextPaint;
	private XLabelAlignment showXLabels;
	private YLabelAlignment showYLabels;
	private boolean markToday;
	private String markDate;
	private float left;
	private float right;
	private float top;
	private float bottom;
	private double minValue;
	private double maxValue;
	private List<String> dates;
	private Typeface amountLabelsTypeface;
	private int amountLabelsColor;
	private float amountLabelsTextSize;
	private int markerGradientBottomColor;
	private int markerGradientTop80OpacityColor;
	private int markerGradientTop50OpacityColor;

	@Override
	protected float topPadding() {
		if (context == null) {
			return 0;
		}

		float padding = 0;
		if (maxValue >= 0) {
			if (extremeValueBasedPadding) {
				padding += ScreenUtils.dpToPixels(context, 20);
			} else {
				padding += ScreenUtils.dpToPixels(context, 1); // Make at least room for the stroke
			}
		}

		if (markToday) {
			padding += ScreenUtils.dpToPixels(context, 5);
		}

		padding = Math.max(padding, ScreenUtils.dpToPixels(context, 20));

		return padding;
	}

	@Override
	protected float bottomPadding() {
		if (context == null) {
			return 0;
		}

		float padding = 0;
		if (minValue < 0) {
			if (extremeValueBasedPadding) {
				padding += ScreenUtils.dpToPixels(context, 20);
			} else {
				padding += ScreenUtils.dpToPixels(context, 1); // Make at least room for the stroke
			}
		}

		if (markToday) {
			padding += ScreenUtils.dpToPixels(context, 25);
		}

		if (makeRoomForXLabels()) {
			padding += getXLabelPaint().getTextSize() + (getXLabelBottomMargin() * 2);
		}

		return padding;
	}

	@Override
	protected float leftPadding() {
		return 0;
	}

	@Override
	protected float rightPadding() {
		return 0;
	}

	protected int yLabelsSideMargin() {
		return ScreenUtils.dpToPixels(context, 16);
	}

	public BalanceLineChartArea(Context context) {
		super(context);
	}

	public BalanceLineChartArea(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BalanceLineChartArea(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void initialize(Context context, String currencyCode, Locale locale,
		String timezoneCode) {

		this.context = context;
		this.locale = locale;
		this.timezoneCode = timezoneCode;
		this.currencyCode = currencyCode;

		markDate = DateUtils.getInstance(locale, timezoneCode).getTodayAsString();

		extremeValueBasedPadding = false;

		markedDateTickPaint = new Paint();
		markedDateTickPaint.setStrokeCap(Paint.Cap.ROUND);
		markedDateTickPaint.setAntiAlias(true);
		markedDateTickPaint.setStrokeWidth(ScreenUtils.dpToPixels(context, 1.34f));
		markedDateTickPaint.setStyle(Paint.Style.STROKE);

		markedDatePaint = new Paint();
		markedDatePaint.setStrokeCap(Paint.Cap.ROUND);
		markedDatePaint.setAntiAlias(true);
		markedDatePaint.setStrokeWidth(ScreenUtils.dpToPixels(context, 2));

		markedDatePaint2 = new Paint();
		markedDatePaint2.setStrokeCap(Paint.Cap.ROUND);
		markedDatePaint2.setAntiAlias(true);
		markedDatePaint2.setStyle(Style.FILL_AND_STROKE);

		markedDatePaint3 = new Paint();
		markedDatePaint3.setStrokeCap(Paint.Cap.ROUND);
		markedDatePaint3.setAntiAlias(true);
		markedDatePaint3.setStyle(Style.FILL_AND_STROKE);

		markedDateTextPaint = new Paint();
		markedDateTextPaint.setAntiAlias(true);
		markedDateTextPaint.setTextAlign(Paint.Align.CENTER);
//        markedDateTextPaint.setTextSize(); TODO : TextSize = UIUtils.GetDimension (Resource.Dimension.smallTextSize)
		markedDateTextPaint.setTypeface(
			Typeface.DEFAULT_BOLD); // TODO : UIUtils.Font.GetTypeface (UIUtils.Font.Semibold)

		pathPaint = new Paint();
		pathPaint.setAntiAlias(true);
		pathPaint.setColor(ContextCompat.getColor(context, R.color.transparent));

		areaPaint = new Paint();
		areaPaint.setAntiAlias(true);
		areaPaint.setStrokeCap(Paint.Cap.ROUND);
		areaPaint.setStyle(Paint.Style.FILL);

		linePaint = new Paint();
		linePaint.setAntiAlias(true);
		linePaint.setDither(true);
		linePaint.setStyle(Paint.Style.STROKE);
		linePaint.setStrokeJoin(Paint.Join.ROUND);
		linePaint.setStrokeCap(Paint.Cap.ROUND);
		linePaint.setPathEffect(new CornerPathEffect(10));
		linePaint.setStrokeWidth(ScreenUtils.dpToPixels(context, 3));
	}

	public float getPointY(double value, RectF bounds) {
		if (maxValue == minValue) {
			maxValue++;
		}
		return bounds.top + bounds.height() * (float) (1 - (value - minValue) / (maxValue
			- minValue));
	}


	public float getPointX(String date, RectF bounds) {
		if (dates.size() < 2) {
			return -1;
		}

		String first = dates.get(0);
		String last = dates.get(dates.size() - 1);

		DateTime firstDate = DateTime.parse(first);
		DateTime lastDate = DateTime.parse(last);
		DateTime dateTime = DateTime.parse(date);

		Interval interval = new Interval(firstDate, lastDate);
		int dateRange = interval.toDuration().toStandardDays().getDays();

		Interval interval1 = new Interval(firstDate, dateTime);
		int differanceFromCurrentDate = interval1.toDuration().toStandardDays().getDays();

		float ratio = ((float) differanceFromCurrentDate) / ((float) dateRange);

		return bounds.left + bounds.width() * ratio;
	}

	private void drawXAxis(Canvas canvas) {
		RectF bounds = bounds(canvas);
		float y = getPointY(0, bounds);

		canvas.drawLine(bounds.left, y, bounds.right, y, getAxisPaint());
	}


	private void drawYLabels(Canvas canvas, List<Integer> lines) {
		// TODO bool useExact = lines.Exists (l => l % 1000 != 0); ??
		if (lines.isEmpty()) {
			return;
		}

		float maxWidth = 0;

		getYLabelPaint().setTypeface(amountLabelsTypeface);
		getYLabelPaint().setColor(amountLabelsColor);
		getYLabelPaint().setTextSize(amountLabelsTextSize);

		for (Integer line : lines) {
			String text = CurrencyUtils.getInstance(locale).formatShort(line, currencyCode);
			Rect size = ScreenUtils.getSize(text, getYLabelPaint());
			maxWidth = Math.max(size.width(), maxWidth);
		}

		for (Integer line : lines) {
			drawYLabel(canvas, line, maxWidth);
		}
	}

	private void drawYLabel(Canvas canvas, double value, float maxWidth) {
		if (showYLabels == YLabelAlignment.NONE) {
			return;
		}

		if (getYLabelPaint().getTextSize() <= 0) {
			return;
		}

		RectF bounds = bounds(canvas);
		float y = getPointY(value, bounds);

		String text = CurrencyUtils.getInstance(locale).formatShort(value, currencyCode);

		Rect size = ScreenUtils.getSize(text, getYLabelPaint());

		canvas.drawText(text,
			bounds.right - maxWidth - yLabelsSideMargin(),
			y, getYLabelPaint());
	}

	private void drawXLabels(Canvas canvas) {
		for (String date : getDatesToShow()) {
			drawXLabel(date, canvas);
		}
	}

	private void drawXLabel(String date, Canvas canvas) {
		RectF bounds = bounds(canvas);
		float x = getPointX(date, bounds);

		if (showXLabels == XLabelAlignment.NONE) {
			return;
		}

		String text = DateUtils.getInstance(locale, timezoneCode).formatDateString(date);

		float textHeight = getXLabelPaint().getTextSize() / 2;

		if (showXLabels == XLabelAlignment.BOTTOM_OUTSIDE_CHARTAREA) {
			canvas.drawText(text, x + ScreenUtils.dpToPixels(context, 2),
				canvas.getHeight() - (textHeight / 2 + getXLabelBottomMargin()), getXLabelPaint());

		} else if (showXLabels == XLabelAlignment.BOTTOM_INSIDE_CHART_AREA
			|| showXLabels == XLabelAlignment.BOTTOM_INSIDE_FOR_POSITIVE_OUTSIDE_FOR_NEGATIVE) {
			canvas.drawText(text, x + ScreenUtils.dpToPixels(context, 8),
				canvas.getHeight() - (textHeight / 2 + getXLabelBottomMargin()), getXLabelPaint());

		} else if (showXLabels == XLabelAlignment.TOP_INSIDE_CHART_AREA) {
			canvas.drawText(text, x + ScreenUtils.dpToPixels(context, 8),
				bounds.top + textHeight + ScreenUtils.dpToPixels(context, 12), getXLabelPaint());
		}
	}

	@Override
	public void draw(Canvas canvas) {
		if (averageItems == null || items == null || getDates() == null) {
			return;
		}

		List<Integer> yLines = PortableChartUtils
			.getSupportLines((int) getMinValue(), (int) getMaxValue());
		minValue = Math.min(yLines.get(0), minValue);
		maxValue = Math.max(yLines.get(yLines.size() - 1), maxValue);

		List<PointF> curvedPoints = getCurvePoints(averageItems, bounds(canvas));
		setPoints(curvedPoints);
		setCurveAdaptedPoints(curvedPoints);

		List<PointF> lineCurvedPoints = getCurvePoints(items, bounds(canvas));
		setLinePoints(lineCurvedPoints);

		drawArea(canvas);

		if (markDate != null || !markDate.isEmpty()) {
			drawDateMarker(canvas, markDate);
		}

		drawZeroLine(canvas);

		drawPath(canvas);

		if (showYLabels != YLabelAlignment.NONE) {
			drawYLabels(canvas, yLines);
		}

		super.draw(canvas);
	}

	protected void drawArea(Canvas canvas) {
		List<PointF> points = getCurveAdaptedPoints();

		if (hasVariant(VARIANT_AREA_BELOW_ACTUAL)) {
			points = getLinePoints();
		}

		if (points == null || points.isEmpty()) {
			return;
		}

		float y0 = getPointY(getMinValue(), bounds(canvas));
		Path path = getPath(points);

		path.lineTo(points.get(points.size() - 1).x, y0);
		path.lineTo(points.get(0).x, y0);
		path.close();

		double amount = maxOnlyAverage;

		if (hasVariant(VARIANT_AREA_BELOW_ACTUAL)) {
			amount = maxWithoutAverage;
		}

		double amountForGradient = getMinValue();

		getAreaPaint().setShader(
			new LinearGradient(
				0,
				getPointY(amount, bounds(canvas)),
				0,
				getPointY(amountForGradient, bounds(canvas)),
				getAreaGradientTopColor(),
				getAreaGradientBottomColor(),
				Shader.TileMode.CLAMP));

		canvas.drawPath(path, getAreaPaint());
	}

	private Paint getAverageLinePaint() {
		if (averageLinePaint == null) {
			averageLinePaint = new Paint();
			averageLinePaint.setAntiAlias(true);
			averageLinePaint.setStyle(Paint.Style.STROKE);
			averageLinePaint.setStrokeWidth(ScreenUtils.dpToPixels(context, 1));
			float dashWidth = ScreenUtils.dpToPixels(context, 2);
			float dashGap = ScreenUtils.dpToPixels(context, 4);
			averageLinePaint.setPathEffect(new DashPathEffect(new float[]{dashWidth, dashGap}, 0));
			averageLinePaint.setColor(Color.TRANSPARENT);
		}
		return averageLinePaint;
	}

	protected void drawPath(Canvas canvas) {
		List<PointF> curveAdaptedPoints = getCurveAdaptedPoints();
		List<PointF> linePoints = getLinePoints();

		if (linePoints != null && !linePoints.isEmpty()) {
			Path path = getPath(linePoints);
			canvas.drawPath(path, linePaint);
		}

		if (curveAdaptedPoints != null && !curveAdaptedPoints.isEmpty()) {
			Path averagePath = getPath(curveAdaptedPoints);
			canvas.drawPath(averagePath, getAverageLinePaint());
		}

	}

	private void drawZeroLine(Canvas canvas) {
		canvas.drawLine(0, getPointY(0, bounds(canvas)), bounds(canvas).width(),
			getPointY(0, bounds(canvas)), getZeroLinePaint());
	}

	private Paint getZeroLinePaint() {
		if (zeroLinePaint == null) {
			zeroLinePaint = new Paint();
			zeroLinePaint.setAntiAlias(true);
			zeroLinePaint.setStrokeWidth(ScreenUtils.dpToPixels(context, 1));
		}

		return zeroLinePaint;
	}

	private List<PointF> getCurvePoints(List<PeriodBalance> items, RectF bounds) {
		List<PointF> points = new ArrayList<>();

		if (items != null) {
			for (PeriodBalance periodBalance : items) {
				points.add(getCurvePoint(periodBalance, bounds));
			}
		}

		return points;
	}

	private PointF getCurvePoint(PeriodBalance periodBalance, RectF bounds) {
		return new PointF(getPointX(periodBalance.getPeriod().toString(), bounds),
			getPointY(periodBalance.getAmount(), bounds));
	}

	public void setZeroLinePaintColor(int color) {
		getZeroLinePaint().setColor(color);
	}

	public void setSecondaryLineColor(int color) {
		getAverageLinePaint().setColor(color);
	}

	double maxWithoutAverage;
	double maxOnlyAverage;

	public void setData(List<PeriodBalance> items, List<PeriodBalance> averageItems) {
		this.items = items;
		this.averageItems = averageItems;

		List<PeriodBalance> localItems = new ArrayList<>(items);
		List<PeriodBalance> localAverageItems = new ArrayList<>(averageItems);

		Collections.sort(localItems, new Comparator<PeriodBalance>() {
			@Override
			public int compare(PeriodBalance a1, PeriodBalance a2) {
				return Double.compare(a1.getAmount(), a2.getAmount());
			}
		});
		Collections.sort(localAverageItems, new Comparator<PeriodBalance>() {
			@Override
			public int compare(PeriodBalance a1, PeriodBalance a2) {
				return Double.compare(a1.getAmount(), a2.getAmount());
			}
		});

		double[] minAndMaxItems = Manager.sharedInstance().getMinAndMaxValue(localItems);
		double maxValueItems = minAndMaxItems[0];
		double minValueItems = minAndMaxItems[1];

		maxWithoutAverage = Math.max(maxValueItems, 0);

		double[] minAndMaxAverageItems = Manager.sharedInstance()
			.getMinAndMaxValue(localAverageItems);
		double maxValueAverageItems = minAndMaxAverageItems[0];
		double minValueAverageItems = minAndMaxAverageItems[1];

		maxOnlyAverage = Math.max(maxValueAverageItems, 0);

		double maxValue = Math.max(maxValueItems, maxValueAverageItems);
		double minValue = Math.min(minValueItems, minValueAverageItems);

		maxValue = Math.max(maxValue, 0);
		minValue = Math.min(minValue, 0);

		setMaxValue(maxValue);
		setMinValue(minValue);

		List<String> datesFromAvg = getDatesFromPeriodBalances(averageItems);
		List<String> datesItems = getDatesFromPeriodBalances(items);

		setDates(union(datesFromAvg, datesItems));
	}


	public List<String> union(List<String> list1, List<String> list2) {
		Set<String> set = new HashSet<>();

		set.addAll(list1);
		set.addAll(list2);

		ArrayList<String> list = new ArrayList<>(set);
		Collections.sort(list);

		return list;
	}

	private List<String> getDatesFromPeriodBalances(List<PeriodBalance> periodBalances) {
		List<String> dates = new ArrayList<>();
		for (PeriodBalance periodBalance : periodBalances) {
			dates.add(periodBalance.getPeriod().toString());
		}
		return dates;
	}

	private void drawDateMarker(Canvas canvas, String date) {
		int index = getIndex(date);
		if (index < 0) {
			return;
		}

		if (!markToday) {
			return;
		}

		DateTime dateTime = DateTime.parse(date);
		if (dateTime.isAfterNow()) {
			return;
		}

		RectF bounds = new RectF(canvas.getClipBounds());
		float x = getPointX(date, bounds);

		if (!MathUtils.isWithinBounds(index, getLinePoints())) {
			return;
		}

		PointF point = getLinePoints().get(index);
		float radius = getTodayDotPaintRadius();

		// Tick
		String text = DateUtils.getInstance(locale, timezoneCode).formatDateHuman(markDate);
		float textY = bottom - ScreenUtils.dpToPixels(context, 16);

		final Rect textBounds = new Rect();

		// The 2 dp is textview padding from top
		int textViewPaddingTop = ScreenUtils.dpToPixels(context, 2);
		markedDateTextPaint.getTextBounds(text, 0, text.length(), textBounds);

		left = x - textBounds.width() / 2 - ScreenUtils.dpToPixels(context, 10);
		right = x + textBounds.width() / 2 + ScreenUtils.dpToPixels(context, 10);
		top = (int) textY - (textBounds.height() / 2) - ScreenUtils.dpToPixels(context, 5)
			- textViewPaddingTop;
		bottom = (int) textY + (textBounds.height() / 2) + ScreenUtils.dpToPixels(context, 5);

		RectF fillerRect = new RectF();
		fillerRect.set(left, top, right, bottom);

		float radius3 = radius + ScreenUtils.dpToPixels(context, 15);
		markedDatePaint3.setStrokeWidth(0);
		markedDatePaint3.setShader(
			new RadialGradient(
				point.x,
				point.y,
				radius3,
				markerGradientBottomColor,
				markerGradientTop50OpacityColor,
				TileMode.MIRROR));
		canvas.drawCircle(point.x, point.y, radius3, markedDatePaint3);

		float radius2 = radius + ScreenUtils.dpToPixels(context, 8);
		markedDatePaint2.setStrokeWidth(0);
		markedDatePaint2.setShader(
			new RadialGradient(
				point.x,
				point.y,
				radius2,
				markerGradientBottomColor,
				markerGradientTop80OpacityColor,
				TileMode.MIRROR));
		canvas.drawCircle(point.x, point.y, radius2, markedDatePaint2);

		markedDatePaint
			.setStyle(DateUtils.getInstance(locale, timezoneCode).isToday(date)
				? Paint.Style.FILL_AND_STROKE : Paint.Style.STROKE);
		canvas.drawCircle(point.x, point.y, radius, markedDatePaint);
	}

	private int getIndex(String date) {
		if (date == null || dates.isEmpty()) {
			return -1;
		}

		for (int i = 0; i < dates.size(); i++) {
			if (Objects.equals(dates.get(i), date)) {
				return i;
			}
		}

		return -1;
	}

	private List<String> getDatesToShow() {

		if (dates == null) {
			return null;
		}

		if (dates.size() < 3) {
			return dates;
		} else {
			List<String> datesToShow = Lists.newArrayList();

			double interval = (dates.size() - 1) / 3d;

			datesToShow.add(dates.get((int) Math.round(interval * 0.5)));
			datesToShow.add(dates.get((int) Math.round(interval * 1.5)));
			datesToShow.add(dates.get((int) Math.round(interval * 2.5)));

			return datesToShow;
		}
	}

	public float getTodayDotPaintRadius() {
		return ScreenUtils.dpToPixels(context, 3.5f);
	}

	public double getMinValue() {
		return minValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	public boolean makeRoomForXLabels() {
		return XLabelAlignment.BOTTOM_OUTSIDE_CHARTAREA == showXLabels ||
			(XLabelAlignment.BOTTOM_INSIDE_FOR_POSITIVE_OUTSIDE_FOR_NEGATIVE == showXLabels
				&& minValue < 0);
	}

	public float getXLabelBottomMargin() {
		if (showXLabels == XLabelAlignment.BOTTOM_INSIDE_FOR_POSITIVE_OUTSIDE_FOR_NEGATIVE ||
			showXLabels == XLabelAlignment.BOTTOM_OUTSIDE_CHARTAREA ||
			showXLabels == XLabelAlignment.BOTTOM_INSIDE_CHART_AREA) {

			return ScreenUtils.dpToPixels(context, 6);
		}

		return 0;
	}

	public int getLastLabelMarginNeeded() {
		return ScreenUtils.dpToPixels(context, 31);
	}

	public int getFirstLabelMarginNeeded() {
		return ScreenUtils.dpToPixels(context, 27);
	}

	public void setDateMarkerCenterColor(int color) {
		markedDatePaint.setColor(color);
	}

	public void setShowXLabels(XLabelAlignment showXLabels) {
		this.showXLabels = showXLabels;
	}

	public void setShowYLabels(YLabelAlignment showYLabels) {
		this.showYLabels = showYLabels;
	}

	public void setMarkToday(boolean markToday) {
		this.markToday = markToday;
	}

	protected void setDates(List<String> dates) {
		this.dates = dates;
	}

	public List<String> getDates() {
		return dates;
	}

	public boolean getMarkToday() {
		return markToday;
	}

	public void setAmountLabelFontType(Typeface typeface, int color, float textSize) {
		amountLabelsTypeface = typeface;
		amountLabelsColor = color;
		amountLabelsTextSize = textSize;
	}

	public void setTodayDotPaintGradientColors(int dateMarkerGradientBottom,
		int dateMarkerGradientTop50, int dateMarkerGradientTop80) {
		markerGradientBottomColor = dateMarkerGradientBottom;
		markerGradientTop50OpacityColor = dateMarkerGradientTop50;
		markerGradientTop80OpacityColor = dateMarkerGradientTop80;
	}

	protected Path getPath(List<PointF> points) {
		Path path = new Path();
		if (points != null && !points.isEmpty()) {
			PointF firstPoint = points.get(0);
			path.moveTo(firstPoint.x, firstPoint.y);

			PointF p1 = new PointF(firstPoint.x, firstPoint.y);
			for (PointF p2 : points) {
				path.quadTo(p1.x, p1.y, (p2.x + p1.x) / 2, (p2.y + p1.y) / 2);
				p1 = p2;
			}

			PointF lastPoint = points.get(points.size() - 1);
			path.lineTo(lastPoint.x, lastPoint.y);
		}

		return path;
	}

	public void setLinePaint(int color) {
		linePaint.setColor(color);
	}

	public Paint getAreaPaint() {
		return areaPaint;
	}

	public List<PointF> getPoints() {
		return points;
	}

	public void setPoints(List<PointF> points) {
		this.points = points;
	}

	public List<PointF> getLinePoints() {
		return linePoints;
	}

	public void setLinePoints(List<PointF> linePoints) {
		this.linePoints = linePoints;
	}

	public List<PointF> getCurveAdaptedPoints() {
		return curveAdaptedPoints;
	}

	public void setCurveAdaptedPoints(List<PointF> curveAdaptedPoints) {
		this.curveAdaptedPoints = curveAdaptedPoints;
	}

	public int getAreaGradientTopColor() {
		return areaGradientTopColor;
	}

	public void setAreaGradientTopColor(int areaGradientTopColor) {
		this.areaGradientTopColor = areaGradientTopColor;
	}

	public int getAreaGradientBottomColor() {
		return areaGradientBottomColor;
	}

	public void setAreaGradientBottomColor(int areaGradientBottomColor) {
		this.areaGradientBottomColor = areaGradientBottomColor;
	}


	public static final int VARIANT_DEFAULT = 0;
	public static final int VARIANT_DASHED_AVERAGE = 1;
	public static final int VARIANT_AREA_BELOW_ACTUAL = 2;

	private int variant = VARIANT_DEFAULT;

	public void setVariant(int variant) {
		this.variant = variant;
	}

	private boolean hasVariant(int variant) {
		return (this.variant & variant) == variant;
	}
}
