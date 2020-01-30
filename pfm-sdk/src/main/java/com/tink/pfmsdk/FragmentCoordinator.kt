package com.tink.pfmsdk

import android.view.View
import androidx.annotation.UiThread
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.transaction
import com.tink.pfmsdk.extensions.isTrue

/**
 * All the methods should be called when UI change is allowed
 */
@UiThread
class FragmentCoordinator(
    private val fragmentManager: FragmentManager,
    private val containerViewId: Int,
    private val transitionCoordinator: TransitionCoordinatorImpl? = null
) {

    val backStackEntryCount
        get() = fragmentManager.backStackEntryCount

    /**
     * return last added currently visible fragment (there can be more than one visible at the moment)
     */
    private val topActiveFragment: BaseFragment?
        get() = fragmentManager.fragments.lastOrNull { it is BaseFragment && it.isVisible && it.isResumed } as BaseFragment?

    @JvmOverloads
    fun add(fragment: BaseFragment,
            addToBackStack: Boolean = true,
            animation: FragmentAnimationFlags = FragmentAnimationFlags.KEEP_BEHIND,
            stateName: String? = fragment.javaClass.canonicalName) {
        if (fragmentManager.isStateSaved) return

        fragmentManager.transaction {
            setAnimation(animation)
            add(containerViewId, fragment)
            if (addToBackStack) {
                addToBackStack(stateName)
            }
        }
    }

    @JvmOverloads
    fun replace(fragment: BaseFragment,
                addToBackStack: Boolean = true,
                animation: FragmentAnimationFlags = FragmentAnimationFlags.KEEP_BEHIND,
                stateName: String? = fragment.javaClass.canonicalName,
                sharedViews: List<View> = emptyList(),
                tag: String? = null
    ) {
        if (fragmentManager.isStateSaved) return

        fragmentManager.transaction {
            setAnimation(animation)
            replace(containerViewId, fragment, tag)
            setReorderingAllowed(true)
            sharedViews.forEach {
                addSharedElement(it, it.transitionName)
            }
            transitionCoordinator?.onExiting(topActiveFragment)
            if (addToBackStack) {
                addToBackStack(stateName)
            }
        }
    }

    fun detachAllAndAttach(fragment: BaseFragment, tag: String) {
        // The fragment has been added, or state is saved already, do nothing
        if (fragment.isAdded || fragmentManager.isStateSaved) return

        fragmentManager.transaction {
            setAnimation(FragmentAnimationFlags.FADE)
            for (f in fragmentManager.fragments) detach(f)
            if (fragment.isDetached) attach(fragment) else add(containerViewId, fragment, tag)
        }
    }

    fun popBackStack() {
        if (fragmentManager.isStateSaved) return

        transitionCoordinator?.clear()
        fragmentManager.popBackStack()
    }

    fun popBackStackImmediate(): Boolean {
        if (fragmentManager.isStateSaved) return false

        transitionCoordinator?.onExiting(topActiveFragment)
        return fragmentManager.popBackStackImmediate()
    }

    private fun backTo(stateName: String?, flag: Int,  popImmediate: Boolean = true) {
        if (fragmentManager.isStateSaved) return

        transitionCoordinator?.onExiting(topActiveFragment)
        if (popImmediate) {
            fragmentManager.popBackStackImmediate(stateName, flag)
        } else {
            fragmentManager.popBackStack(stateName, flag)
        }
    }

    fun backTo(stateName: String) = backTo(stateName, FragmentManager.POP_BACK_STACK_INCLUSIVE)

    fun backTo(type: Class<out Fragment>) = backTo(type.canonicalName, 0)

    @JvmOverloads
    fun backToMainScreen(popImmediate: Boolean = true) =
        backTo(null, FragmentManager.POP_BACK_STACK_INCLUSIVE, popImmediate)

    fun handleBackPress(): Boolean {
        if (!topActiveFragment?.onBackPressed().isTrue()) {
            // Temporary fix to use popBackStack() instead of popBackStackImmediate() since the latter doesn't seem to pop the fragment from the backstack in the current setup.
            popBackStack()
        }
        return true
    }

    fun handleThirdPartyCallbackResult(state: String, parameters: Map<String, String>) =
        topActiveFragment?.handleThirdPartyCallbackResult(state, parameters).isTrue()

    fun clear() = with(fragmentManager) {
        transitionCoordinator?.clear()

        if (isStateSaved) return@with false

        popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        transaction {
            fragments.filter { it is BaseFragment }.forEach { remove(it) }
        }
        executePendingTransactions()
    }

    fun findByTag(tag: String): BaseFragment? = with(fragmentManager) {
        findFragmentByTag(tag) as? BaseFragment
    }

    fun show(fragment: DialogFragment) = fragment.show(fragmentManager, fragment.javaClass.name)
}
