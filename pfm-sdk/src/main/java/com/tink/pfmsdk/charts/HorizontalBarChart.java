package com.tink.pfmsdk.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import androidx.annotation.ColorInt;

/**
 * @deprecated - use Progress bar instead
 */
@Deprecated
public class HorizontalBarChart extends ChartArea {


	public HorizontalBarChart(Context context) {
		super(context);
		initialize();
	}

	public HorizontalBarChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}

	public HorizontalBarChart(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initialize();

	}


	@Override
	protected float leftPadding() {
		return 0;
	}

	@Override
	protected float rightPadding() {
		return 0;
	}

	@Override
	protected float topPadding() {
		return 0;
	}

	@Override
	protected float bottomPadding() {
		return 0;
	}

	@Override
	protected int yLabelsSideMargin() {
		return 0;
	}

	private @ColorInt
	int markedGradientStartColor;

	public void setMarkedGradientStartColor(@ColorInt int color) {
		markedGradientStartColor = color;
	}

	private @ColorInt
	int markedGradientStopColor;

	public void setMarkedGradientStopColor(@ColorInt int color) {
		markedGradientStopColor = color;
	}

	public void setBackgroundColor(@ColorInt int color) {
		backgroundPaint.setColor(color);
	}

	private void setMarkedPartHeight(float height) {
		markedPaint.setStrokeWidth(height);
	}

	private void setBackgroundHeight(float height) {
		backgroundPaint.setStrokeWidth(height);
	}

	private float sideMargin;

	public void setSideMargin(float sideMargin) {
		this.sideMargin = sideMargin;
	}

	private float markedPart;

	public void setMarkedPart(float markedPart) {
		this.markedPart = markedPart;
		invalidate();
	}

	public float getMarkedPart() {
		return markedPart;
	}

	protected Paint backgroundPaint;

	public Paint getBackgroundPaint() {
		return backgroundPaint;
	}

	private Paint markedPaint;

	public Paint getMarkedPaint() {
		return markedPaint;
	}


	private void initialize() {

		backgroundPaint = new Paint();
		backgroundPaint.setStrokeCap(Cap.ROUND);
		backgroundPaint.setAntiAlias(true);
		backgroundPaint.setStyle(Style.FILL_AND_STROKE);

		markedPaint = new Paint();
		markedPaint.setAntiAlias(true);
		markedPaint.setStrokeCap(Cap.ROUND);
		markedPaint.setStyle(Style.FILL_AND_STROKE);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (oldh != h) {
			setMarkedPartHeight(h);
			setBackgroundHeight(h);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		drawChart(canvas);

		super.onDraw(canvas);
	}

	private void drawChart(Canvas canvas) {
		RectF rect = bounds(canvas);

		float left = rect.left;
		float right = rect.right;
		float top = rect.top;
		float height = rect.height();

		float xStart = left + backgroundPaint.getStrokeWidth() / 2 + sideMargin;
		float xEnd = right - sideMargin - backgroundPaint.getStrokeWidth() / 2;
		float y = top + height / 2;

		float fullLength = xEnd - xStart;

		float x2Start = xStart;

		canvas.drawLine(xStart, y, xEnd, y, backgroundPaint);

		float drawedLength = fullLength * Math.min(1, markedPart);
		if (drawedLength == 0) {
			return;
		}

		float x2End = x2Start + drawedLength;
		markedPaint.setShader(new LinearGradient(0, 0, drawedLength, 0, markedGradientStartColor,
			markedGradientStopColor, TileMode.CLAMP));

		markedPaint.setStrokeCap(Cap.ROUND);
		canvas.drawLine(x2Start, y, x2Start + 1, y, markedPaint); //1 is minimum allowed line
		markedPaint.setStrokeCap(Cap.SQUARE);

		//Draw between max and min but excluding rounded edges.
		//End of line can not be shorter left of start (That's' why we have the max)
		canvas.drawLine(x2Start + markedPaint.getStrokeWidth() / 2, y,
			Math.max(x2End - markedPaint.getStrokeWidth() / 2,
				x2Start + markedPaint.getStrokeWidth() / 2 + 1), y, markedPaint);
		if (markedPart >= 1) { //If the bar is filled it should be rounded on the right edge too
			markedPaint.setStrokeCap(Cap.ROUND);
			canvas.drawLine(x2End - 1, y, x2End, y, markedPaint);
		}
	}

}
