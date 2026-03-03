---
title: UserRepository
---
//[moneymanager-ui](../../../index.html)/[se.tink.android.repository.user](../index.html)/[UserRepository](index.html)



# UserRepository



[androidJvm]\
class [UserRepository](index.html)@Injectconstructor(userProfileService: UserProfileService, dispatcher: DispatcherProvider)



## Constructors


| | |
|---|---|
| [UserRepository](-user-repository.html) | [androidJvm]<br>@Inject<br>fun [UserRepository](-user-repository.html)(userProfileService: UserProfileService, dispatcher: DispatcherProvider) |


## Functions


| Name | Summary |
|---|---|
| [refresh](refresh.html) | [androidJvm]<br>fun [refresh](refresh.html)() |
| [refreshState](refresh-state.html) | [androidJvm]<br>fun [refreshState](refresh-state.html)() |


## Properties


| Name | Summary |
|---|---|
| [userProfile](user-profile.html) | [androidJvm]<br>val [userProfile](user-profile.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;UserProfile?&gt; |
| [userProfileState](user-profile-state.html) | [androidJvm]<br>val [userProfileState](user-profile-state.html): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;ResponseState&lt;UserProfile&gt;&gt; |

