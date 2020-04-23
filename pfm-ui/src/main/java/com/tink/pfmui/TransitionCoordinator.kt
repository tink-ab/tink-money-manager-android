package com.tink.pfmui

import androidx.annotation.UiThread
import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

private typealias FragmentClass = KClass<out Fragment>

interface TransitionCoordinator {
    fun postponeEnterTransition(fragment: Fragment)
    fun startPostponedEnterTransition(fragment: Fragment)
    fun hasTransitionInProgress(): Boolean
}

@UiThread
internal class TransitionCoordinatorImpl(private val transitions: Set<TransitionDescription>) :
    TransitionCoordinator {
    private var exitingFragment: Fragment? = null

    fun onExiting(current: Fragment?) {
        if (!hasTransitionInProgress()) {
            clear()
            exitingFragment = current
        }
    }

    fun clear() {
        exitingFragment = null
    }

    override fun postponeEnterTransition(fragment: Fragment) {
        if (shouldRunTransition(exitingFragment, fragment)) {
            fragment.postponeEnterTransition()
        }
    }

    override fun startPostponedEnterTransition(fragment: Fragment) =
        startTransition(exitingFragment, fragment)

    override fun hasTransitionInProgress(): Boolean = exitingFragment != null

    private fun startTransition(exiting: Fragment?, entering: Fragment) {
        if (exiting != null && shouldRunTransition(exiting, entering)) {
            val exitingSet = captureHierarchy(exiting)
            val enteringSet = captureHierarchy(entering)
            val forward = transitions.filter { exitingSet.contains(it.fragment1) && enteringSet.contains(it.fragment2) }
            val backward = transitions.filter { enteringSet.contains(it.fragment1) && exitingSet.contains(it.fragment2) }
            if (BuildConfig.DEBUG && forward.size + backward.size > 1) {
                throw IllegalStateException("Ambiguity in transition from $exiting to $entering.")
            }
            // Always clear first
            clearTransitions(exiting, entering)
            forward.firstOrNull()?.setup(exiting, entering)
            backward.firstOrNull()?.setup(entering, exiting)
            clear()
        }
        entering.startPostponedEnterTransition()
    }

    private fun shouldRunTransition(exiting: Fragment?, fragment: Fragment): Boolean =
        exiting != null && exiting.parentFragment === fragment.parentFragment

    private fun captureHierarchy(f: Fragment): Set<FragmentClass> {

        // Can't use [Fragment.isVisible], it checks for [Fragment.view] visibility also, which is false for exiting fragment at this point
        fun isVisible(f: Fragment): Boolean {
            return f.isAdded && !f.isHidden && f.view != null
                    && f.view?.windowToken != null && f.userVisibleHint
        }

        fun captureHierarchy(f: Fragment, set: MutableSet<FragmentClass>) {
            set.add(f::class)
            val children = f.childFragmentManager.fragments
            for (child in children) {
                if (isVisible(child)) {
                    captureHierarchy(child, set)
                }
            }
        }

        return mutableSetOf<FragmentClass>().also {
            captureHierarchy(f, it)
        }
    }

    private fun clearTransitions(exiting: Fragment, entering: Fragment) {

        fun Fragment.clearTransitions() {
            exitTransition = null
            reenterTransition = null
            enterTransition = null
            returnTransition = null
            sharedElementEnterTransition = null
            sharedElementReturnTransition = null
        }

        exiting.clearTransitions()
        entering.clearTransitions()
    }
}

internal interface TransitionDescription {
    val fragment1: FragmentClass
    val fragment2: FragmentClass

    fun setup(f1: Fragment, f2: Fragment)
}