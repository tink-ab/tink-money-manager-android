---
title: TransitionCoordinatorImpl
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui](../index.html)/[TransitionCoordinatorImpl](index.html)



# TransitionCoordinatorImpl



[androidJvm]\
@[UiThread](https://developer.android.com/reference/kotlin/androidx/annotation/UiThread.html)



class [TransitionCoordinatorImpl](index.html)(transitions: [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[TransitionDescription](../-transition-description/index.html)&gt;) : [TransitionCoordinator](../-transition-coordinator/index.html)



## Constructors


| | |
|---|---|
| [TransitionCoordinatorImpl](-transition-coordinator-impl.html) | [androidJvm]<br>fun [TransitionCoordinatorImpl](-transition-coordinator-impl.html)(transitions: [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[TransitionDescription](../-transition-description/index.html)&gt;) |


## Functions


| Name | Summary |
|---|---|
| [clear](clear.html) | [androidJvm]<br>fun [clear](clear.html)() |
| [hasTransitionInProgress](has-transition-in-progress.html) | [androidJvm]<br>open override fun [hasTransitionInProgress](has-transition-in-progress.html)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [onExiting](on-exiting.html) | [androidJvm]<br>fun [onExiting](on-exiting.html)(current: [Fragment](https://developer.android.com/reference/kotlin/androidx/fragment/app/Fragment.html)?) |
| [postponeEnterTransition](postpone-enter-transition.html) | [androidJvm]<br>open override fun [postponeEnterTransition](postpone-enter-transition.html)(fragment: [Fragment](https://developer.android.com/reference/kotlin/androidx/fragment/app/Fragment.html)) |
| [startPostponedEnterTransition](start-postponed-enter-transition.html) | [androidJvm]<br>open override fun [startPostponedEnterTransition](start-postponed-enter-transition.html)(fragment: [Fragment](https://developer.android.com/reference/kotlin/androidx/fragment/app/Fragment.html)) |

