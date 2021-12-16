package se.tink.commons.transactions

import com.tink.model.category.Category
import com.tink.model.misc.Amount
import com.tink.model.misc.ExactNumber
import com.tink.model.transaction.Transaction
import org.joda.time.DateTime
import se.tink.commons.R
import se.tink.commons.categories.*
import se.tink.commons.currency.AmountFormatter
import se.tink.commons.extensions.isValid
import se.tink.commons.extensions.toDateTime
import se.tink.utils.DateUtils
import javax.inject.Inject

class TransactionItemFactory @Inject constructor(
    private val amountFormatter: AmountFormatter,
    private val dateUtils: DateUtils
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
                description = category.name,
                label = description,
                amount = amount,
                upcomingTransactionData = null
            )
        }

    fun latestTransactionItemFromTransactionItem(
        transactionItem: ListItem.TransactionItem
    ): ListItem.TransactionItem? =
        with(transactionItem) {
            ListItem.TransactionItem(
                id = id,
                icon = icon,
                label = label,
                description = dateUtils.formatDateHuman(date),
                amount = amount,
                dispensableAmount = dispensableAmount,
                date = date,
                merchantLogoAllowed = merchantLogoAllowed,
                recurring = recurring,
                upcomingTransactionData = upcomingTransactionData
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
        description: String,
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

            ListItem.TransactionItem(
                id = id,
                icon = icon,
                label = label,
                description = description,
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
