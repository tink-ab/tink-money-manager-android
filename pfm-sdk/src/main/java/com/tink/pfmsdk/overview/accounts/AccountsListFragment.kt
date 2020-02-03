package com.tink.pfmsdk.overview.accounts

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tink.pfmsdk.BaseFragment
import com.tink.pfmsdk.R
import kotlinx.android.synthetic.main.fragment_accounts_list.*

class AccountsListFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_accounts_list
    override fun needsLoginToBeAuthorized(): Boolean = true

    private lateinit var viewModel: AccountsViewModel
    private val accountListAdapter = AccountListAdapter()

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[AccountsViewModel::class.java]
        accountListAdapter.onAccountClickedListener = {
            fragmentCoordinator.replace(AccountDetailsFragment.newInstance(it.id))
        }
    }

    override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)

        accountList.apply {
            layoutManager = AccountLayoutManager(context)
            adapter = accountListAdapter
        }

        viewModel.accounts.observe(viewLifecycleOwner, Observer { list ->
            list?.let { accounts ->
                accountListAdapter.setAccounts(accounts)
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { loading ->
            TransitionManager.beginDelayedTransition(loader as ViewGroup)
            loader.visibility = if (loading) View.VISIBLE else View.GONE
        })
    }
}