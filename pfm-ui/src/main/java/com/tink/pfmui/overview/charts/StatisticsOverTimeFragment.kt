package com.tink.pfmui.overview.charts

import com.tink.pfmui.BaseFragment
import com.tink.pfmui.R

class StatisticsOverTimeFragment : BaseFragment() {
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun getLayoutId(): Int = R.layout.fragment_statistics_over_time
}
