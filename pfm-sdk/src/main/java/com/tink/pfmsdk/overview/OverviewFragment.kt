package com.tink.pfmsdk.overview

import android.util.Log
import com.tink.pfmsdk.BaseFragment
import com.tink.pfmsdk.R

class OverviewFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_overview
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun viewReadyAfterLayout(): Boolean = false
    override fun onChildViewReady(child: BaseFragment?) {
        Log.d("Jan", "Child view ready: $child")
        if(child is OverviewChartFragment) onViewReady()
    }
}