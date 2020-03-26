package com.tink.pfmui.theme;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.tink.model.category.Category;
import com.tink.pfmui.R;
import com.tink.pfmui.transaction.SimilarTransactionsFragment;
import com.tink.pfmui.view.TinkSnackbar;
import com.tink.pfmui.view.TinkToolbar.Theme;
import se.tink.commons.extensions.ContextUtils;

public class TinkTransactionSimilarTheme implements SimilarTransactionsFragment.Theme {

	private final Context context;
	private Category category;

	public TinkTransactionSimilarTheme(Context context) {
		this.context = context;
	}

	public void setCategory(Category category) {
		this.category = category;
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
				int id;
				switch (category.getType()) {
					case EXPENSE:
						id = R.attr.tink_expensesDarkColor;
						break;
					case INCOME:
						id = R.attr.tink_incomeDarkColor;
						break;
					case TRANSFER:
					default:
						id = R.attr.tink_transferColor;
				}
				return ContextCompat.getColor(context, id);
			}
		};
	}

	@Override
	public TinkSnackbar.Theme getSnackbarErrorTheme() {
		return new TinkErrorSnackbarTheme(context);
	}

	private int getColor() {
		int id;
		switch (category.getType()) {
			case EXPENSE:
				id = R.attr.tink_expensesColor;
				break;
			case INCOME:
				id = R.attr.tink_incomeColor;
				break;
			case TRANSFER:
			default:
				id = R.attr.tink_transferColor;
		}
		return ContextUtils.getColorFromAttr(context, id);
	}
}
