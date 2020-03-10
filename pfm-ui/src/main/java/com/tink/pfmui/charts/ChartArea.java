package com.tink.pfmui.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.tink.pfmui.R;
import com.tink.pfmui.util.ScreenUtils;

abstract class ChartArea extends View {

	private Paint xLabelPaint;
	private Paint yLabelPaint;
	private Paint axisPaint;
	private Paint yLabelAmountPaint;
	private int yAmountColor;
	private float yAmountTextSize;
	private Typeface yAmountTypeface;

	ChartArea(Context context) {
		this(context, null);
	}

	public ChartArea(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ChartArea(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		setupXLabelPaint(ContextCompat.getColor(context, R.color.chart_area_label),
			ScreenUtils.dpToPixels(context, 12), Typeface.DEFAULT); // TODO typeface
		setupYLabelPaint(ContextCompat.getColor(context, R.color.chart_area_label),
			ScreenUtils.dpToPixels(context, 12), Typeface.DEFAULT); // TODO typeface
	}

	public void setupXLabelPaint(int xColor, float xTextSize, Typeface xTypeface) {
		xLabelPaint = new Paint();
		xLabelPaint.setAntiAlias(true);
		xLabelPaint.setColor(xColor);
		xLabelPaint.setTextSize(xTextSize);
		xLabelPaint.setTypeface(xTypeface);
	}

	public void setupYLabelPaint(int yColor, float yTextSize, Typeface yTypeface) {
		yLabelPaint = new Paint();
		yLabelPaint.setAntiAlias(true);
		yLabelPaint.setColor(yColor);
		yLabelPaint.setTextSize(yTextSize);
		yLabelPaint.setTypeface(yTypeface);
	}

	public void setupYLabelAmountPaint(int yColor, float yTextSize, Typeface yTypeface) {
		yLabelAmountPaint = new Paint();
		yLabelAmountPaint.setAntiAlias(true);
		yLabelAmountPaint.setColor(yAmountColor);
		yLabelAmountPaint.setTextSize(yAmountTextSize);
		yLabelAmountPaint.setTypeface(yAmountTypeface);
	}

	public void setAxisPaint(float width, int color) {
		axisPaint = new Paint();
		axisPaint.setAntiAlias(true);
		axisPaint.setStrokeCap(Paint.Cap.ROUND);
		axisPaint.setStrokeWidth(width);
		axisPaint.setColor(color);
		axisPaint.setStyle(Paint.Style.STROKE);
	}

	protected RectF addPadding(Rect rectangle) { // TODO check if this method is correct.
		RectF paddedRectangle = new RectF(rectangle);
		paddedRectangle.left += leftPadding();
		paddedRectangle.right -= rightPadding();
		paddedRectangle.top += topPadding();
		paddedRectangle.bottom -= bottomPadding();
		return paddedRectangle;
	}

	protected RectF bounds(Canvas canvas) {
		return addPadding(canvas.getClipBounds());
	}

	public Paint getAxisPaint() {
		return axisPaint;
	}

	public Paint getXLabelPaint() {
		return xLabelPaint;
	}

	public Paint getYLabelPaint() {
		return yLabelPaint;
	}

	public Paint getYLabelAmountPaint() {
		return yLabelAmountPaint;
	}

	/* Abstract methods */
	protected abstract float leftPadding();

	protected abstract float rightPadding();

	protected abstract float topPadding();

	protected abstract float bottomPadding();

	protected abstract int yLabelsSideMargin();
}
