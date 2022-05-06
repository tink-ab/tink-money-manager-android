---
title: CategoryTree
---
//[moneymanager-ui](../../../index.html)/[com.tink.model.category](../index.html)/[CategoryTree](index.html)



# CategoryTree



[androidJvm]\
data class [CategoryTree](index.html)(val expenses: [Category](../-category/index.html), val income: [Category](../-category/index.html), val transfers: [Category](../-category/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)



## Constructors


| | |
|---|---|
| [CategoryTree](-category-tree.html) | [androidJvm]<br>fun [CategoryTree](-category-tree.html)(expenses: [Category](../-category/index.html), income: [Category](../-category/index.html), transfers: [Category](../-category/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [describeContents](../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [describeContents](../../com.tink.service.provider/-provider-filter/index.html#-1578325224%2FFunctions%2F1000845458)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [writeToParcel](../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458) | [androidJvm]<br>abstract fun [writeToParcel](../../com.tink.service.provider/-provider-filter/index.html#-1754457655%2FFunctions%2F1000845458)(p0: [Parcel](https://developer.android.com/reference/kotlin/android/os/Parcel.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [expenses](expenses.html) | [androidJvm]<br>val [expenses](expenses.html): [Category](../-category/index.html) |
| [income](income.html) | [androidJvm]<br>val [income](income.html): [Category](../-category/index.html) |
| [transfers](transfers.html) | [androidJvm]<br>val [transfers](transfers.html): [Category](../-category/index.html) |


## Extensions


| Name | Summary |
|---|---|
| [findCategoryByCode](../../se.tink.commons.extensions/find-category-by-code.html) | [androidJvm]<br>fun [CategoryTree](index.html).[findCategoryByCode](../../se.tink.commons.extensions/find-category-by-code.html)(code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Category](../-category/index.html)? |
| [findCategoryById](../../se.tink.commons.extensions/find-category-by-id.html) | [androidJvm]<br>fun [CategoryTree](index.html).[findCategoryById](../../se.tink.commons.extensions/find-category-by-id.html)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Category](../-category/index.html)? |
| [getCategoryByType](../../se.tink.commons.extensions/get-category-by-type.html) | [androidJvm]<br>fun [CategoryTree](index.html).[getCategoryByType](../../se.tink.commons.extensions/get-category-by-type.html)(type: [Category.Type](../-category/-type/index.html)): [Category](../-category/index.html) |

