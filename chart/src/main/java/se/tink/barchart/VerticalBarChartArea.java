package se.tink.barchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import se.tink.android.charts.R;
import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import se.tink.ChartArea;
import se.tink.core.models.PeriodBalance;
import se.tink.enums.YLabelAlignment;
import se.tink.utils.CurrencyUtils;
import se.tink.utils.DateUtils;
import se.tink.utils.PortableChartUtils;
import se.tink.utils.ScreenUtils;

public class VerticalBarChartArea extends ChartArea {

	private static final int NUMBER_OF_LINES = 6;
	private static final int BOTTOM_LABELS_BOTTOM_MARGIN_DP = 24;

	private Context context;
	private boolean showXLines;
	private boolean showZeroLine;
	private boolean showMeanLine;
	private boolean showAmountLabels;
	private boolean showPeriodLabels;
	private boolean includeVerticalPadding;
	private boolean amountLabelsAboveBars;
	private int paddingBetweenBars;
	private int selectedIndex = -1;
	private int barColor = -1;
	private int negativeBarColor = -1;
	private int selectedBarColor = -1;
	private Paint textPaint;
	private Paint amountTextPaint;
	private Paint lineTextPaint;
	private Paint zeroLinePaint;
	private Paint meanValuePaint;
	private List<Rect> barRects;
	private BarChartAreaAdapter adapter;
	private boolean isSelector;
	private int cornerRadii;
	private IndexSelectorEvent event;
	private boolean invertYAxisSign;
	private YLabelAlignment showYLabels;
	private Locale locale;
	private String timezoneCode;
	private String currencyCode;

	public VerticalBarChartArea(Context context) {
		super(context);
	}

