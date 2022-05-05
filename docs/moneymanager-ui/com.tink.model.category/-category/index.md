---
title: Category
---
//[moneymanager-ui](../../../index.html)/[com.tink.model.category](../index.html)/[Category](index.html)



# Category



[androidJvm]\
data class [Category](index.html)(val code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val type: [Category.Type](-type/index.html), val parentId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val children: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Category](index.html)&gt;, val sortOrder: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val isDefaultChild: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)



## Constructors


| | |
|---|---|
| [Category](-category.html) | [androidJvm]<br>fun [Category](-category.html)(code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), type: [Category.Type](-type/index.html), parentId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, children: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Category](index.html)&gt;, sortOrder: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), isDefaultChild: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |


## Types


| Name | Summary |
|---|---|
| [Type](-type/index.html) | [androidJvm]<br>enum [Type](-type/index.html) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[Category.Type](-type/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [describeContents](../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [describeContents](../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [writeToParcel](../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [children](children.html) | [androidJvm]<br>val [children](children.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Category](index.html)&gt; |
| [code](code.html) | [androidJvm]<br>val [code](code.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [id](id.html) | [androidJvm]<br>val [id](id.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [isDefaultChild](is-default-child.html) | [androidJvm]<br>val [isDefaultChild](is-default-child.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [name](name.html) | [androidJvm]<br>val [name](name.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [parentId](parent-id.html) | [androidJvm]<br>val [parentId](parent-id.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [sortOrder](sort-order.html) | [androidJvm]<br>val [sortOrder](sort-order.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [type](type.html) | [androidJvm]<br>val [type](type.html): [Category.Type](-type/index.html) |


## Extensions


| Name | Summary |
|---|---|
| [getIcon](../../se.tink.commons.categories/get-icon.html) | [androidJvm]<br>@[AttrRes](https://developer.android.com/reference/kotlin/androidx/annotation/AttrRes.html)<br>fun [Category](index.html).[getIcon](../../se.tink.commons.categories/get-icon.html)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [graphColor](../../se.tink.commons.categories/graph-color.html) | [androidJvm]<br>@[AttrRes](https://developer.android.com/reference/kotlin/androidx/annotation/AttrRes.html)<br>fun [Category](index.html).[graphColor](../../se.tink.commons.categories/graph-color.html)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [iconBackgroundColor](../../se.tink.commons.categories/icon-background-color.html) | [androidJvm]<br>@[AttrRes](https://developer.android.com/reference/kotlin/androidx/annotation/AttrRes.html)<br>fun [Category](index.html).[iconBackgroundColor](../../se.tink.commons.categories/icon-background-color.html)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [iconColor](../../se.tink.commons.categories/icon-color.html) | [androidJvm]<br>@[AttrRes](https://developer.android.com/reference/kotlin/androidx/annotation/AttrRes.html)<br>fun [Category](index.html).[iconColor](../../se.tink.commons.categories/icon-color.html)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [recursiveIdList](../../se.tink.commons.extensions/recursive-id-list.html) | [androidJvm]<br>val [Category](index.html).[recursiveIdList](../../se.tink.commons.extensions/recursive-id-list.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |

