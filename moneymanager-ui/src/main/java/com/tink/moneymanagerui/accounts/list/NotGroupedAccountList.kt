package com.tink.moneymanagerui.accounts.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tink.model.account.Account
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.accounts.AccountWithImage
import com.tink.moneymanagerui.overview.accounts.setImageResFromAttr
import com.tink.moneymanagerui.util.CurrencyUtils
import com.tink.moneymanagerui.util.ImageUtils
import kotlinx.android.synthetic.main.tink_item_account.view.*
import se.tink.commons.extensions.inflate

class NotGroupedAccountList(val accountClicked: (Account) -> Unit) :
    ListAdapter<AccountWithImage, NotGroupedAccountList.AccountViewHolder>(AccountDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        return AccountViewHolder(parent.inflate(R.layout.tink_item_account))
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(accountWithImage: AccountWithImage) {
            if (accountWithImage.hasIcon) {
                ImageUtils.loadImageFromUrl(itemView.bankLogo, accountWithImage.iconUrl!!, R.attr.tink_icon_ingested_account)
            } else {
                itemView.bankLogo.setImageResFromAttr(R.attr.tink_icon_ingested_account)
            }

            itemView.customerAccountName.text = accountWithImage.account.name
            itemView.customerAccountNumber.text = accountWithImage.account.accountNumber
            itemView.customerAccountBalance.text = CurrencyUtils.formatCurrencyExact(accountWithImage.account.balance)
            itemView.rootView.setOnClickListener {
                accountClicked(accountWithImage.account)
            }
        }
    }
}