	public VerticalBarChartArea(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public VerticalBarChartArea(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void initialize(Context context, Locale locale, String timezoneCode,
		String currencyCode) {
		this.context = context;
		this.locale = locale;
		this.timezoneCode = timezoneCode;
		this.currencyCode = currencyCode;

		setupPaints();
	}

	private void setupPaints() {
		getTextPaint();

		getAmountTextPaint();

		lineTextPaint = new Paint(getTextPaint());
		lineTextPaint.setAntiAlias(true);
		lineTextPaint.setTextAlign(Paint.Align.LEFT);

		zeroLinePaint = new Paint();
		zeroLinePaint.setAntiAlias(true);
		zeroLinePaint.setStrokeWidth(ScreenUtils.dpToPixels(context, 1));
		zeroLinePaint.setStyle(Paint.Style.STROKE);

		meanValuePaint = new Paint(zeroLinePaint);
		meanValuePaint.setPathEffect(new DashPathEffect(new float[]{
			ScreenUtils.dpToPixels(context, 1), ScreenUtils.dpToPixels(context, 3)}, 0f));
	}

	@Override
	protected float leftPadding() {
		return ScreenUtils.dpToPixels(context, 12);
	}

	@Override
	protected float rightPadding() {
		return 0;
	}

	@Override
	protected float topPadding() {
		return includeVerticalPadding ? getAmountVerticalMargin() + getAmountTextPaint()
			.getTextSize() : 0;
	}

	@Override
	protected float bottomPadding() {
		return 0;
	}

	@Override
	protected int yLabelsSideMargin() {
		return ScreenUtils.dpToPixels(context, 8);
	}

	private void drawZeroLine(float zeroY, Canvas canvas) {
		canvas.drawLine(0, zeroY, getWidth(), zeroY, zeroLinePaint);
	}

	private void drawXLine(Canvas canvas, float y, double value) {
		canvas.drawLine(0, y, canvas.getWidth(), y, getAxisPaint());
		String textToDraw = CurrencyUtils.getInstance(locale)
			.formatCurrencyWithAmountSignAndWithoutSymbol(value, currencyCode);
		canvas.drawText(textToDraw, ScreenUtils.dpToPixels(context, 6),
			y + getTextPaint().getTextSize() + getLabelPadding(), lineTextPaint);
	}

	private void drawXLines(float zeroPoint, Canvas canvas) {
		RectF bounds = bounds(canvas);
		float spacingBetweenLines = bounds.height() / NUMBER_OF_LINES;
		int linesOverZero = (int) Math.floor(zeroPoint / spacingBetweenLines);
		float lastY = zeroPoint - spacingBetweenLines;
		double deltaValue = getMaxValue() - getMinValue();

		double valuePerLine = deltaValue * (spacingBetweenLines / bounds.height());
		for (int i = 0; i < linesOverZero; i++) {
			drawXLine(canvas, lastY, valuePerLine * (i + 1));
			lastY -= spacingBetweenLines;
		}

		double linesUnderZero = Math.floor((canvas.getHeight() - zeroPoint) / spacingBetweenLines);
		lastY = zeroPoint + spacingBetweenLines;
		for (int i = 0; i < linesUnderZero; i++) {
			drawXLine(canvas, lastY, -(valuePerLine * (i + 1)));
			lastY += spacingBetweenLines;
		}
	}

	private List<BarWithLabel> getBarDrawables(float zeroY, double barWidth, Canvas canvas) {
		List<BarWithLabel> barList = new ArrayList<>();
		barRects = new ArrayList<>();

		// TODO should be used for some bar charts.
//        float[] positiveRadii = new float[]{getBarCornerRadii(), getBarCornerRadii(), getBarCornerRadii(), getBarCornerRadii(), 0, 0, 0, 0};
//        float[] negativeRadii = new float[]{0, 0, 0, 0, getBarCornerRadii(), getBarCornerRadii(), getBarCornerRadii(), getBarCornerRadii()};

		/* Positive and negative radii */
		float[] radiiDown = new float[]{0, 0, 0, 0,
			getBarCornerRadii(), getBarCornerRadii(), getBarCornerRadii(), getBarCornerRadii()};
		float[] radiiUp = new float[]{getBarCornerRadii(), getBarCornerRadii(), getBarCornerRadii(),
			getBarCornerRadii(),
			0, 0, 0, 0};

		double offsettedX = getPaddingToFirstBar();
		for (int i = 0; i < adapter.getCount(); i++) {
			GradientDrawable drawable = new GradientDrawable();
			drawable.setShape(GradientDrawable.RECTANGLE);

			PeriodBalance item = adapter.getItems().get(i);

			int color;
			if (i == selectedIndex) {
				color = selectedBarColor;
				if (selectedBarColor == -1) {
					color = getBarColor();
				}
			} else {
				if (item.getAmount() >= 0) {
					color = getBarColor();
				} else {
					color = getNegativeBarColor();
				}
			}

			drawable.setColor(color);

			float endY = getPointY(item.getAmount(), bounds(canvas));
			if (item.getAmount() >= 0) {
				if (item.getAmount() == 0) {
					endY--;
				}

				drawable.setCornerRadii(radiiUp);
				drawable.setBounds((int) offsettedX, (int) endY, (int) (offsettedX + barWidth),
					(int) zeroY);
			} else {
				drawable.setCornerRadii(radiiDown);
				drawable.setBounds((int) offsettedX, (int) zeroY, (int) (offsettedX + barWidth),
					(int) endY);
			}

			Rect rect = drawable.getBounds();
			Point point;
			if (amountLabelsAboveBars || (
				(item.getAmount() - getMinValue()) / adapter.getDifference() < 0.1)) {
				if (item.getAmount() >= 0) {
					point = new Point(rect.centerX(), (int) (rect.top - getAmountVerticalMargin()));
				} else {
					point = new Point(rect.centerX(),
						(int) (rect.bottom + getAmountVerticalMargin() + (
							getAmountTextPaint().getTextSize() * .75f)));
				}
			} else {
				point = new Point(rect.centerX(), rect.top + getAmountVerticalMargin());
			}

			barRects.add(rect);
			String text = CurrencyUtils.getInstance(locale)
				.formatCurrencyWithAmountSignAndWithoutSymbol(item.getAmount(), currencyCode);
			barList.add(new BarWithLabel(drawable, point, text));
			offsettedX += barWidth + paddingBetweenBars;
		}

		return barList;
	}

	protected void drawBarLabels(float zeroY, double barWidth, Canvas canvas) {
		double offsettedX = getPaddingToFirstBar() + (barWidth / 2);
		for (int i = 0; i < adapter.getCount(); i++) {
			PeriodBalance item = adapter.getItems().get(i);
			String date = DateUtils.getInstance(locale, timezoneCode)
				.getMonthFromDateTime(item.getPeriod().getStop());

			RectF bounds = bounds(canvas);
			float y = bounds.bottom - ScreenUtils
				.dpToPixels(getContext(), BOTTOM_LABELS_BOTTOM_MARGIN_DP);

			if (item.getAmount() >= 0) {
				canvas.drawText(date, (int) offsettedX, y, getTextPaint());
			} else {
				canvas.drawText(date, (int) offsettedX, y, getTextPaint());
			}
			offsettedX += barWidth + paddingBetweenBars;
		}
	}

	protected void drawMeanLine(float position, Canvas canvas) {
		Path path = new Path();

		float sideMargin = ScreenUtils.dpToPixels(context, 8);
		path.moveTo(sideMargin, position);
		path.lineTo(getWidth() - sideMargin, position);
		canvas.drawPath(path, meanValuePaint);
	}

	protected void drawBars(List<BarWithLabel> bars, Canvas canvas) {
		for (BarWithLabel bar : bars) {
			bar.getGradient().draw(canvas);
			if (showAmountLabels) {
				canvas.drawText(bar.getLabelText(), bar.getLabelPoint().x, bar.getLabelPoint().y,
					getAmountTextPaint());
			}
		}
	}

	@Override
	public void draw(Canvas canvas) {
		if (adapter == null) {
			return;
		}

		List<Integer> yLines = PortableChartUtils
			.getSupportLines((int) getMinValue(), (int) getMaxValue());
		adapter.setMinValue(Math.min(yLines.get(0), adapter.getMinValue()));
		adapter.setMaxValue(Math.max(yLines.get(yLines.size() - 1), adapter.getMaxValue()));

		super.draw(canvas);

		if (adapter == null || Strings.isNullOrEmpty(currencyCode)) {
			return;
		}

		if (showMeanLine) {
			float meanYValue = getPointY(adapter.getMeanValue(), bounds(canvas));
			drawMeanLine(meanYValue, canvas);
		}

		float zeroY = getPointY(0, bounds(canvas));
		double barWidth = (getWidth() - getPaddingToFirstBar() - rightPaddingWithRoomForLabels() - (
			paddingBetweenBars * adapter.getCount())) / (double) adapter.getCount();

		if (showZeroLine) {
			drawZeroLine(zeroY, canvas);
		}

		if (showXLines) {
			drawXLines(zeroY, canvas);
		}

		drawBars(getBarDrawables(zeroY, barWidth, canvas), canvas);

		if (showPeriodLabels) {
			drawBarLabels(zeroY, barWidth, canvas);
		}

		drawYLabels(canvas, yLines);
	}

	private int rightPaddingWithRoomForLabels() {
		return ScreenUtils.dpToPixels(getContext(), 40);
	}


	private void drawYLabels(Canvas canvas, List<Integer> lines) {
		// TODO bool useExact = lines.Exists (l => l % 1000 != 0); ??
		if (lines.isEmpty()) {
			return;
		}

		float maxWidth = 0;
		for (Integer line : lines) {
			String text = CurrencyUtils.getInstance(locale).formatShort(line, currencyCode);
			Rect size = ScreenUtils.getSize(text, getAmountYLabelPaint());
			maxWidth = Math.max(size.width(), maxWidth);
		}

		for (Integer line : lines) {
			drawYLabel(canvas, line, maxWidth);
		}
	}

	private void drawYLabel(Canvas canvas, int value, float maxWidth) {
		if (getAmountYLabelPaint().getTextSize() <= 0) {
			return;
		}

		if (invertYAxisSign) {
			value = -value;
		}

		RectF bounds = bounds(canvas);
		float y = getPointY(value, bounds);

		String text = CurrencyUtils.getInstance(locale).formatShort(value, currencyCode);

		Rect size = ScreenUtils.getSize(text, getAmountYLabelPaint());

		canvas.drawText(text,
			bounds.right - yLabelsSideMargin(),
			y, getAmountYLabelPaint());
	}


	protected int getIndexAtPoint(PointF point) {
		float width = paddingBetweenBars + 1;
		float left = point.x - width / 2;

		Rect hitarea = new Rect((int) left, 0, (int) (left + width), getHeight());

		for (int i = 0; i < adapter.getCount(); i++) {
			if (Rect.intersects(barRects.get(i), hitarea)) {
				return i;
			}
		}

		return (hitarea.left < 0) ? 0 : barRects.size() - 1;
	}

	public void handleClick(float x, float y) {
		if (!isSelector) {
			return;
		}

		int selectedIndexLocal = getIndexAtPoint(new PointF(x, y));
		if (selectedIndex != selectedIndexLocal) {
			selectedIndex = selectedIndexLocal;
			event.indexSelected(selectedIndex);
			invalidate();
		}
	}

	public void setIndexSelectorEvent(IndexSelectorEvent event) {
		this.event = event;
	}

	public interface IndexSelectorEvent {

		void indexSelected(int index);
	}

	private float getPointY(double value, RectF bounds) {
		return bounds.top + (bounds.height() - getBottomTextAreaHeight()) * (float) (1
			- (value - getMinValue()) / (getMaxValue() - getMinValue()));
	}

	private float getBottomTextAreaHeight() {
		return getMinValue() < 0 && getMaxValue() > 0 ? ScreenUtils.dpToPixels(getContext(), 76)
			: ScreenUtils.dpToPixels(getContext(), 56);
	}

	private double getMaxValue() {
		if (adapter != null) {
			if (adapter.getMinValue() < 0 && adapter.getMaxValue() < 0) {
				return 0;
			}
			return adapter.getMaxValue();
		}
		return 0;
	}

	private double getMinValue() {
		if (adapter != null) {
			if (adapter.getMaxValue() > 0 && adapter.getMinValue() > 0) {
				return 0;
			}
			return adapter.getMinValue();
		}
		return 0;
	}

	private int getLabelPadding() {
		return ScreenUtils.dpToPixels(context, 2);
	}

	private int getAmountVerticalMargin() {
		return ScreenUtils.dpToPixels(context, 12);
	}

	private int getPaddingToFirstBar() {
		return (int) leftPadding() + (showXLines ? ScreenUtils.dpToPixels(context, 46)
			: paddingBetweenBars);
	}

	public int getBarCornerRadii() {
		return ScreenUtils.dpToPixels(context, cornerRadii);
	}

	public void setAdapter(BarChartAreaAdapter adapter) {
		this.adapter = adapter;
	}

	public void setPaddingBetweenBars(int paddingBetweenBars) {
		this.paddingBetweenBars = paddingBetweenBars;
	}

	public int getBarColor() {
		if (barColor == -1) {
			return ContextCompat
				.getColor(context, R.color.vertical_bar_chart_default_color); // TODO should have default values?
		}
		return barColor;
	}

	public void setBarColor(int barColor) {
		this.barColor = barColor;
	}

	public int getNegativeBarColor() {
		if (negativeBarColor == -1) {
			return getBarColor();
		}
		return negativeBarColor;
	}

	public void setNegativeBarColor(int negativeBarColor) {
		this.negativeBarColor = negativeBarColor;
	}

	public void setTextColor(int color) {
		getTextPaint().setColor(color);
		lineTextPaint.setColor(color);
	}

	public void setZeroLineColor(int color) {
		zeroLinePaint.setColor(color);
	}

	public void setMeanValueColor(int color) {
		meanValuePaint.setColor(color);
	}

	public void setShowXLines(boolean showXLines) {
		this.showXLines = showXLines;
	}

	public void setShowZeroLine(boolean showZeroLine) {
		this.showZeroLine = showZeroLine;
	}

	public void setShowMeanLine(boolean showMeanLine) {
		this.showMeanLine = showMeanLine;
	}

	public void setShowAmountLabels(boolean showAmountLabels) {
		this.showAmountLabels = showAmountLabels;
	}

	public void setShowPeriodLabels(boolean showPeriodLabels) {
		this.showPeriodLabels = showPeriodLabels;
	}

	public void setIncludeVerticalPadding(boolean includeVerticalPadding) {
		this.includeVerticalPadding = includeVerticalPadding;
	}

	public void setAmountLabelsAboveBars(boolean amountLabelsAboveBars) {
		this.amountLabelsAboveBars = amountLabelsAboveBars;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedBarColor(int selectedBarColor) {
		this.selectedBarColor = selectedBarColor;
	}

	public void setIsSelector(boolean isSelector) {
		this.isSelector = isSelector;
	}

	public void setCornerRadii(int cornerRadii) {
		this.cornerRadii = cornerRadii;
	}

	public Paint getTextPaint() {
		if (textPaint == null) {
			textPaint = new Paint();
			textPaint.setAntiAlias(true);
			textPaint.setTextAlign(Paint.Align.CENTER);
		}
		return textPaint;
	}

	public Paint getAmountTextPaint() {
		if (amountTextPaint == null) {
			amountTextPaint = new Paint();
			amountTextPaint.setAntiAlias(true);
			amountTextPaint.setTextAlign(Paint.Align.CENTER);
		}
		return amountTextPaint;
	}

	private Paint amountYLabelPaint;

	public Paint getAmountYLabelPaint() {
		if (amountYLabelPaint == null) {
			amountYLabelPaint = new Paint();
			amountYLabelPaint.setAntiAlias(true);
			amountYLabelPaint.setTextAlign(Align.RIGHT);
		}
		return amountYLabelPaint;
	}

	public void setupAmountTextPaint(Typeface typeface, int textColor, float textSize,
		float lineHeight, float spacing) {
		getAmountTextPaint().setTypeface(typeface);
		getAmountTextPaint().setColor(textColor);
		getAmountTextPaint().setTextSize(textSize);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getAmountTextPaint().setLetterSpacing(spacing);
		}
		// TODO lineHeight
	}

	public void setupAmountYLabelTextPaint(Typeface typeface, int textColor, float textSize,
		float spacing) {
		getAmountYLabelPaint().setTypeface(typeface);
		getAmountYLabelPaint().setColor(textColor);
		getAmountYLabelPaint().setTextSize(textSize);
		getAmountYLabelPaint().setTextAlign(Align.RIGHT);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getAmountYLabelPaint().setLetterSpacing(spacing);
		}
		// TODO lineHeight
	}

	public void setupDateTextPaint(Typeface typeface, int textColor, float textSize,
		float lineHeight, float spacing) {
		getTextPaint().setTypeface(typeface);
		getTextPaint().setColor(textColor);
		getTextPaint().setTextSize(textSize);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getTextPaint().setLetterSpacing(spacing);
		}
		// TODO lineHeight
	}

	public void clearAllData() {
		adapter = null;
		barRects = null;
		textPaint = null;
		amountTextPaint = null;
		lineTextPaint = null;
		zeroLinePaint = null;
		meanValuePaint = null;
	}

}
