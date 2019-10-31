package se.tink.barchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import androidx.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import java.util.List;

public class SimpleLabeledBarChart extends View {

	private List<Double> data;
	private List<String> labels;

	double maxAmount;
	int maxIndex;

	float barWidth;
	float barCornerRadius;

	Paint passiveBarPaint;
	Paint activeBarPaint;
	Paint textPaint;

	int viewPaddingTop;
	int chartPaddingBottom;
	int viewPaddingBottom;

	public SimpleLabeledBarChart(Context context) {
		super(context);
	}

	public SimpleLabeledBarChart(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public SimpleLabeledBarChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void initialize(List<Double> data, List<String> labels) {

		this.data = data;
		this.labels = labels;

		for (int i = 0; i < data.size(); i++) {
			double amount = data.get(i);
			if (maxIndex < 0 || amount > maxAmount) {
				maxIndex = i;
				maxAmount = amount;
			}
		}

		passiveBarPaint = new Paint();
		passiveBarPaint.setStyle(Paint.Style.FILL);
		passiveBarPaint.setAntiAlias(true);

		activeBarPaint = new Paint(passiveBarPaint);

		textPaint = new TextPaint();
		textPaint.setTextAlign(Paint.Align.CENTER);
		textPaint.setAntiAlias(true);
	}

	@Override
	public void draw(Canvas canvas) {
		int count = data.size();

		float bottom = canvas.getHeight() - chartPaddingBottom - viewPaddingBottom;
		float betweenMargin = (canvas.getWidth() - count * barWidth) / (count - 1);

		for (int i = 0; i < count; i++) {
			float x = i * (betweenMargin + barWidth);
			float top = (float) (viewPaddingTop + (bottom - viewPaddingTop) * (1
				- data.get(i) / maxAmount));
			canvas.drawRoundRect(x, top, x + barWidth, bottom, barCornerRadius, barCornerRadius,
				i == maxIndex ? activeBarPaint : passiveBarPaint);
			canvas.drawRect(x, bottom - barCornerRadius, x + barWidth, bottom,
				i == maxIndex ? activeBarPaint : passiveBarPaint);
			canvas
				.drawText(labels.get(i), x + barWidth / 2, bottom + chartPaddingBottom, textPaint);
		}
		super.draw(canvas);
	}

	public void applySettings(VisualSettings settings) {
		activeBarPaint.setColor(settings.activeColor);
		passiveBarPaint.setColor(settings.passiveColor);
		textPaint.setColor(settings.labelColor);
		textPaint.setTextSize(settings.labelTextSize);
		textPaint.setTypeface(settings.labelTypeface);
		barWidth = settings.barWidth;
		barCornerRadius = settings.barCornerRadius;
		viewPaddingTop = settings.viewPaddingTop;
		chartPaddingBottom = settings.chartPaddingBottom;
		viewPaddingBottom = settings.viewPaddingBottom;

		invalidate();
	}

	public static class VisualSettings {

		public int activeColor;
		public int passiveColor;
		public int labelColor;
		public float labelTextSize;
		public Typeface labelTypeface;
		public float barWidth;
		public float barCornerRadius;
		public int viewPaddingTop;
		public int chartPaddingBottom;
		public int viewPaddingBottom;
	}
}
