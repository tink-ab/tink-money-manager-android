package com.tink.pfmsdk.view;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.StringDef;
import com.tink.pfmsdk.util.CategoryUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import se.tink.commons.categories.enums.CategoryExpenseType;
import se.tink.commons.categories.enums.CategoryIncomeType;
import se.tink.commons.categories.enums.CategoryTransferType;
import se.tink.commons.categories.enums.CategoryType;

public class TinkIcon {

	public static final String HOME = "\uE900";
	public static final String HOUSEANDGARDEN = "\uE901";
	public static final String FOODANDDRINKS = "\uE902";
	public static final String TRANSPORT = "\uE903";
	public static final String SHOPPING = "\uE904";
	public static final String LEISURE = "\uE905";
	public static final String HEALTHANDBEAUTY = "\uE906";
	public static final String OTHER = "\uE907";
	public static final String UNCATEGORIZED = "\uE908";
	public static final String SAVINGS = "\uE909";
	public static final String TRANSFER = "\uE90A";
	public static final String EXCLUDE = "\uE90B";
	public static final String SALARY = "\uE90C";
	public static final String PENSION = "\uE90D";
	public static final String REIMBURSEMENTS = "\uE90E";
	public static final String BENEFITS = "\uE90F";
	public static final String FINANCIAL = "\uE910";
	public static final String OTHERINCOME = "\uE907";
	public static final String OVERVIEW = "\uE912";
	public static final String FEED = "\uE913";
	public static final String ACCOUNTS = "\uE914";
	public static final String RESIDENCE = "\uE915";
	public static final String PROFILE = "\uE916";
	public static final String ARROWUP = "\uE917";
	public static final String ARROWDOWN = "\uE918";
	public static final String ARROWLEFT = "\uE919";
	public static final String ARROWRIGHT = "\uE91A";
	public static final String ARROWSEMIUP = "\uE91B";
	public static final String ARROWSEMIDOWN = "\uE91C";
	public static final String SEARCH = "\uE91D";
	public static final String PHOTO = "\uE91E";
	public static final String EDITPEN = "\uE91F";
	public static final String ARROWSMALLUP = "\uE920";
	public static final String ARROWSMALLDOWN = "\uE921";
	public static final String ARROWSMALLRIGHT = "\uE922";
	public static final String ARROWSMALLLEFT = "\uE923";
	public static final String CLOSE = "\uE924";
	public static final String ACCEPT = "\uE925";
	public static final String ARROWSMALLBACK = "\uE926";
	public static final String STAR = "\uE927";
	public static final String STARFILLED = "\uE928";
	public static final String PLUS = "\uE929";
	public static final String UPDATE = "\uE92A";
	public static final String PAUSE = "\uE92B";
	public static final String TRASHCAN = "\uE92C";
	public static final String PLAY = "\uE92D";
	public static final String RELOAD = "\uE92E";
	public static final String MOREOPTIONS = "\uE92F";
	public static final String SETTINGS = "\uE930";
	public static final String EVENT = "\uE931";
	public static final String ALERT = "\uE932";
	public static final String DOUBLETRANSACTION = "\uE933";
	public static final String PAYBILLS = "\uE934";
	public static final String UPCOMING_TRANSACTION = PAYBILLS;
	public static final String ADDBUDGET = "\uE935";
	public static final String ADDSAVINGSGOAL = "\uE936";
	public static final String ADDBANK = "\uE937";
	public static final String SHAREDACCOUNT = "\uE938";
	public static final String INFO = "\uE939";
	public static final String RECEIPT = "\uE93A";
	public static final String ID_CONTROL = "\uE93C";
	public static final String CHAT = "\uE93F";
	public static final String DOCUMENT = "\uE940";

	@StringDef({
		CHAT,
		HOME,
		HOUSEANDGARDEN,
		FOODANDDRINKS,
		TRANSPORT,
		SHOPPING,
		LEISURE,
		HEALTHANDBEAUTY,
		OTHER,
		UNCATEGORIZED,
		SAVINGS,
		TRANSFER,
		EXCLUDE,
		SALARY,
		PENSION,
		REIMBURSEMENTS,
		BENEFITS,
		FINANCIAL,
		OTHERINCOME,
		OVERVIEW,
		FEED,
		ACCOUNTS,
		RESIDENCE,
		PROFILE,
		ARROWUP,
		ARROWDOWN,
		ARROWLEFT,
		ARROWRIGHT,
		ARROWSEMIUP,
		ARROWSEMIDOWN,
		SEARCH,
		PHOTO,
		EDITPEN,
		ARROWSMALLUP,
		ARROWSMALLDOWN,
		ARROWSMALLRIGHT,
		ARROWSMALLLEFT,
		CLOSE,
		ACCEPT,
		ARROWSMALLBACK,
		STAR,
		STARFILLED,
		PLUS,
		UPDATE,
		PAUSE,
		TRASHCAN,
		PLAY,
		RELOAD,
		MOREOPTIONS,
		SETTINGS,
		EVENT,
		ALERT,
		DOUBLETRANSACTION,
		PAYBILLS,
		ADDBUDGET,
		ADDSAVINGSGOAL,
		ADDBANK,
		SHAREDACCOUNT,
		INFO,
		RECEIPT,
		ID_CONTROL,
		DOCUMENT
	})
	@Retention(RetentionPolicy.SOURCE)
	public @interface Icon {

	}

