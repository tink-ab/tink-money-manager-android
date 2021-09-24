package se.tink.commons.transactions

import android.content.Context
import com.tink.model.category.Category
import com.tink.model.misc.Amount
import com.tink.model.misc.ExactNumber
import com.tink.model.transaction.Transaction
import org.joda.time.DateTime
import se.tink.android.di.application.ApplicationScoped
import se.tink.commons.R
import se.tink.commons.categories.getIcon
import se.tink.commons.categories.iconBackgroundColor
import se.tink.commons.categories.iconColor
import se.tink.commons.categories.isExcluded
import se.tink.commons.categories.isUncategorized
import se.tink.commons.currency.AmountFormatter
import se.tink.commons.extensions.isValid
import se.tink.commons.extensions.toDateTime
import se.tink.utils.DateUtils
import javax.inject.Inject

class TransactionItemFactory @Inject constructor(
    private val amountFormatter: AmountFormatter,
    private val dateUtils: DateUtils,
    @ApplicationScoped private val context: Context
) {

    fun fromTransaction(
        transaction: Transaction,
        category: Category
    ): ListItem.TransactionItem? =
        with(transaction) {
            createItem(
                id = id,
                isUpcoming = false,
                category = category,
                date = this.date.toDateTime(), //TODO: Core setup
                label = description,
                amount = amount,
                upcomingTransactionData = null
            )
        }

    fun similarTransactionItemFromTransaction(
        transaction: Transaction,
        category: Category,
        isSelected: Boolean = true
    ): SimilarTransactionsAdapter.SimilarTransactionItem? =
        with (transaction) {
            if (amount.isValid) {
                val icon = with(category) {
                    ListItem.TransactionItem.Icon(getIcon(), iconColor(), iconBackgroundColor())
                }
                SimilarTransactionsAdapter.SimilarTransactionItem(
                    id = id,
                    icon = icon,
                    label = description,
                    amount = amountFormatter.format(amount = amount, explicitlyPositive = true),
                    description = category.name,
                    date = dateUtils.getDateWithYear(date.toDateTime()),
                    merchantLogoAllowed = !category.code.isUncategorized() && !category.code.isExcluded(),
                    selected = isSelected
                )
            } else {
                null
            }
        }

    fun createItem(
        id: String,
        isUpcoming: Boolean, // TODO: PFMSDK: Remove upcoming flag
        category: Category,
        date: DateTime,
        label: String,
        amount: Amount,
        dispensableAmount: Amount = Amount(ExactNumber(0,0), "SEK"), // TODO:PFMSDK: Remove dispensableAmount
        upcomingTransactionData: ListItem.TransactionItem.UpcomingTransactionData? = null // TODO: PFMSDK: Remove upcoming transaction data
    ): ListItem.TransactionItem? =
        if (amount.isValid && dispensableAmount.isValid) {
            val icon = if (isUpcoming) {
                // TODO: PFMSDK: Remove upcoming transaction UI setting
                ListItem.TransactionItem.Icon(
                    R.attr.tink_icon_category_all_expenses,
                    R.attr.tink_transferColor,
                    R.attr.tink_transferLightColor
                )
            } else {
                with(category) {
                    ListItem.TransactionItem.Icon(getIcon(), iconColor(), iconBackgroundColor())
                }
            }

            val descriptionText = category.name

            ListItem.TransactionItem(
                id = id,
                icon = icon,
                label = label,
                description = descriptionText,
                amount = amountFormatter.format(amount = amount, explicitlyPositive = true),
                dispensableAmount = amountFormatter.format(
                    amount = dispensableAmount,
                    explicitlyPositive = true
                ),
                date = date,
                merchantLogoAllowed = !category.code.isUncategorized() && !isUpcoming && !category.code.isExcluded(),
                upcomingTransactionData = upcomingTransactionData
            )
        } else {
            null
        }
}
