package se.tink.commons.transactions

import android.content.Context
import org.joda.time.DateTime
import se.tink.android.di.application.ApplicationScoped
import se.tink.commons.R
import se.tink.commons.categories.getIcon
import se.tink.commons.categories.iconBackgroundColor
import se.tink.commons.categories.iconColor
import se.tink.commons.categories.isExcluded
import se.tink.commons.categories.isUncategorized
import se.tink.commons.currency.AmountFormatter
import se.tink.core.models.Category
import se.tink.core.models.misc.Amount
import se.tink.core.models.misc.ExactNumber
import se.tink.core.models.transaction.Transaction
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
                isUpcoming = isUpcoming,
                category = category,
                date = timestamp,
                label = description,
                amount = amount,
                dispensableAmount = dispensableAmount,
                upcomingTransactionData = if (isUpcoming) {
                    ListItem.TransactionItem.UpcomingTransactionData(
                        pending = isPending,
                        editable = isEditable,
                        transferId = details?.transferId ?: ""
                    )
                } else {
                    null
                }
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
                    date = dateUtils.formatDateHuman(timestamp),
                    merchantLogoAllowed = !category.code.isUncategorized() && !isUpcoming && !category.code.isExcluded(),
                    selected = isSelected
                )
            } else {
                null
            }
        }

    fun createItem(
        id: String,
        isUpcoming: Boolean,
        category: Category,
        date: DateTime,
        label: String,
        amount: Amount,
        dispensableAmount: Amount = Amount(ExactNumber.ZERO, "SEK"), // TODO:PFMSDK: Remove dispensableAmount
        upcomingTransactionData: ListItem.TransactionItem.UpcomingTransactionData? = null
    ): ListItem.TransactionItem? =
        if (amount.isValid && dispensableAmount.isValid) {
            val icon = if (isUpcoming) {
                ListItem.TransactionItem.Icon(
                    R.drawable.ic_all_expenses,
                    R.color.upcoming_transaction,
                    R.color.upcomingLight
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
