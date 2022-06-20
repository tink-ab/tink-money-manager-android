---
title: FragmentCoordinator
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui](../index.html)/[FragmentCoordinator](index.html)



# FragmentCoordinator



[androidJvm]\
@[UiThread](https://developer.android.com/reference/kotlin/androidx/annotation/UiThread.html)



class [FragmentCoordinator](index.html)(fragmentManager: [FragmentManager](https://developer.android.com/reference/kotlin/androidx/fragment/app/FragmentManager.html), containerViewId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), transitionCoordinator: [TransitionCoordinatorImpl](../-transition-coordinator-impl/index.html)? = null)

All the methods should be called when UI change is allowed



## Constructors


| | |
|---|---|
| [FragmentCoordinator](-fragment-coordinator.html) | [androidJvm]<br>fun [FragmentCoordinator](-fragment-coordinator.html)(fragmentManager: [FragmentManager](https://developer.android.com/reference/kotlin/androidx/fragment/app/FragmentManager.html), containerViewId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), transitionCoordinator: [TransitionCoordinatorImpl](../-transition-coordinator-impl/index.html)? = null) |


## Functions


| Name | Summary |
|---|---|
| [add](add.html) | [androidJvm]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [add](add.html)(fragment: &lt;ERROR CLASS&gt;, addToBackStack: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, animation: [FragmentAnimationFlags](../-fragment-animation-flags/index.html) = FragmentAnimationFlags.KEEP_BEHIND, stateName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = fragment.javaClass.canonicalName) |
| [backToMainScreen](back-to-main-screen.html) | [androidJvm]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [backToMainScreen](back-to-main-screen.html)(popImmediate: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true) |
| [clear](clear.html) | [androidJvm]<br>fun [clear](clear.html)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [findByTag](find-by-tag.html) | [androidJvm]<br>fun [findByTag](find-by-tag.html)(tag: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): &lt;ERROR CLASS&gt;? |
| [handleBackPress](handle-back-press.html) | [androidJvm]<br>fun [handleBackPress](handle-back-press.html)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [popBackStack](pop-back-stack.html) | [androidJvm]<br>fun [popBackStack](pop-back-stack.html)() |
| [replace](replace.html) | [androidJvm]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [replace](replace.html)(fragment: &lt;ERROR CLASS&gt;, addToBackStack: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, animation: [FragmentAnimationFlags](../-fragment-animation-flags/index.html) = FragmentAnimationFlags.KEEP_BEHIND, stateName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = fragment.javaClass.canonicalName, sharedViews: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[View](https://developer.android.com/reference/kotlin/android/view/View.html)&gt; = emptyList(), tag: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) |
| [show](show.html) | [androidJvm]<br>fun [show](show.html)(fragment: [DialogFragment](https://developer.android.com/reference/kotlin/androidx/fragment/app/DialogFragment.html)) |


## Properties


| Name | Summary |
|---|---|
| [backStackEntryCount](back-stack-entry-count.html) | [androidJvm]<br>val [backStackEntryCount](back-stack-entry-count.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

