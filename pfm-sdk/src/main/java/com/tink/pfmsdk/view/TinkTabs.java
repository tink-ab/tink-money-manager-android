package com.tink.pfmsdk.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.google.android.material.tabs.TabLayout;
import com.tink.pfmsdk.R;
import com.tink.pfmsdk.util.DimensionUtils;
import com.tink.pfmsdk.util.ScreenUtils;

public class TinkTabs extends TabLayout {

	private Theme theme;

	public TinkTabs(Context context) {
		super(context);
	}

	public TinkTabs(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TinkTabs(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setTheme(Theme theme) {
		this.theme = theme;

		setBackgroundColor(theme.getBackgroundColor());
		setSelectedTabIndicatorColor(theme.getMarkerColor());

		Context context = getContext();
		setElevation(DimensionUtils.getPixelsFromDP(theme.getElevation(), context));
		setSelectedTabIndicatorHeight(ScreenUtils.dpToPixels(context, 3));
	}

	@Override
	public void addTab(@NonNull Tab tab, int position, boolean setSelected) {
		TinkTextView tinkTextView = new TinkTextView(getContext());
		tinkTextView.setTheme(theme.getTabsTitle());
		tinkTextView.setTextColor(ContextCompat.getColorStateList(tinkTextView.getContext(), R.color.tink_tab_label_color));
		tinkTextView.setText(tab.getText());
		tinkTextView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
		tab.setCustomView(tinkTextView);

		super.addTab(tab, position, setSelected);
	}

	public void updateNameOnFirstTab(String name) {
		TinkTextView tinkTextView = (TinkTextView) getTabAt(0).getCustomView();
		tinkTextView.setText(name);
		getTabAt(0).setCustomView(tinkTextView);
	}

	public void hide() {
		postDelayed(() -> {
			Animation anim = AnimationUtils
				.loadAnimation(getContext(), R.anim.slide_in_from_top);
			startAnimation(anim);
		}, 0);
	}

	public void show() {
		postDelayed(() -> {
			Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_to_top);
			startAnimation(anim);
		}, 0);
	}

	public interface Theme {

		int getBackgroundColor();

		int getMarkerColor();

		int getElevation();

		TinkTextView.Theme getTabsTitle();
	}

}
