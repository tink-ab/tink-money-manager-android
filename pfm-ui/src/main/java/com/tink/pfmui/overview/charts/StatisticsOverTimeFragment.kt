package com.tink.pfmui.overview.charts

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tink.pfmui.BaseFragment
import com.tink.pfmui.R
import kotlinx.android.synthetic.main.fragment_statistics_over_time.*

class StatisticsOverTimeFragment : BaseFragment() {
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun getLayoutId(): Int = R.layout.fragment_statistics_over_time

    val adapter = PeriodBalanceBarChartAdapter()

    private lateinit var viewModel: StatisticsOverTimeViewModel

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(
            this,
            viewModelFactory
        )[StatisticsOverTimeViewModel::class.java]
    }

    override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)

        viewModel.periodSelectionButtonText.observe(viewLifecycleOwner, Observer {
            periodSelectionButton.text = it
        })

        viewModel.periodBalances.observe(viewLifecycleOwner, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, true)

        periodSelectionButton.setOnClickListener {
            PeriodSelectionDialog()
                .apply {
                    onPeriodSelected = {
                        viewModel.selectPeriod(it)
                    }
                }
                .show(requireFragmentManager(), "dialog_tag")
        }
    }
}
