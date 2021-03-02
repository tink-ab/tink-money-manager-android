package com.tink.moneymanagerui.charts;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.View;
import com.tink.moneymanagerui.charts.models.Labels;
import com.tink.moneymanagerui.util.ScreenUtils;
import java.util.List;


class LabelsView extends View {

	private Labels labels;
	private Context context;
	private int screenWidth;
	private int screenHeight;
	private int paddingTop;
	private float opticalVerticalAdjustment = 0f;

	private float text1Size;
	private int text1Color;
	private int text1X;
	private int text1Y;
	private float characterSpacing1 = 0f;

	private float text2Size;
	private int text2Color;
	private int text2X;
	private int text2Y;
	private float characterSpacing2 = 0f;

	private float text3Size;
	private int text3Color;
	private int text3X;
	private int text3Y;
	private float characterSpacing3 = 0f;

	private static final String REFERENCE_HEIGHT_LETTER = "M";

	LabelsView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	LabelsView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	LabelsView(Context context) {
		super(context);
	}

	void initialize(Context context, Labels labels, int screenWidth, int screenHeight,
		int paddingTop, float opticalVerticalAdjustment) {

		this.labels = labels;
		this.context = context;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.paddingTop = paddingTop;
		this.opticalVerticalAdjustment = opticalVerticalAdjustment;

		text1Size = labels.getTextSize1();
		text1Color = labels.getTextColor1();
		text1X = 0;
		text1Y = 0;
		characterSpacing1 = labels.getCharacterSpacing1();

		text2Size = labels.getTextSize2();
		text2Color = labels.getTextColor2();
		text2X = 0;
		text2Y = 0;
		characterSpacing2 = labels.getCharacterSpacing2();

		text3Size = labels.getTextSize3();
		text3Color = labels.getTextColor3();
		text3X = 0;
		text3Y = 0;
		characterSpacing3 = labels.getCharacterSpacing3();
	}

	private PointF getCenterPoint() {
		return new PointF(screenWidth / 2, screenHeight / 2);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		PointF center = getCenterPoint();
		float x = center.x;
		float y = center.y;
		y += paddingTop;

		List<String> texts = labels.getTexts();
		if (texts == null || texts.isEmpty()) {
			return;
		}

		int text2Height = 0;
		int text1Height = 0;

		float opticalVerticalAdjustment = ScreenUtils
			.dpToPixels(context, this.opticalVerticalAdjustment);

		if (texts.size() > 1) {
			String text2 = texts.get(1);
			if (text2 != null && !text2.isEmpty()) {

				Paint text2Paint = getTextPaint(labels.getTypeface2(), text2Color, text2Size,
					characterSpacing2);
				text2Height = ScreenUtils.getSize(REFERENCE_HEIGHT_LETTER, text2Paint).height();

				float yPos = y + (text2Height / 2) + text2Y - opticalVerticalAdjustment;

				canvas.drawText(
					text2,
					x + text2X,
					yPos,
					text2Paint);
			}
		}

		String text1 = texts.get(0);
		if (text1 != null && !text1.isEmpty()) {
			Paint text1Paint = getTextPaint(labels.getTypeface1(), text1Color, text1Size,
				characterSpacing1);
			text1Height = ScreenUtils.getSize(REFERENCE_HEIGHT_LETTER, text1Paint).height();
			float yPos =
				y - (text2Height / 2) - text1Height - ScreenUtils.dpToPixels(context, 5) + text1Y
					- opticalVerticalAdjustment;
			canvas.drawText(
				text1,
				x + text1X,
				yPos,
				text1Paint);
		}

		if (texts.size() == 3) {
			String text3 = texts.get(2);
			Paint text3Paint = getTextPaint(labels.getTypeface3(), text3Color, text3Size,
				characterSpacing3);
			int text3Height = ScreenUtils.getSize(REFERENCE_HEIGHT_LETTER, text3Paint).height();
			float yPos = y + (text2Height) + text3Height + ScreenUtils.dpToPixels(context, 5)
				- opticalVerticalAdjustment;

			canvas.drawText(
				text3,
				x + text3X,
				yPos,
				text3Paint);
		}
	}

	void updateScrolledForText2(int text2X, int text2Y) {
		this.text2X = text2X;
		this.text2Y = text2Y;
	}

	void updateScrolledForText1(int text1X, int text1Y) {
		this.text1X = text1X;
		this.text1Y = text1Y;
	}

	void updateScrolledForText3(int text3X, int text3Y) {
		this.text3X = text3X;
		this.text3Y = text3Y;
	}

	void updateText1Size(float textSize) {
		text1Size = textSize;
	}

	void updateTextSize2(float textSize) {
		text2Size = textSize;
	}

	void updateText3Size(float textSize) {
		text3Size = textSize;
	}

	void updateColorForText1(int color) {
		text1Color = color;
	}

	void updateColorForText2(int color) {
		text2Color = color;
	}

	void updateColorForText3(int color) {
		text3Color = color;
	}

	private Paint getTextPaint(Typeface typeface, int textColor, float textSize,
		float characterSpacing) {
		Paint textPaint = new Paint();
		textPaint.setAntiAlias(true);
		textPaint.setTextAlign(Align.CENTER);
		textPaint.setColor(textColor);
		textPaint.setTextSize(textSize);
		textPaint.setTypeface(typeface);
		if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
			textPaint.setLetterSpacing(characterSpacing);
		}
		return textPaint;
	}

	void updateLabels(Labels labels) {
		this.labels = labels;
		invalidate();
	}
}