	public static Bitmap toBitmap(
		@Icon String icon,
		Typeface iconFont,
		@Dimension float size,
		@ColorInt int color) {
		return toBitmap(icon, iconFont, size, color, 0);
	}

	public static Bitmap toBitmap(
		@Icon String icon,
		Typeface iconFont,
		@Dimension float sizePx,
		@ColorInt int color,
		@Dimension float paddingPx) {

		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setTypeface(iconFont);
		paint.setTextSize(sizePx);
		paint.setColor(color);
		paint.setTextAlign(Align.LEFT);

		float baseline = -paint.ascent();
		int width = (int) Math.ceil(paint.measureText(icon) + paddingPx * 2);
		int height = (int) Math.ceil(baseline + paint.descent() + paddingPx * 2);

		Bitmap bitmap = Bitmap.createBitmap(
			width,
			height,
			Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);

		canvas.drawText(icon, paddingPx, baseline + paddingPx, paint);
		return bitmap;
	}

	public static String fromCategoryCode(String categoryCode) {
		if (CategoryUtils.isUncategorized(categoryCode)) {
			return UNCATEGORIZED;
		}

		if (Objects.equals(categoryCode, CategoryType.EXPENSES.getStringCode()))

		{
			return RECEIPT;
		}
		if (categoryCode.startsWith(CategoryExpenseType.EXPENSES_TRANSPORT.getCode()))

		{
			return TRANSPORT;
		}
		if (categoryCode.startsWith(CategoryExpenseType.EXPENSES_FOOD.getCode()))

		{
			return FOODANDDRINKS;
		}
		if (categoryCode.startsWith(CategoryExpenseType.EXPENSES_HOME.getCode()))

		{
			return HOME;
		}
		if (categoryCode.startsWith(CategoryExpenseType.EXPENSES_WELLNESS.getCode()))

		{
			return HEALTHANDBEAUTY;
		}
		if (categoryCode.startsWith(CategoryExpenseType.EXPENSES_ENTERTAINMENT.getCode()))

		{
			return LEISURE;
		}
		if (categoryCode.startsWith(CategoryExpenseType.EXPENSES_HOUSE.getCode()))

		{
			return HOUSEANDGARDEN;
		}
		if (categoryCode.startsWith(CategoryExpenseType.EXPENSES_MISC.getCode()))

		{
			return OTHER;
		}
		if (categoryCode.startsWith(CategoryExpenseType.EXPENSES_SHOPPING.getCode()))

		{
			return SHOPPING;
		}

		if (Objects.equals(categoryCode, CategoryType.INCOME.getStringCode()))

		{
			return RECEIPT;
		}
		if (categoryCode.startsWith(CategoryIncomeType.INCOME_BENEFITS.getCode()))

		{
			return BENEFITS;
		}
		if (categoryCode.startsWith(CategoryIncomeType.INCOME_OTHER.getCode()))

		{
			return OTHERINCOME;
		}
		if (categoryCode.startsWith(CategoryIncomeType.INCOME_FINANCIAL.getCode()))

		{
			return FINANCIAL;
		}
		if (categoryCode.startsWith(CategoryIncomeType.INCOME_PENSION.getCode()))

		{
			return PENSION;
		}
		if (categoryCode.startsWith(CategoryIncomeType.INCOME_SALARY.getCode()))

		{
			return SALARY;
		}
		if (categoryCode.startsWith(CategoryIncomeType.INCOME_REFUND.getCode()))

		{
			return REIMBURSEMENTS;
		}

		if (categoryCode.startsWith(CategoryTransferType.TRANSFERS_EXCLUDE.getCode()))

		{
			return EXCLUDE;
		}
		if (categoryCode.startsWith(CategoryTransferType.TRANSFERS_SAVINGS.getCode()))

		{
			return SAVINGS;
		}
		if (categoryCode.startsWith(CategoryTransferType.TRANSFERS_OTHER.getCode()))

		{
			return TRANSFER;
		}

		return UNCATEGORIZED;
	}
}
