package com.tink.pfmsdk.theme;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.tink.pfmsdk.R;
import com.tink.pfmsdk.transaction.SimilarTransactionsFragment;
import com.tink.pfmsdk.view.TinkSnackbar;
import com.tink.pfmsdk.view.TinkToolbar.Theme;
import se.tink.commons.extensions.ContextUtils;
import se.tink.core.models.Category;
import se.tink.core.models.Category.Type;

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
				Type type =
					(category == null) || (category.getType() == null) ? Type.TYPE_UNKKNOWN : category.getType();
				switch (type) {
					case TYPE_EXPENSES:
						id = R.attr.tink_expensesDarkColor;
						break;
					case TYPE_INCOME:
						id = R.attr.tink_incomeDarkColor;
						break;
					case TYPE_TRANSFER:
					case TYPE_UNKKNOWN:
					default:
						id = R.attr.tink_transferDarkColor;
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

		Type type = (category == null) || (category.getType() == null) ? Type.TYPE_UNKKNOWN : category.getType();
		switch (type) {
			case TYPE_EXPENSES:
				id = R.attr.tink_expensesColor;
				break;
			case TYPE_INCOME:
				id = R.attr.tink_incomeColor;
				break;
			case TYPE_TRANSFER:
			case TYPE_UNKKNOWN:
			default:
				id = R.attr.tink_transferColor;
		}
		return ContextUtils.getColorFromAttr(context, id);
	}
}
