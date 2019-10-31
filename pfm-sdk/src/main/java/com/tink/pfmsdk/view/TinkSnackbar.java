package com.tink.pfmsdk.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.snackbar.SnackbarContentLayout;
import com.tink.pfmsdk.R;
import com.tink.pfmsdk.util.DimensionUtils;

public class TinkSnackbar {

	private static final float LOADER_SIZE_DP = 35;
	private static final float ICON_SIZE_DP = 35;

	private SnackbarContentLayout snackbarContentLayout;

	private Snackbar wrappedSnackbar;

	private Context context;

	private ProgressBar loadingIndicator;

	private Theme theme;

	private TinkSnackbar(Snackbar snackbar, Theme theme) {
		this.wrappedSnackbar = snackbar;
		this.context = snackbar.getContext();
		this.theme = theme;
		snackbarContentLayout = findRecursive(wrappedSnackbar.getView());

		wrappedSnackbar.getView().setBackgroundColor(theme.getBackgroundColor());

		TextView messageView = snackbarContentLayout.findViewById(R.id.snackbar_text);
		TextView actionView = snackbarContentLayout.findViewById(R.id.snackbar_action);

		styleText(messageView, theme.getTextTheme());
		styleText(actionView, theme.getButtonTheme());
	}

	public boolean isShown() {
		return wrappedSnackbar.isShown();
	}

	public static TinkSnackbar make(View view, String text, int duration, Theme theme) {
		Snackbar snackbar = Snackbar.make(view, text, duration);
		LayoutParams lp = snackbar.getView().getLayoutParams();

		// TODO: PFMSDK: How can he handle this with the app's bottom navigation view?
		/*boolean isBottomNavigationBar = view.findViewById(R.id.bottomNavigation) != null
			&& lp instanceof CoordinatorLayout.LayoutParams;
		if (isBottomNavigationBar) {
			CoordinatorLayout.LayoutParams clp = (CoordinatorLayout.LayoutParams) lp;
			clp.setAnchorId(R.id.bottomNavigation);
			clp.anchorGravity = Gravity.TOP;
			clp.gravity = Gravity.TOP;
			snackbar.getView().setLayoutParams(clp);
		}*/
		TinkSnackbar instance = new TinkSnackbar(snackbar, theme);
		View snackbarView = findRecursive(snackbar.getView());
		if (snackbarView != null) {
			snackbarView
				.setMinimumHeight((int) view.getResources().getDimension(R.dimen.snackbar_height));
		}
		return instance;
	}

	public void setLoading(boolean loading) {
		if (!loading) {
			if (loadingIndicator != null) {
				loadingIndicator.setVisibility(View.GONE);
			}
		} else {
			if (loadingIndicator == null) {
				initLoader();
			}
			loadingIndicator.setVisibility(View.VISIBLE);
		}
	}

	public void setAction(String text, View.OnClickListener listener) {
		wrappedSnackbar.setAction(text, listener);
	}

	public void setIcon(Drawable drawable) {
		ImageView icon = new ImageView(context);
		icon.setImageDrawable(drawable);
		int iconSize = Math.round(DimensionUtils.getPixelsFromDP(ICON_SIZE_DP, context));
		LinearLayout.LayoutParams iconLayoutParams = new LinearLayout.LayoutParams(
			new LayoutParams(iconSize, iconSize));
		iconLayoutParams.gravity = Gravity.CENTER_VERTICAL;
		icon.setLayoutParams(iconLayoutParams);
		snackbarContentLayout.addView(icon);
	}

	public void show() {
		wrappedSnackbar.show();
	}

	public Snackbar getWrappedSnackbar() {
		return wrappedSnackbar;
	}

	private static SnackbarContentLayout findRecursive(View root) {
		if (root instanceof SnackbarContentLayout) {
			return (SnackbarContentLayout) root;
		} else if (root instanceof ViewGroup) {
			ViewGroup viewGroup = (ViewGroup) root;
			for (int i = 0; i < viewGroup.getChildCount(); i++) {
				View child = viewGroup.getChildAt(i);
				SnackbarContentLayout foundInChildren = findRecursive(child);
				if (foundInChildren != null) {
					return foundInChildren;
				}
			}
		}
		return null;
	}

	private static void styleText(TextView textView, TinkTextView.Theme theme) {
		textView.setTypeface(theme.getFont());
		textView.setTextColor(theme.getTextColor());
	}

	private void initLoader() {
		loadingIndicator = new ProgressBar(context);
		int progressSize = Math.round(DimensionUtils.getPixelsFromDP(LOADER_SIZE_DP, context));
		LinearLayout.LayoutParams progressLayoutParams = new LinearLayout.LayoutParams(
			new LayoutParams(progressSize, progressSize));
		progressLayoutParams.gravity = Gravity.CENTER_VERTICAL;
		loadingIndicator.setLayoutParams(progressLayoutParams);
		loadingIndicator.getIndeterminateDrawable().setColorFilter(theme.getLoadingIndicatorColor(),
			android.graphics.PorterDuff.Mode.SRC_IN);
		loadingIndicator.setVisibility(View.GONE);
		snackbarContentLayout.addView(loadingIndicator);
	}

	public void dismiss() {
		wrappedSnackbar.dismiss();
	}

	public interface Theme {

		String MESSAGE_THEME = "message_theme";
		String ERROR_THEME = "error_theme";

		TinkTextView.Theme getTextTheme();

		TinkTextView.Theme getButtonTheme();

		int getBackgroundColor();

		int getLoadingIndicatorColor();
	}
}
