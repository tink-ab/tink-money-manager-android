package com.tink.pfmsdk.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import com.tink.pfmsdk.R;
import com.tink.pfmsdk.util.DimensionUtils;

public class TinkToolbar extends Toolbar {

	public static final String TAG = "TinkToolbar";

	private Theme theme;

	public TinkToolbar(Context context) {
		super(context);
	}

	public TinkToolbar(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public TinkToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setCustomView(View view) {
		ViewGroup customViewContainer = findViewById(R.id.tink_toolbar_custom_view_container);
		if (customViewContainer == null) {
			customViewContainer = new FrameLayout(getContext());
			customViewContainer.setId(R.id.tink_toolbar_custom_view_container);
			customViewContainer.setLayoutParams(
				new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT));

			addView(customViewContainer);
		}
		customViewContainer.removeAllViews();
		customViewContainer.addView(view);
	}

	public void clearCustomView() {
		ViewGroup customViewContainer = findViewById(R.id.tink_toolbar_custom_view_container);
		if (customViewContainer != null) {
			customViewContainer.removeAllViews();
			removeView(customViewContainer);
		}
	}

	@Override
	public void setNavigationIcon(@Nullable Drawable icon) {
		if (icon != null && theme != null) {
			icon = icon.mutate();
			icon.setColorFilter(theme.getTitleColor(), PorterDuff.Mode.SRC_ATOP);
		}
		super.setNavigationIcon(icon);
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
		setTitleTextAppearance(getContext(), R.style.mega);
		Theme.ToolbarTextTheme actionButtonTheme = theme.getActionButtonTheme();

		setNavigationIcon(getNavigationIcon());

		setBackgroundColor(theme.getBackgroundColor());
		setTitleTextColor(theme.getTitleColor());

		setElevation(DimensionUtils.getPixelsFromDP(theme.getElevationDP(), getContext()));

		Drawable overflowIcon = getOverflowIcon();
		overflowIcon.setColorFilter(actionButtonTheme.getTextColor(), PorterDuff.Mode.SRC_ATOP);
		setOverflowIcon(overflowIcon);

		for (int i = 0; i < getMenu().size(); i++) {
			MenuItem item = getMenu().getItem(i);
			Drawable itemIcon = item.getIcon();
			if (itemIcon != null) {
				itemIcon.setColorFilter(actionButtonTheme.getTextColor(), PorterDuff.Mode.SRC_ATOP);
				item.setIcon(itemIcon);
			}
		}

		ActionMenuView menu = null;

		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			if (child instanceof ActionMenuView) {
				menu = (ActionMenuView) child;
			}
		}
		if (menu != null) {
			final ActionMenuView finalMenu = menu;
			final Theme.ToolbarTextTheme finalTheme = actionButtonTheme;
			ViewCompat.postOnAnimation(this, () -> styleMenu(finalMenu, finalTheme));
		}
	}

	private void styleMenu(ActionMenuView menu, Theme.ToolbarTextTheme theme) {
		//style action items
		for (int i = 0; i < menu.getChildCount(); i++) {
			View child = menu.getChildAt(i);
			if (child instanceof ActionMenuItemView) {
				ActionMenuItemView itemView = ((ActionMenuItemView) child);
				itemView.setAllCaps(false);
				styleTextView(itemView, theme);
			}
		}
	}

	private void styleTextView(TextView textView, Theme.ToolbarTextTheme theme) {
		textView.setTextColor(theme.getTextColor());
		textView.setTypeface(theme.getFont());
		textView.setLetterSpacing(theme.getLetterSpacing());
		textView.setAllCaps(false);
		if (theme.shouldChangeTextSize()) {
			textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, theme.getTextSizeInPx());
		}
	}

	public interface Theme {

		int getBackgroundColor();

		@ColorInt
		int getTitleColor();

		ToolbarTextTheme getActionButtonTheme();

		float getElevationDP();

		interface ToolbarTextTheme extends TinkTextView.Theme {

			boolean shouldChangeTextSize();

			float getTextSizeInPx();

			float getLetterSpacing();
		}
	}
}