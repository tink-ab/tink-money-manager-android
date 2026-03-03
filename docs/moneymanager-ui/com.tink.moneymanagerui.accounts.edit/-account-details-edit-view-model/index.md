---
title: AccountDetailsEditViewModel
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.accounts.edit](../index.html)/[AccountDetailsEditViewModel](index.html)



# AccountDetailsEditViewModel



[androidJvm]\
class [AccountDetailsEditViewModel](index.html)@Injectconstructor(accountRepository: [AccountRepository](../../se.tink.android.repository.account/-account-repository/index.html), statisticsRepository: [StatisticsRepository](../../com.tink.moneymanagerui.repository/-statistics-repository/index.html)) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)



## Constructors


| | |
|---|---|
| [AccountDetailsEditViewModel](-account-details-edit-view-model.html) | [androidJvm]<br>@Inject<br>fun [AccountDetailsEditViewModel](-account-details-edit-view-model.html)(accountRepository: [AccountRepository](../../se.tink.android.repository.account/-account-repository/index.html), statisticsRepository: [StatisticsRepository](../../com.tink.moneymanagerui.repository/-statistics-repository/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [addCloseable](../../com.tink.moneymanagerui.insights/-archived-insights-view-model/index.html#264516373%2FFunctions%2F1000845458) | [androidJvm]<br>open fun [addCloseable](../../com.tink.moneymanagerui.insights/-archived-insights-view-model/index.html#264516373%2FFunctions%2F1000845458)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Closeable](https://developer.android.com/reference/kotlin/java/io/Closeable.html)) |
| [setAccountId](set-account-id.html) | [androidJvm]<br>fun [setAccountId](set-account-id.html)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [uppdateAccount](uppdate-account.html) | [androidJvm]<br>fun [uppdateAccount](uppdate-account.html)() |


## Properties


| Name | Summary |
|---|---|
| [accountDetailsEditData](account-details-edit-data.html) | [androidJvm]<br>val [accountDetailsEditData](account-details-edit-data.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;ResponseState&lt;[AccountDetailsEditData](../-account-details-edit-data/index.html)&gt;&gt; |
| [editedExcluded](edited-excluded.html) | [androidJvm]<br>val [editedExcluded](edited-excluded.html): [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [editedFavorite](edited-favorite.html) | [androidJvm]<br>val [editedFavorite](edited-favorite.html): [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [editedNameText](edited-name-text.html) | [androidJvm]<br>val [editedNameText](edited-name-text.html): [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [editedShared](edited-shared.html) | [androidJvm]<br>val [editedShared](edited-shared.html): [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [editedType](edited-type.html) | [androidJvm]<br>val [editedType](edited-type.html): [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;Account.Type&gt; |
| [enableFields](enable-fields.html) | [androidJvm]<br>val [enableFields](enable-fields.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[EditAccountField](../../com.tink.moneymanagerui.accounts/-edit-account-field/index.html)&gt; |
| [hasMadeChanges](has-made-changes.html) | [androidJvm]<br>val [hasMadeChanges](has-made-changes.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [isUpdating](is-updating.html) | [androidJvm]<br>val [isUpdating](is-updating.html): [MutableLiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/MutableLiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [saveButtonEnabled](save-button-enabled.html) | [androidJvm]<br>val [saveButtonEnabled](save-button-enabled.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |

