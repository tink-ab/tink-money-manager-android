package com.tink.moneymanagerui.budgets.creation.specification

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.R
import org.joda.time.DateTime
import com.tink.moneymanagerui.budgets.creation.BudgetCreationFragment
import com.tink.moneymanagerui.budgets.creation.di.BudgetCreationViewModelFactory
import com.tink.moneymanagerui.budgets.creation.specification.PeriodValue.CUSTOM
import com.tink.moneymanagerui.budgets.creation.specification.PeriodValue.MONTH
import com.tink.moneymanagerui.budgets.creation.specification.PeriodValue.WEEK
import com.tink.moneymanagerui.budgets.creation.specification.PeriodValue.YEAR
import com.tink.moneymanagerui.databinding.TinkFragmentBudgetCreationSpecificationBinding
import com.tink.moneymanagerui.extensions.closeKeyboard
import com.tink.moneymanagerui.extensions.openKeyboard
import com.tink.moneymanagerui.extensions.textChangedObserver
import com.tink.moneymanagerui.tracking.ScreenEvent
import com.tink.moneymanagerui.view.TinkSnackbar
import kotlinx.android.synthetic.main.tink_fragment_budget_creation_specification.*
import kotlinx.android.synthetic.main.tink_fragment_budget_creation_specification.view.*
import se.tink.commons.extensions.getThemeResIdFromAttr
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Named

internal class BudgetCreationSpecificationFragment : BaseFragment() {

    companion object {
        fun newInstance() = BudgetCreationSpecificationFragment()
    }

    @Inject
    internal lateinit var budgetCreationViewModelFactory: BudgetCreationViewModelFactory

    internal lateinit var viewModel: BudgetCreationSpecificationViewModel

    @Inject
    @field:Named(TinkSnackbar.Theme.ERROR_THEME)
    lateinit var errorSnackbarTheme: TinkSnackbar.Theme

    override fun getLayoutId(): Int = R.layout.tink_fragment_budget_creation_specification
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun doNotRecreateView(): Boolean = false
    override fun hasToolbar(): Boolean = true

    override fun getScreenEvent(): ScreenEvent =
        if (viewModel.isEditing) {
            ScreenEvent.EDIT_BUDGET
        } else {
            ScreenEvent.CREATE_BUDGET
        }

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(
            this,
            budgetCreationViewModelFactory
        )[BudgetCreationSpecificationViewModel::class.java]
    }

    override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)

        DataBindingUtil.bind<TinkFragmentBudgetCreationSpecificationBinding>(view.root)
            ?.also {
                it.viewModel = viewModel
                it.lifecycleOwner = viewLifecycleOwner
            }

        viewModel.defaultBudgetName.observe(viewLifecycleOwner, { name ->
            if (nameInputText.text.isNullOrBlank()) {
                nameInputText.setText(name.orEmpty())
            }
        })

        viewModel.createdBudgetSpecification.observe(viewLifecycleOwner, {
            it?.let { specification ->
                (parentFragment as BudgetCreationFragment).goToDetailsFragment(specification.id)
            }
        })

        viewModel.createdBudgetError.observe(viewLifecycleOwner, { error ->
            context?.let {
                snackbarManager.displayError(error, it, errorSnackbarTheme)
            }
        })

        viewModel.amountTextWatcher.observe(viewLifecycleOwner, { amountTextWatcher ->
            viewModel.amount?.let {
                amountTextWatcher.getTextFromAmount(it).let { amountString ->
                    viewModel.amountString.postValue(amountString)
                }
            }
        })

        nameInputText.textChangedObserver().observe(viewLifecycleOwner, { name ->
            title = name
            viewModel.updateBudgetName(name)
        })

        amountInputText.setOnFocusChangeListener { inputView, hasFocus ->
            val editText = inputView as EditText
            if (hasFocus) {
                editText.openKeyboard()
            } else {
                editText.closeKeyboard()
            }
        }

        periodText.setOnClickListener {
            amountInputText.closeKeyboard()
            onPeriodClicked()
        }

        periodStartText.setOnClickListener {
            amountInputText.closeKeyboard()
            val currentDate = viewModel.periodStartValue.value ?: DateTime.now()
            onPeriodDatePickerClicked(currentDate) { date ->
                viewModel.periodStartValue.value = date
            }
        }

        periodEndText.setOnClickListener {
            amountInputText.closeKeyboard()
            val currentDate = viewModel.periodEndValue.value ?: DateTime.now()
            onPeriodDatePickerClicked(currentDate) { date ->
                viewModel.periodEndValue.value = date
            }
        }

        viewModel.isPeriodEndValid.observe(viewLifecycleOwner, { isValid ->
            if (isValid == false) {
                viewModel.periodEndValue.value = null
                periodEndInputLayout.error =
                        getString(R.string.tink_budget_create_period_end_invalid_message)
            } else {
                periodEndInputLayout.error = null
            }
        })

        viewModel.budgetDeleteResult.observe(viewLifecycleOwner, { errorOrValue ->
            if (errorOrValue?.error != null) {
                snackbarManager.displayError(
                    getString(R.string.tink_budget_delete_error_message),
                    context
                )
            } else if (errorOrValue?.value != null) {
                // Exit budgets edit flow
                fragmentCoordinator.popBackStack()
                // Pop to overview
                fragmentCoordinator.handleBackPress()
            }
        })

        nameInputText.setText(viewModel.budgetName.orEmpty())
    }

    override fun onCreateToolbarMenu(toolbar: Toolbar) {
        if (viewModel.isEditing) {
            toolbar.inflateMenu(R.menu.tink_budget_edit_menu)
        }
    }

    override fun onToolbarMenuItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_delete_budget) {
            context?.let { context ->
                MaterialAlertDialogBuilder(
                    context,
                    context.getThemeResIdFromAttr(R.attr.tink_alertDialogStyle)
                )
                    .setMessage(
                        getString(
                            R.string.tink_budget_delete_dialog_message,
                            viewModel.budgetName
                        )
                    )
                    .setPositiveButton(R.string.tink_budget_delete_dialog_confirm_button) { _, _ ->
                        viewModel.deleteBudget()
                    }
                    .show()
                return true
            }
        }
        return super.onToolbarMenuItemSelected(item)
    }

    private fun onPeriodClicked() {
        val context = context ?: return
        val choiceItems = arrayOf(WEEK, MONTH, YEAR, CUSTOM)
        val choiceItemTexts = choiceItems
            .map { it.toChoiceString(context) }
            .toTypedArray()
        MaterialAlertDialogBuilder(
            context,
            context.getThemeResIdFromAttr(R.attr.tink_alertDialogStyle)
        )
            .setItems(choiceItemTexts) { _, choiceIndex ->
                viewModel.periodValue.value = choiceItems[choiceIndex]
            }
            .show()
    }

    private fun onPeriodDatePickerClicked(
        currentDate: DateTime,
        onDateSelect: (DateTime) -> Unit
    ) {
        context?.let { context ->
            DatePickerDialog(
                context,
                context.getThemeResIdFromAttr(R.attr.tink_datePickerStyle),
                { _, year, month, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val date = DateTime(calendar.timeInMillis)
                    onDateSelect.invoke(date)
                },
                currentDate.year,
                // Calendar#MONTH expects 0-11 while DateTime#monthOfYear outputs 1-12
                currentDate.monthOfYear - 1,
                currentDate.dayOfMonth
            )
                .show()
        }
    }
}
