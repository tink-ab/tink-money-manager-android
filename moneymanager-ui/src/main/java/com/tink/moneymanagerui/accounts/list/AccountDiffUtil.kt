package com.tink.moneymanagerui.accounts.list

import androidx.recyclerview.widget.DiffUtil
import com.tink.moneymanagerui.accounts.AccountWithImage

internal object AccountDiffUtil : DiffUtil.ItemCallback<AccountWithImage>() {
    override fun areItemsTheSame(oldItem: AccountWithImage, newItem: AccountWithImage): Boolean {
        return oldItem.account.accountNumber == newItem.account.accountNumber
    }

    override fun areContentsTheSame(oldItem: AccountWithImage, newItem: AccountWithImage): Boolean {
        return oldItem == newItem
    }
}
