package com.tink.moneymanagerui.overview.accounts

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.MoneyManagerFeatureType
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.accounts.list.AccountDetailsListFragment
import com.tink.moneymanagerui.extensions.visibleIf
import com.tink.service.network.ErrorState
import com.tink.service.network.LoadingState
import com.tink.service.network.SuccessState
import kotlinx.android.synthetic.main.tink_fragment_accounts_list.*

internal class AccountsListFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.tink_fragment_accounts_list
    override fun needsLoginToBeAuthorized(): Boolean = true

    private lateinit var viewModel: AccountsViewModel
    private val accountListAdapter = AccountListAdapter()

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[AccountsViewModel::class.java]
        accountListAdapter.onAccountClickedListener = {
            fragmentCoordinator.replace(AccountDetailsFragment.newInstance(it.account.id))
        }
    }

    override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)

        accountList.apply {
            layoutManager = AccountLayoutManager(context)
            adapter = accountListAdapter
        }

        seeAllAccounts.setOnClickListener {
            navigateToAllAccounts()
        }

        emptyStateSeeAllActions.setOnClickListener {
            navigateToAllAccounts()
        }

        viewModel.overviewAccounts.observe(viewLifecycleOwner, Observer { list ->
            list?.let { accounts ->
                // TODO: Load and display provider image from accountsWithImage liveData
                accountListAdapter.setAccounts(accounts)
            }
        })

        viewModel.areAllAccountsOnOverview.observe(viewLifecycleOwner, Observer { areAllAccountsOnOverview ->
            //seeAllAccounts.visibleIf { !areAllAccountsOnOverview }
        })

        viewModel.hasOverviewAccount.observe(viewLifecycleOwner, Observer { hasFavoriteAccount ->
            accountsHeader.visibleIf { hasFavoriteAccount }
            accountList.visibleIf { hasFavoriteAccount }
            noFavoriteAccountsCard.visibleIf { !hasFavoriteAccount }
        })

        viewModel.accountsState.observe(viewLifecycleOwner, Observer { accountState ->
            loader.visibleIf { accountState is LoadingState }
            accountList.visibleIf { accountState is SuccessState }
            accounts_error_text.visibleIf { accountState is ErrorState }
        })
    }

    private fun navigateToAllAccounts() {
        fragmentCoordinator.replace(AccountDetailsListFragment())
    }

    override fun getMoneyManagerFeatureType() = MoneyManagerFeatureType.ACCOUNTS
}