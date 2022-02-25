package com.tink.moneymanagerui.accounts.edit

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProviders
import com.tink.model.account.Account
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.MoneyManagerFeatureType
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.extensions.visibleIf
import com.tink.moneymanagerui.util.SoftKeyboardUtils
import com.tink.service.network.LoadingState
import com.tink.service.network.SuccessState
import kotlinx.android.synthetic.main.tink_fragment_account_details_edit.*


class AccountDetailsEditFragment : BaseFragment() {
    private val accountId: String by lazy { requireNotNull(arguments?.getString(ACCOUNT_ID_ARGS)) }
    private lateinit var viewModel: AccountDetailsEditViewModel

    private lateinit var accountTypeToNameList: List<Pair<Account.Type, String>>

    override fun getLayoutId(): Int = R.layout.tink_fragment_account_details_edit
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun getMoneyManagerFeatureType(): MoneyManagerFeatureType = MoneyManagerFeatureType.ACCOUNTS
    override fun hasToolbar(): Boolean = true

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)

        accountTypeToNameList = listOf(
            Account.Type.CHECKING to getString(R.string.tink_accounts_type_checking),
            Account.Type.CREDIT_CARD to getString(R.string.tink_accounts_type_credit_card),
            Account.Type.EXTERNAL to getString(R.string.tink_accounts_type_external),
            Account.Type.INVESTMENT to getString(R.string.tink_accounts_type_investment),
            Account.Type.LOAN to getString(R.string.tink_accounts_type_loan),
            Account.Type.MORTGAGE to getString(R.string.tink_accounts_type_mortgage),
            Account.Type.OTHER to getString(R.string.tink_accounts_type_other),
            Account.Type.PENSION to getString(R.string.tink_accounts_type_pension),
            Account.Type.SAVINGS to getString(R.string.tink_accounts_type_savings)
        ).sortedBy { it.second }

        with(ViewModelProviders.of(this, viewModelFactory)) {
            viewModel = get(AccountDetailsEditViewModel::class.java)
        }

        viewModel.setAccountId(accountId)
    }

    override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)
        setupViews()

        viewModel.accountDetailsEditData.observe(viewLifecycleOwner) { state ->
            loader.visibleIf { state is LoadingState }
            inputContainer.visibleIf { state is SuccessState }
            saveButton.visibleIf { state is SuccessState }


            if (state is SuccessState) {
                state.data.apply {
                    if (nameInputText.text.isNullOrBlank()) {
                        nameInputText.setText(name)
                    }
                    val typeText = accountTypeToNameList.find { it.first == type}?.second
                        ?: getString(R.string.tink_accounts_type_other)
                    typeInputText.setText(typeText)
                    includedSwitch.isChecked = !isExcluded
                    sharedSwitch.isChecked = isShared
                    favoriteSwitch.isChecked = isFavored

                    favoriteSwitch.isEnabled = !isExcluded
                    favoriteContainer.isEnabled = !isExcluded
                    if (isExcluded) {
                        favoriteSwitch.isChecked = false
                    }
                }
            }
        }

        viewModel.isUpdating.observe(viewLifecycleOwner) { isUpdating ->
            saveButton.isEnabled = !isUpdating
        }
    }

    private fun setupViews() {
        setupNameInput()
        setupTypeInput()
        setupSwitches()
        setupSaveButton()
    }

    private fun setupNameInput() {
        nameInputText.doOnTextChanged { text, _, _, _ ->
            if (viewModel.editedNameText.value != text.toString()) {
                viewModel.editedNameText.postValue(text.toString())
            }
        }
    }

    private fun setupTypeInput() {
        typeInputText.inputType = InputType.TYPE_NULL

        typeInputText.setOnClickListener {
            openTypeInputDialog()
        }

        typeInputText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                openTypeInputDialog()
            }
        }

        typeInputText.doOnTextChanged { text, _, _, _ ->
            val accountType = accountTypeToNameList.find {
                it.second == typeInputText.text.toString()
            }?.first ?: Account.Type.UNKNOWN
            if (viewModel.editedType.value != accountType) {
                viewModel.editedType.postValue(accountType)
            }
        }
    }

    private fun openTypeInputDialog() {
        AlertDialog.Builder(context)
            .setTitle(R.string.tink_accounts_edit_field_type_dialog_title)
            .setItems(
                accountTypeToNameList.map { it.second }.toTypedArray()
            ) { _, item ->
                typeInputText.setText(accountTypeToNameList[item].second)
            }.create().show()
    }

    private fun setupSwitches() {
        includedContainer.setOnClickListener {
            includedSwitch.toggle()
        }
        sharedContainer.setOnClickListener {
            sharedSwitch.toggle()
        }
        favoriteContainer.setOnClickListener {
            favoriteSwitch.toggle()
        }

        includedSwitch.setOnCheckedChangeListener { _, included ->
            viewModel.editedExcluded.postValue(!included)
        }
        favoriteSwitch.setOnCheckedChangeListener { _, isFavorite ->
            viewModel.editedFavorite.postValue(isFavorite)
        }
        sharedSwitch.setOnCheckedChangeListener { _, isShared ->
            viewModel.editedShared.postValue(isShared)
        }
    }

    private fun setupSaveButton() {
        saveButton.setOnClickListener {
            SoftKeyboardUtils.closeSoftKeyboard(requireActivity())
            viewModel.uppdateAccount()
        }
    }

    companion object {
        private const val ACCOUNT_ID_ARGS = "account_id_args"

        fun newInstance(accountId: String): BaseFragment =
            AccountDetailsEditFragment().apply {
                arguments = bundleOf(ACCOUNT_ID_ARGS to accountId)
            }
    }
}