package com.tink.pfmsdk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.tink.pfmsdk.R;
import com.tink.pfmsdk.util.DimensionUtils;

public class CardGroupHeader extends RelativeLayout {

	private TinkTextView header;
	private TinkTextView button;
	private Theme theme;

	public CardGroupHeader(Context context) {
		super(context);
		init(context);
	}

	public CardGroupHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public CardGroupHeader(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		header = new TinkTextView(context);
		button = new TinkTextView(context);

		int topPadding = (int) DimensionUtils.getPixelsFromDP(20, context);
		int sidePadding = (int) getResources().getDimension(R.dimen.horizontal_margin);
		int bottomPadding = (int) DimensionUtils.getPixelsFromDP(8, context);

		setPadding(sidePadding, topPadding, sidePadding, bottomPadding);

		LayoutParams headerLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
			ViewGroup.LayoutParams.WRAP_CONTENT);
		LayoutParams buttonLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
			ViewGroup.LayoutParams.WRAP_CONTENT);
		buttonLp.topMargin = (int) DimensionUtils.getPixelsFromDP(3, getContext());
		buttonLp.addRule(ALIGN_PARENT_RIGHT);

		addView(header, headerLp);
		addView(button, buttonLp);
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
		header.setTheme(theme.getHeaderTheme());
		button.setTheme(theme.getButtonTheme());
		GroupPositionUtils.applyGroupPosition(GroupPosition.Top, this, theme.getBackground());
	}

	public void setHeaderText(String text) {
		header.setText(text);
	}

	public void setButtonText(String text) {
		button.setText(text);
	}

	public void setButtonOnClickListener(OnClickListener onClickListener) {
		button.setOnClickListener(onClickListener);
	}

	public void setEmpty(boolean isEmpty) {
		if (isEmpty) {
			GroupPositionUtils
				.applyGroupPosition(GroupPosition.Alone, this, theme.getBackground());

		} else {
			GroupPositionUtils
				.applyGroupPosition(GroupPosition.Top, this, theme.getBackground());

		}
	}

	public interface Theme {

		TinkTextView.Theme getHeaderTheme();

		TinkTextView.Theme getButtonTheme();

		GroupPositionUtils.Theme getBackground();
	}
}
