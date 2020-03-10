package com.tink.pfmui.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.StringDef
import com.tink.pfmui.util.isUncategorized
import se.tink.commons.categories.enums.CategoryExpenseType
import se.tink.commons.categories.enums.CategoryIncomeType
import se.tink.commons.categories.enums.CategoryTransferType
import se.tink.commons.categories.enums.CategoryType
import kotlin.math.ceil

internal object TinkIcon {
    const val HOME = "\uE900"
    const val HOUSEANDGARDEN = "\uE901"
    const val FOODANDDRINKS = "\uE902"
    const val TRANSPORT = "\uE903"
    const val SHOPPING = "\uE904"
    const val LEISURE = "\uE905"
    const val HEALTHANDBEAUTY = "\uE906"
    const val OTHER = "\uE907"
    const val UNCATEGORIZED = "\uE908"
    const val SAVINGS = "\uE909"
    const val TRANSFER = "\uE90A"
    const val EXCLUDE = "\uE90B"
    const val SALARY = "\uE90C"
    const val PENSION = "\uE90D"
    const val REIMBURSEMENTS = "\uE90E"
    const val BENEFITS = "\uE90F"
    const val FINANCIAL = "\uE910"
    const val OTHERINCOME = "\uE907"
    const val OVERVIEW = "\uE912"
    const val FEED = "\uE913"
    const val ACCOUNTS = "\uE914"
    const val RESIDENCE = "\uE915"
    const val PROFILE = "\uE916"
    const val ARROWUP = "\uE917"
    const val ARROWDOWN = "\uE918"
    const val ARROWLEFT = "\uE919"
    const val ARROWRIGHT = "\uE91A"
    const val ARROWSEMIUP = "\uE91B"
    const val ARROWSEMIDOWN = "\uE91C"
    const val SEARCH = "\uE91D"
    const val PHOTO = "\uE91E"
    const val EDITPEN = "\uE91F"
    const val ARROWSMALLUP = "\uE920"
    const val ARROWSMALLDOWN = "\uE921"
    const val ARROWSMALLRIGHT = "\uE922"
    const val ARROWSMALLLEFT = "\uE923"
    const val CLOSE = "\uE924"
    const val ACCEPT = "\uE925"
    const val ARROWSMALLBACK = "\uE926"
    const val STAR = "\uE927"
    const val STARFILLED = "\uE928"
    const val PLUS = "\uE929"
    const val UPDATE = "\uE92A"
    const val PAUSE = "\uE92B"
    const val TRASHCAN = "\uE92C"
    const val PLAY = "\uE92D"
    const val RELOAD = "\uE92E"
    const val MOREOPTIONS = "\uE92F"
    const val SETTINGS = "\uE930"
    const val EVENT = "\uE931"
    const val ALERT = "\uE932"
    const val DOUBLETRANSACTION = "\uE933"
    const val PAYBILLS = "\uE934"
    const val UPCOMING_TRANSACTION = PAYBILLS
    const val ADDBUDGET = "\uE935"
    const val ADDSAVINGSGOAL = "\uE936"
    const val ADDBANK = "\uE937"
    const val SHAREDACCOUNT = "\uE938"
    const val INFO = "\uE939"
    const val RECEIPT = "\uE93A"
    const val ID_CONTROL = "\uE93C"
    const val CHAT = "\uE93F"
    const val DOCUMENT = "\uE940"

    fun toBitmap(
        @Icon icon: String?,
        iconFont: Typeface?,
        @Dimension size: Float,
        @ColorInt color: Int
    ): Bitmap {
        return toBitmap(icon, iconFont, size, color, 0f)
    }

    private fun toBitmap(
        @Icon icon: String?,
        iconFont: Typeface?,
        @Dimension sizePx: Float,
        @ColorInt color: Int,
        @Dimension paddingPx: Float
    ): Bitmap {
        val paint =
            Paint(Paint.ANTI_ALIAS_FLAG)
        paint.typeface = iconFont
        paint.textSize = sizePx
        paint.color = color
        paint.textAlign = Paint.Align.LEFT
        val baseline = -paint.ascent()
        val width =
            ceil(paint.measureText(icon) + paddingPx * 2.toDouble()).toInt()
        val height =
            ceil(baseline + paint.descent() + (paddingPx * 2).toDouble()).toInt()
        val bitmap = Bitmap.createBitmap(
            width,
            height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        canvas.drawText(icon!!, paddingPx, baseline + paddingPx, paint)
        return bitmap
    }

    @JvmStatic
    fun fromCategoryCode(categoryCode: String): String {
        if (categoryCode.isUncategorized()) {
            return UNCATEGORIZED
        }
        if (categoryCode == CategoryType.EXPENSES.stringCode) {
            return RECEIPT
        }
        if (categoryCode.startsWith(CategoryExpenseType.EXPENSES_TRANSPORT.code)) {
            return TRANSPORT
        }
        if (categoryCode.startsWith(CategoryExpenseType.EXPENSES_FOOD.code)) {
            return FOODANDDRINKS
        }
        if (categoryCode.startsWith(CategoryExpenseType.EXPENSES_HOME.code)) {
            return HOME
        }
        if (categoryCode.startsWith(CategoryExpenseType.EXPENSES_WELLNESS.code)) {
            return HEALTHANDBEAUTY
        }
        if (categoryCode.startsWith(CategoryExpenseType.EXPENSES_ENTERTAINMENT.code)) {
            return LEISURE
        }
        if (categoryCode.startsWith(CategoryExpenseType.EXPENSES_HOUSE.code)) {
            return HOUSEANDGARDEN
        }
        if (categoryCode.startsWith(CategoryExpenseType.EXPENSES_MISC.code)) {
            return OTHER
        }
        if (categoryCode.startsWith(CategoryExpenseType.EXPENSES_SHOPPING.code)) {
            return SHOPPING
        }
        if (categoryCode == CategoryType.INCOME.stringCode) {
            return RECEIPT
        }
        if (categoryCode.startsWith(CategoryIncomeType.INCOME_BENEFITS.code)) {
            return BENEFITS
        }
        if (categoryCode.startsWith(CategoryIncomeType.INCOME_OTHER.code)) {
            return OTHERINCOME
        }
        if (categoryCode.startsWith(CategoryIncomeType.INCOME_FINANCIAL.code)) {
            return FINANCIAL
        }
        if (categoryCode.startsWith(CategoryIncomeType.INCOME_PENSION.code)) {
            return PENSION
        }
        if (categoryCode.startsWith(CategoryIncomeType.INCOME_SALARY.code)) {
            return SALARY
        }
        if (categoryCode.startsWith(CategoryIncomeType.INCOME_REFUND.code)) {
            return REIMBURSEMENTS
        }
        if (categoryCode.startsWith(CategoryTransferType.TRANSFERS_EXCLUDE.code)) {
            return EXCLUDE
        }
        if (categoryCode.startsWith(CategoryTransferType.TRANSFERS_SAVINGS.code)) {
            return SAVINGS
        }
        return if (categoryCode.startsWith(CategoryTransferType.TRANSFERS_OTHER.code)) {
            TRANSFER
        } else UNCATEGORIZED
    }

    @StringDef(
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
    )
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class Icon
}