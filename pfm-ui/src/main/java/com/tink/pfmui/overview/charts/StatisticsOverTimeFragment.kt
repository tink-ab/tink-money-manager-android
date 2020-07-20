package com.tink.pfmui.overview.charts

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tink.pfmui.BaseFragment
import com.tink.pfmui.R
import kotlinx.android.synthetic.main.fragment_statistics_over_time.*

class StatisticsOverTimeFragment : BaseFragment() {
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun getLayoutId(): Int = R.layout.fragment_statistics_over_time

    val adapter = PeriodBalanceBarChartAdapter()

    override fun authorizedOnViewCreated(view: View, savedInstanceState: Bundle?) {
        super.authorizedOnViewCreated(view, savedInstanceState)

        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, true)
    }
}
