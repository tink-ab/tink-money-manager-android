package com.tink.moneymanagerui.accounts.edit

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.MoneyManagerFeatureType
import com.tink.moneymanagerui.R
import kotlinx.android.synthetic.main.tink_fragment_account_details_edit.*

class AccountDetailsEditFragment : BaseFragment() {
    private val accountId: String by lazy { requireNotNull(arguments?.getString(ACCOUNT_ID_ARGS)) }
    private lateinit var viewModel: AccountDetailsEditViewModel

    override fun getLayoutId(): Int = R.layout.tink_fragment_account_details_edit
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun getMoneyManagerFeatureType(): MoneyManagerFeatureType = MoneyManagerFeatureType.ACCOUNTS
    override fun hasToolbar(): Boolean = true

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)

        with(ViewModelProviders.of(this, viewModelFactory)) {
            viewModel = get(AccountDetailsEditViewModel::class.java)
        }

       //
    // viewModel.setAccountId(accountId)
    }

    override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        includedContainer.setOnClickListener {
            includedSwitch.toggle()
        }
        sharedContainer.setOnClickListener {
            sharedSwitch.toggle()
        }
        favoriteContainer.setOnClickListener {
            favoriteSwitch.toggle()
        }
    //           actionButton.isEnabled = !isLoading
    }

    companion object {
        private const val ACCOUNT_ID_ARGS = "account_id_args"

        fun newInstance(accountId: String): BaseFragment =
            AccountDetailsEditFragment().apply {
                arguments = bundleOf(ACCOUNT_ID_ARGS to accountId)
            }
    }
}