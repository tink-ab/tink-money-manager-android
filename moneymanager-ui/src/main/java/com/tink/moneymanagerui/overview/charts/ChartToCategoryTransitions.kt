package com.tink.moneymanagerui.overview.charts

import androidx.fragment.app.Fragment
import android.transition.ChangeBounds
import android.transition.Fade
import com.tink.moneymanagerui.TransitionDescription
import com.tink.moneymanagerui.overview.charts.piechart.CategorySelectionTransition
import javax.inject.Inject


internal class ChartToCategoryTransition @Inject constructor() : TransitionDescription {
    override val fragment1 get() = ChartDetailsPagerFragment::class
    override val fragment2 get() = CategorySelectionFragment::class

    override fun setup(f1: Fragment, f2: Fragment) {
        setupExitTransition(f1)
        setupEnterTransition(f2)
    }

    private fun setupExitTransition(overview: Fragment) {
        with(overview) {
            exitTransition = Fade()
            reenterTransition = null
            allowReturnTransitionOverlap = true
        }
    }

    private fun setupEnterTransition(chartFragment: Fragment) {
        with(chartFragment) {
            sharedElementEnterTransition = ChangeBounds()
            sharedElementReturnTransition = sharedElementEnterTransition
            enterTransition = CategorySelectionTransition().apply {
                duration = 500
            }
            returnTransition = enterTransition
            allowEnterTransitionOverlap = true
        }
    }
}