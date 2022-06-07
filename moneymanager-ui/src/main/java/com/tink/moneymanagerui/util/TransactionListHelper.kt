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
        adapter.onTransactionItemClickedListener = { id ->
            navToCategorizationFlow(fragmentCoordinator, id, featureType)
        }

        adapter.onTransactionIconClickedListener = { id, isPending, isEditable ->
            if (isPending && !isEditable) {
                showUneditableDialogMessage(context)
            } else {
                navToCategorizationFlow(fragmentCoordinator, id, featureType)
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
}
