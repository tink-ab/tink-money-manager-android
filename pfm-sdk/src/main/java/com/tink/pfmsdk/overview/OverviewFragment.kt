package com.tink.pfmsdk.overview

import com.tink.pfmsdk.BaseFragment
import com.tink.pfmsdk.R
import com.tink.pfmsdk.tracking.ScreenEvent

class OverviewFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_overview
    override fun getScreenEvent(): ScreenEvent = ScreenEvent.OVERVIEW
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun viewReadyAfterLayout(): Boolean = false
    override fun onChildViewReady(child: BaseFragment?) {
        if(child is OverviewChartFragment) onViewReady()
    }
}
