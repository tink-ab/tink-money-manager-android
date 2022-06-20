package com.tink.moneymanagerui.util

import android.app.AlertDialog
import android.content.Context
import com.tink.moneymanagerui.FragmentAnimationFlags
import com.tink.moneymanagerui.FragmentCoordinator
import com.tink.moneymanagerui.MoneyManagerFeatureType
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.transaction.CategorizationFlowFragment
import se.tink.commons.transactions.TransactionItemListAdapter

internal class TransactionListHelper {

    fun navToCategorizationOrShowUneditableMsg(
        fragmentCoordinator: FragmentCoordinator,
        context: Context,
        adapter: TransactionItemListAdapter,
        featureType: MoneyManagerFeatureType?
    ) {
        adapter.onTransactionItemClickedListener = { data ->
            if (data.isPending && !data.isEditable) {
                showUneditableDialogMessage(context)
            } else {
                navToCategorizationFlow(fragmentCoordinator, data.id, featureType)
            }
        }

        adapter.onTransactionIconClickedListener = { data ->
            if (data.isPending && !data.isEditable) {
                showUneditableDialogMessage(context)
            } else {
                navToCategorizationFlow(fragmentCoordinator, data.id, featureType)
            }
        }
    }

    private fun navToCategorizationFlow(
        fragmentCoordinator: FragmentCoordinator,
        id: String,
        featureType: MoneyManagerFeatureType?
    ) {
        CategorizationFlowFragment
            .newInstance(id, featureType)
            .also {
                fragmentCoordinator.replace(
                    it,
                    animation = FragmentAnimationFlags.FADE_IN_ONLY
                )
            }
    }

    private fun showUneditableDialogMessage(context: Context) {
        val builder = AlertDialog.Builder(context)
        with(builder) {
            setTitle(R.string.tink_transaction_pending_uneditable_dialog_title)
            setMessage(
                R.string.tink_transaction_pending_uneditable_dialog_body
            )
            setPositiveButton(context.getString(R.string.tink_general_ok)) { _, _ -> }
            show()
        }
    }

    fun isEditableOnPendingValue(context: Context) = context.getSharedPreferences(
        "ARG_IS_EDITABLE_ON_PENDING_TRANSACTION",
        Context.MODE_PRIVATE
    ).getBoolean("IS_EDITABLE_ON_PENDING_TRANSACTION", true)
}
