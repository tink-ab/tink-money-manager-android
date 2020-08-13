package com.tink.pfmui.overview.charts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tink.pfmui.R
import kotlinx.android.synthetic.main.dialog_period_selection.*

class PeriodSelectionDialog : BottomSheetDialogFragment() {

    var onPeriodSelected: ((PeriodSelection) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_period_selection, container)
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
