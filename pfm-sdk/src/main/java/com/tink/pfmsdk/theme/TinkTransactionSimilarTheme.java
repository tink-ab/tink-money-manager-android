package com.tink.pfmsdk.theme;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.tink.pfmsdk.R;
import com.tink.pfmsdk.transaction.SimilarTransactionsFragment;
import com.tink.pfmsdk.view.TinkSnackbar;
import com.tink.pfmsdk.view.TinkToolbar.Theme;
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
						id = R.color.expensesDark;
						break;
					case TYPE_INCOME:
						id = R.color.incomeDark;
						break;
					case TYPE_TRANSFER:
					case TYPE_UNKKNOWN:
					default:
						id = R.color.transferDark;
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
				id = R.color.expenses;
				break;
			case TYPE_INCOME:
				id = R.color.income;
				break;
			case TYPE_TRANSFER:
			case TYPE_UNKKNOWN:
			default:
				id = R.color.transfer;
		}
		return ContextCompat.getColor(context, id);
	}
}
