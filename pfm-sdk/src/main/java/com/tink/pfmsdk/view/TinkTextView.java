package com.tink.pfmsdk.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.appcompat.widget.AppCompatTextView;
import com.google.common.base.Strings;
import com.tink.pfmsdk.util.DimensionUtils;

public class TinkTextView extends AppCompatTextView {

	private Theme theme;
	private Context context;
	private int compundPaddingTop;
	private boolean ignoreBaseLine;
	private BaseLineHelper helper;

	public TinkTextView(Context context) {
		super(context);
		initialize(context, null);
	}

	public TinkTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize(context, attrs);
	}

	public TinkTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initialize(context, attrs);
	}

	private void initialize(Context context, AttributeSet attrs) {
		float materialStep = TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
		helper = new BaseLineHelper(materialStep);

		this.context = context;

		setIncludeFontPadding(false);

		if (attrs != null) {
			// TODO: PFMSDK: Resource IDs cannot be used for switch expressions in a library module
			//setCustomAttributes(context, attrs);
		}
	}

	// TODO: PFMSDK: Resource IDs cannot be used for switch expressions in a library module
	/*private void setCustomAttributes(Context context, AttributeSet attrs) {
		@SuppressLint("CustomViewStyleable")
		TypedArray arrayAttributes = context
			.obtainStyledAttributes(attrs, R.styleable.TinkWidgetStyle);

		try {
			int n = arrayAttributes.getIndexCount();
			for (int i = 0; i < n; i++) {
				int attr = arrayAttributes.getIndex(i);
				switch (attr) {
					case R.styleable.TinkWidgetStyle_ignore_baseLine:
						ignoreBaseLine = true;
						break;
					case R.styleable.TinkWidgetStyle_lineHeight:
						float dimension = arrayAttributes.getDimension(attr, 0);
						setLineSpacing(dimension, 0);
						break;
				}
			}
		} finally {
			arrayAttributes.recycle();
		}
	}*/

	public void setTheme(Theme theme) {
		this.theme = theme;

		setTypeface(theme.getFont());
		setTextColor(theme.getTextColor());
		setTextSize(TypedValue.COMPLEX_UNIT_PX, theme.getTextSize());
		setLetterSpacing(theme.getSpacing());
		setLineSpacing(theme.getLineHeight(), 0);

		String text = getText().toString();
		if (theme.toUpperCase() && !Strings.isNullOrEmpty(text)) {
			text = text.toUpperCase();
			setText(text);
		}
	}

	@Override
	public int getCompoundPaddingTop() {
		return super.getCompoundPaddingTop() + compundPaddingTop;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		compundPaddingTop = 0;

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		if (ignoreBaseLine) {
			return;
		}

		float oneDpExtraHeightToAvoidClipping = DimensionUtils.getPixelsFromDP(1f, context);

		compundPaddingTop = helper.getCompoundTopPaddingToPutBaselineOnGrid(getBaseline());
		int height = getMeasuredHeight();
		height += helper.putHeightOnGrid(height);
		height += oneDpExtraHeightToAvoidClipping;
		setMeasuredDimension(getMeasuredWidth(), height);
	}

	public void setIgnoreBaseline(boolean ignoreBaseline) {
		this.ignoreBaseLine = ignoreBaseline;
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		if (theme != null && theme.toUpperCase()) {
			text = text.toString().toUpperCase();
		}
		super.setText(text, type);
	}

	public interface Theme {

		int getTextColor();

		Typeface getFont();

		float getTextSize();

		float getLineHeight();

		float getSpacing();

		boolean toUpperCase();
	}

}

