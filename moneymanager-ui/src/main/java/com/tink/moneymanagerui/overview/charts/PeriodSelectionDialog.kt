package com.tink.moneymanagerui.overview.charts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tink.moneymanagerui.R
import kotlinx.android.synthetic.main.tink_dialog_period_selection.*

class PeriodSelectionDialog : BottomSheetDialogFragment() {

    var onPeriodSelected: ((PeriodSelection) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.setOnShowListener { dialog ->
            (dialog as? BottomSheetDialog)
                ?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                ?.let { bottomSheet ->
                    BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
                }
        }
        return inflater.inflate(R.layout.tink_dialog_period_selection, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sixMonthsButton.setOnClickListener { periodSelected(PeriodSelection.SixMonths()) }
        twelveMonthsButton.setOnClickListener { periodSelected(PeriodSelection.TwelveMonths()) }
        allTimeButton.setOnClickListener { periodSelected(PeriodSelection.AllTime()) }
    }

    private fun periodSelected(periodSelection: PeriodSelection) {
        onPeriodSelected?.invoke(periodSelection)
        dismiss()
    }
}
