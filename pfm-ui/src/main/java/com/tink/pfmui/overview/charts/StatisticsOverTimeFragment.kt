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
import se.tink.core.models.Category

class StatisticsOverTimeFragment : BaseFragment() {
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun getLayoutId(): Int = R.layout.fragment_statistics_over_time

    val adapter = BarChartItemAdapter()

    private lateinit var viewModel: StatisticsOverTimeViewModel

    private lateinit var categoryViewModel: ChartDetailsViewModel

    override fun authorizedOnCreate(savedInstanceState: Bundle?) {
        super.authorizedOnCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(
            this,
            viewModelFactory
        )[StatisticsOverTimeViewModel::class.java]

        categoryViewModel = ViewModelProviders.of(
            rootFragment, viewModelFactory
        )[ChartDetailsViewModel::class.java]
    }

    override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)

        viewModel.periodSelectionButtonText.observe(viewLifecycleOwner, Observer {
            periodSelectionButton.text = it
        })

        viewModel.barChartItems.observe(viewLifecycleOwner, Observer {
            adapter.items = it
            adapter.averageHeightFactor = it.averageFactor
            adapter.notifyDataSetChanged()
        })

        viewModel.average.observe(viewLifecycleOwner, Observer {
            average.text = it
        })

        viewModel.sum.observe(viewLifecycleOwner, Observer {
            sum.text = it
        })

        categoryViewModel.category.observe(
            viewLifecycle,
            Observer { category: Category ->
                viewModel.selectCategory(category)
                adapter.notifyDataSetChanged()
            }
        )

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
