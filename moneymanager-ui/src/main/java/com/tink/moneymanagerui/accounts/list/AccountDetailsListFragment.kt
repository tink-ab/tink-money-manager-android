package com.tink.moneymanagerui.accounts.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tink.model.account.Account
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.extensions.visibleIf
import com.tink.moneymanagerui.overview.accounts.AccountDetailsFragment
import com.tink.moneymanagerui.overview.accounts.AccountDetailsScreenType
import com.tink.moneymanagerui.overview.accounts.AccountWithImage
import com.tink.moneymanagerui.overview.accounts.AccountsViewModel
import com.tink.service.network.ErrorState
import com.tink.service.network.LoadingState
import com.tink.service.network.SuccessState
import kotlinx.android.synthetic.main.tink_fragment_all_accounts_list.*

class AccountDetailsListFragment : BaseFragment() {

    private lateinit var viewModel: AccountsViewModel

    private val accountsAdapter = NotGroupedAccountList {
        fragmentCoordinator.replace(AccountDetailsFragment.newInstance(it.id))
    }

    private val groupedAccountsAdapter = GroupedAccountList {
        fragmentCoordinator.replace(AccountDetailsFragment.newInstance(it.id))
    }

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[AccountsViewModel::class.java]
    }

    public override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)

        title = getString(R.string.tink_overview_toolbar_title)

        accountsNotGroupedRoot.visibleIf { viewModel.accountDetailsDisplayMode == AccountDetailsScreenType.NOT_GROUPED_ACCOUNTS_LIST }
        accountsGroupedRoot.visibleIf { viewModel.accountDetailsDisplayMode == AccountDetailsScreenType.GROUPED_ACCOUNTS_LIST }

        allAccountsList.layoutManager = LinearLayoutManager(context)
        allAccountsList.adapter = accountsAdapter
        allAccountsList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        groupedAccountsList.layoutManager = LinearLayoutManager(context)
        groupedAccountsList.adapter = groupedAccountsAdapter
        //groupedAccountsList.isNestedScrollingEnabled = true

        viewModel.accountsState.observe(
            viewLifecycleOwner,
            Observer { accountsState ->
                allAccountsList.visibleIf { accountsState is SuccessState }
                groupedAccountsList.visibleIf { accountsState is SuccessState }
                accountErrorText.visibleIf { accountsState is ErrorState }
                accountsProgressBar.visibleIf { accountsState is LoadingState }
                if (accountsState is SuccessState) {

                    accountsAdapter.submitList(accountsState.data.map {
                        AccountWithImage(it, null)
                    })

                    val list = accountsState.data.map {
                        AccountWithImage(it, null)
                    }.toMutableList()
                    // Add test data
                    val a0 = list?.get(0)
                    val a1 = a0.copy(account = a0.account.copy(accountNumber = "a1", type = Account.Type.LOAN))
                    val a2 = a0.copy(account = a0.account.copy(accountNumber = "a2", type = Account.Type.MORTGAGE))
                    val a3 = a0.copy(account = a0.account.copy(accountNumber = "a3", type = Account.Type.CREDIT_CARD))
                    val a4 = a0.copy(account = a0.account.copy(accountNumber = "a4", type = Account.Type.OTHER))
                    val a5 = a0.copy(account = a0.account.copy(accountNumber = "a5", type = Account.Type.PENSION))
                    val a6 = a0.copy(account = a0.account.copy(accountNumber = "a6", type = Account.Type.OTHER))
                    val a7 = a0.copy(account = a0.account.copy(accountNumber = "a7", type = Account.Type.OTHER))
                    val a8 = a0.copy(account = a0.account.copy(accountNumber = "a8", type = Account.Type.OTHER))
                    val a9 = a0.copy(account = a0.account.copy(accountNumber = "a9", type = Account.Type.OTHER))

                    list.addAll(listOf(a1, a2, a3, a4, a5, a6, a7, a8, a9))

                    groupedAccountsAdapter.submitList(list)
                }
            }
        )
    }

    override fun hasToolbar(): Boolean = true

    override fun getLayoutId() = R.layout.tink_fragment_all_accounts_list

    override fun needsLoginToBeAuthorized(): Boolean = true
}