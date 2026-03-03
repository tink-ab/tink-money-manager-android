---
title: CustomRecyclerViewAssertions
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.testutil](../index.html)/[CustomRecyclerViewAssertions](index.html)



# CustomRecyclerViewAssertions



[androidJvm]\
object [CustomRecyclerViewAssertions](index.html)



## Functions


| Name | Summary |
|---|---|
| [itemAtPosHasInnerViewDisplayed](item-at-pos-has-inner-view-displayed.html) | [androidJvm]<br>fun [itemAtPosHasInnerViewDisplayed](item-at-pos-has-inner-view-displayed.html)(pos: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), @[IdRes](https://developer.android.com/reference/kotlin/androidx/annotation/IdRes.html)viewId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): &lt;Error class: unknown class&gt;?<br>Returns a {@link ViewAssertion} that asserts that the item of the recyclerView at the given position has an inner view with the given id that is displayed. NOTE: the view must be visible on the screen. |
| [itemAtPosHasInnerViewNotDisplayed](item-at-pos-has-inner-view-not-displayed.html) | [androidJvm]<br>fun [itemAtPosHasInnerViewNotDisplayed](item-at-pos-has-inner-view-not-displayed.html)(pos: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), @[IdRes](https://developer.android.com/reference/kotlin/androidx/annotation/IdRes.html)viewId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): &lt;Error class: unknown class&gt;?<br>Returns a {@link ViewAssertion} that asserts that the item of the recyclerView at the given position has an inner view with the given id that is NOT displayed. NOTE: the view must be visible on the screen. |
| [itemAtPosMatches](item-at-pos-matches.html) | [androidJvm]<br>fun [itemAtPosMatches](item-at-pos-matches.html)(pos: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), @[IdRes](https://developer.android.com/reference/kotlin/androidx/annotation/IdRes.html)viewId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), matcher: &lt;Error class: unknown class&gt;&lt;[View](https://developer.android.com/reference/kotlin/android/view/View.html)?&gt;): &lt;Error class: unknown class&gt;?<br>Returns a {@link ViewAssertion} that asserts that the item of the recyclerView at the given position matches the given matcher. NOTE: the view must be visible on the screen. |

