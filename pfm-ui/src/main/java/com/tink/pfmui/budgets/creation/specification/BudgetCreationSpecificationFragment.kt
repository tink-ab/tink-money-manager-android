package com.tink.pfmui.budgets.creation.specification

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.tink.pfmui.BaseFragment
import com.tink.pfmui.R
import org.joda.time.DateTime
import com.tink.pfmui.budgets.creation.BudgetCreationFragment
import com.tink.pfmui.budgets.creation.di.BudgetCreationViewModelFactory
import com.tink.pfmui.budgets.creation.specification.PeriodValue.CUSTOM
import com.tink.pfmui.budgets.creation.specification.PeriodValue.MONTH
import com.tink.pfmui.budgets.creation.specification.PeriodValue.WEEK
import com.tink.pfmui.budgets.creation.specification.PeriodValue.YEAR
import com.tink.pfmui.databinding.TinkFragmentBudgetCreationSpecificationBinding
import com.tink.pfmui.extensions.closeKeyboard
import com.tink.pfmui.extensions.openKeyboard
import com.tink.pfmui.tracking.ScreenEvent
import kotlinx.android.synthetic.main.tink_fragment_budget_creation_specification.*
import kotlinx.android.synthetic.main.tink_fragment_budget_creation_specification.view.*
import java.util.Calendar
import javax.inject.Inject

internal class BudgetCreationSpecificationFragment : BaseFragment() {

    companion object {
        fun newInstance() = BudgetCreationSpecificationFragment()
    }

    @Inject
    internal lateinit var budgetCreationViewModelFactory: BudgetCreationViewModelFactory

    internal lateinit var viewModel: BudgetCreationSpecificationViewModel

    override fun getLayoutId(): Int = R.layout.tink_fragment_budget_creation_specification
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun doNotRecreateView(): Boolean = false
    override fun hasToolbar(): Boolean = true

    override fun getScreenEvent(): ScreenEvent? = null //TODO: Budgets

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

        viewModel.budgetName.observe(viewLifecycleOwner, { name ->
            name?.let { title = getString(R.string.tink_budget_specification_toolbar_title, name) }
        })

        viewModel.createdBudgetSpecification.observe(viewLifecycleOwner, {
            it?.let { specification ->
                (parentFragment as BudgetCreationFragment).goToDetailsFragment(specification.id)
            }
        })

        viewModel.createdBudgetError.observe(viewLifecycleOwner, {
            // TODO: Handle error
        })

//        viewModel.amountTextWatcher.observe(viewLifecycle, {
//            amountInputText.addTextChangedListener(it)
//        })
//
//        viewModel.amountInputKeyListener.observe(viewLifecycle, {
//            amountInputText.keyListener = it
//        })

        viewModel.amount?.let {
            viewModel.amountTextWatcher.value?.getTextFromAmount(it)?.let { amountString ->
                viewModel.amountString.postValue(amountString)
            }
        }

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
                fragmentCoordinator.backToMainScreen()
            }
        })
    }

    override fun onCreateToolbarMenu(toolbar: Toolbar) {
        if (viewModel.isEditing) {
            toolbar.inflateMenu(R.menu.tink_budget_edit_menu)
        }
    }

    override fun onToolbarMenuItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_delete_budget) {
            context?.let { context ->
                AlertDialog.Builder(context)
                    .setMessage(
                        getString(
                            R.string.tink_budget_delete_dialog_message,
                            viewModel.budgetName.value
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
        AlertDialog.Builder(context)
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
                0, //TODO: Budgets
//                R.style.MyDatePickerDialogTheme,
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
