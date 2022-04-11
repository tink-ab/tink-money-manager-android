package com.tink.moneymanagerui.overview.accounts

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.MoneyManagerFeatureType
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.accounts.details.AccountDetailsFragment
import com.tink.moneymanagerui.accounts.list.AccountDetailsListFragment
import com.tink.moneymanagerui.accounts.list.AccountsViewModel
import com.tink.moneymanagerui.extensions.visibleIf
import com.tink.service.network.ErrorState
import com.tink.service.network.LoadingState
import com.tink.service.network.SuccessState
import kotlinx.android.synthetic.main.tink_fragment_accounts_list.*

internal class AccountsOverviewListFragment : BaseFragment() {
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
            layoutManager = AccountOverviewLayoutManager(context)
            adapter = accountListAdapter
        }

        seeAllAccounts.setOnClickListener {
            navigateToAllAccounts()
        }

        emptyStateSeeAllActions.setOnClickListener {
            navigateToAllAccounts()
        }

        viewModel.overviewAccounts.observe(
            viewLifecycleOwner,
            Observer { overviewAccounts ->
                overviewAccounts?.let { accounts ->
                    if (accounts is SuccessState) {
                        accountListAdapter.setAccounts(accounts.data)
                    }
                }
                loader.visibleIf { overviewAccounts is LoadingState }
                accountList.visibleIf { overviewAccounts is SuccessState }
                accounts_error_text.visibleIf { overviewAccounts is ErrorState }
            }
        )

        viewModel.hasOverviewAccount.observe(
            viewLifecycleOwner,
            Observer { hasFavoriteAccount ->
                accountsHeader.visibleIf { hasFavoriteAccount }
                accountList.visibleIf { hasFavoriteAccount }
                noFavoriteAccountsCard.visibleIf { !hasFavoriteAccount }
            }
        )
    }

    private fun navigateToAllAccounts() {
        fragmentCoordinator.replace(AccountDetailsListFragment())
    }

    override fun getMoneyManagerFeatureType() = MoneyManagerFeatureType.ACCOUNTS
}
