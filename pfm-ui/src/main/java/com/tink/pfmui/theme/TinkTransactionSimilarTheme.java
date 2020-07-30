package com.tink.pfmui.theme;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.tink.model.category.Category;
import com.tink.pfmui.R;
import com.tink.pfmui.transaction.SimilarTransactionsFragment;
import com.tink.pfmui.util.CategoryUtils;
import com.tink.pfmui.view.TinkSnackbar;
import com.tink.pfmui.view.TinkToolbar.Theme;
import se.tink.commons.extensions.ContextUtils;

public class TinkTransactionSimilarTheme implements SimilarTransactionsFragment.Theme {

	private final Context context;
	private String categoryCode;

	public TinkTransactionSimilarTheme(Context context) {
		this.context = context;
	}

	public void setCategory(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	@Override
	public Theme getToolbarTheme() {
		return new TinkDefaultToolbarTheme(context) {
			@Override
			public int getBackgroundColor() {
				return getColor();
			}
		};
	}

	public StatusBarTheme getStatusBarTheme() {
		return new TinkDefaultStatusBarTheme(context) {
			@Override
			public int getStatusBarColor() {
				return getColor();
			}
		};
	}

	@Override
	public TinkSnackbar.Theme getSnackbarErrorTheme() {
		return new TinkErrorSnackbarTheme(context);
	}

	private int getColor() {
		int id = CategoryUtils.getColorId(categoryCode);
		return ContextUtils.getColorFromAttr(context, id);
	}
}
