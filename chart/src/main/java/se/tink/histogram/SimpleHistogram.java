package se.tink.histogram;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import androidx.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class SimpleHistogram extends View {

	private double[] frequencies;

	private double maxFrequency;

	private int selectedIndex;

	private String activeBarLabel;

	private Paint passiveBarPaint;
	private Paint activeBarPaint;
	private Paint textPaint;

	private int viewPaddingTop;
	private int chartPaddingBottom;
	private int viewPaddingBottom;

	Rect textBounds;

	public SimpleHistogram(Context context) {
		super(context);
	}

	public SimpleHistogram(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public SimpleHistogram(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}


	public void initialize(double[] frequencies, String activeBarLabel) {

		this.activeBarLabel = activeBarLabel;
		this.frequencies = frequencies;

		passiveBarPaint = new Paint();
		passiveBarPaint.setStyle(Paint.Style.STROKE);
		passiveBarPaint.setStrokeCap(Paint.Cap.ROUND);

		activeBarPaint = new Paint(passiveBarPaint);

		textPaint = new TextPaint();
		textPaint.setTextAlign(Paint.Align.CENTER);
		textPaint.setAntiAlias(true);

		textBounds = new Rect();

		for (double frequency : frequencies) {
			maxFrequency = Math.max(frequency, maxFrequency);
		}
	}

	public void setSelectedIndex(int i) {
		selectedIndex = i;
		invalidate();
	}

	@Override
	public void draw(Canvas canvas) {
		float bottom =
			canvas.getHeight() - textBounds.height() - chartPaddingBottom - viewPaddingBottom;
		for (int i = 0; i < frequencies.length; i++) {
			float x = (i + 0.5f) * canvas.getWidth() / frequencies.length;
			canvas.drawLine(x, bottom, x, (float) (viewPaddingTop + (bottom - viewPaddingTop) * (1
					- frequencies[i] / maxFrequency)),
				i == selectedIndex ? activeBarPaint : passiveBarPaint);
			if (i == selectedIndex) {
				float textX = Math.max(x, textBounds.width() / 2);
				textX = Math.min(textX, canvas.getWidth() - textBounds.width() / 2);
				canvas.drawText(activeBarLabel, textX,
					bottom + chartPaddingBottom + textBounds.height(), textPaint);
			}
		}
		super.draw(canvas);
	}

	public void applySettings(VisualSettings settings) {
		activeBarPaint.setColor(settings.activeColor);
		activeBarPaint.setStrokeWidth(settings.barWidth);
		passiveBarPaint.setColor(settings.passiveColor);
		passiveBarPaint.setStrokeWidth(settings.barWidth);
		textPaint.setColor(settings.labelColor);
		textPaint.setTextSize(settings.labelTextSize);
		textPaint.setTypeface(settings.labelTypeface);
		textPaint.getTextBounds(activeBarLabel, 0, activeBarLabel.length(), textBounds);
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
		public int viewPaddingTop;
		public int chartPaddingBottom;
		public int viewPaddingBottom;
	}
}
