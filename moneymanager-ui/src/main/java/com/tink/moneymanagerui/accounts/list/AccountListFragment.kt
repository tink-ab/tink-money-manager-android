package com.tink.moneymanagerui.accounts.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.MoneyManagerFeatureType
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.accounts.details.AccountDetailsFragment
import com.tink.moneymanagerui.extensions.visibleIf
import com.tink.moneymanagerui.tracking.ScreenEvent
import com.tink.service.network.ErrorState
import com.tink.service.network.LoadingState
import com.tink.service.network.SuccessState
import kotlinx.android.synthetic.main.tink_fragment_accounts_list.*

class AccountListFragment : BaseFragment() {

    private lateinit var viewModel: AccountsViewModel

    private val accountsAdapter = NotGroupedAccountListAdapter {
        fragmentCoordinator.replace(AccountDetailsFragment.newInstance(it.id))
    }

    private val groupedAccountsAdapter = GroupedAccountListAdapter {
        fragmentCoordinator.replace(AccountDetailsFragment.newInstance(it.id))
    }

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[AccountsViewModel::class.java]
    }

    public override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)

        title = getString(R.string.tink_overview_toolbar_title)

        accountsGroupedRoot.visibleIf { viewModel.accountDetailsViewMode.areAccountsGrouped }
        accountsNotGroupedRoot.visibleIf { !viewModel.accountDetailsViewMode.areAccountsGrouped }

        allAccountsList.layoutManager = LinearLayoutManager(context)
        allAccountsList.adapter = accountsAdapter

        groupedAccountsList.layoutManager = LinearLayoutManager(context)
        groupedAccountsList.adapter = groupedAccountsAdapter

        viewModel.accountsWithImageState.observe(
            viewLifecycleOwner,
            Observer { accountsState ->
                allAccountsList.visibleIf { accountsState is SuccessState }
                accountErrorText.visibleIf { accountsState is ErrorState }
                accountsProgressBar.visibleIf { accountsState is LoadingState }
                if (accountsState is SuccessState) {
                    accountsAdapter.submitList(accountsState.data)
                }
            }
        )

        viewModel.groupedAccountsState.observe(
            viewLifecycleOwner,
            Observer { groupedAccountsState ->
                groupedAccountErrorText.visibleIf { groupedAccountsState is ErrorState }
                groupedAccountsProgressBar.visibleIf { groupedAccountsState is LoadingState }
                groupedAccountsList.visibleIf { groupedAccountsState is SuccessState }
                if (groupedAccountsState is SuccessState) {
                    groupedAccountsAdapter.submitList(groupedAccountsState.data)
                }
            }
        )
    }

    override fun hasToolbar(): Boolean = true

    override fun getLayoutId() = R.layout.tink_fragment_accounts_list

    override fun needsLoginToBeAuthorized(): Boolean = true

    override fun getMoneyManagerFeatureType() = MoneyManagerFeatureType.ACCOUNTS

    override fun getScreenEvent() = ScreenEvent.ALL_ACCOUNTS
}
