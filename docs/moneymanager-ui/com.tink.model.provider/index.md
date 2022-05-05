---
title: com.tink.model.provider
---
//[moneymanager-ui](../../index.html)/[com.tink.model.provider](index.html)



# Package com.tink.model.provider



## Types


| Name | Summary |
|---|---|
| [Provider](-provider/index.html) | [androidJvm]<br>data class [Provider](-provider/index.html)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val displayName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val type: [Provider.Type](-provider/-type/index.html), val status: [Provider.Status](-provider/-status/index.html), val releaseStatus: [Provider.ReleaseStatus](-provider/-release-status/index.html)? = null, val credentialsType: [Credentials.Type](../com.tink.model.credentials/-credentials/-type/index.html), val helpText: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val isPopular: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val fields: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Field](../com.tink.model.misc/-field/index.html)&gt;, val groupDisplayName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val displayDescription: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val marketCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val images: [Images](../com.tink.model/-images/index.html)? = null, val financialInstitution: [Provider.FinancialInstitution](-provider/-financial-institution/index.html), val accessType: [Provider.AccessType](-provider/-access-type/index.html), val capabilities: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Provider.Capability](-provider/-capability/index.html)&gt;, val authenticationUserType: [Provider.AuthenticationUserType](-provider/-authentication-user-type/index.html)) : [Comparable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-comparable/index.html)&lt;[Provider](-provider/index.html)&gt; , [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)<br>The provider model represents a way of connecting to different banks or other financial institutions in Tink. Multiple providers can exist for the same financial institution, each representing a different type of access the user can choose from. The model contains metadata about the provider which can be used to group and properly display the representations, thus helping the end user make the correct decision on which provider to choose. |
| [ProviderTreeNode](-provider-tree-node/index.html) | [androidJvm]<br>sealed class [ProviderTreeNode](-provider-tree-node/index.html) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html)<br>This class represents a tree structure of [FinancialInstitutionGroupNode](-provider-tree-node/-financial-institution-group-node/index.html) objects with children. This eventually leads to a leaf object of type [ProviderNode](-provider-tree-node/-provider-node/index.html), that contains more detailed [Provider](-provider/index.html) data. |
| [RefreshProvider](-refresh-provider/index.html) | [androidJvm]<br>data class [RefreshProvider](-refresh-provider/index.html)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val displayName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [Parcelable](https://developer.android.com/reference/kotlin/android/os/Parcelable.html) |


## Functions


| Name | Summary |
|---|---|
| [toProviderTree](to-provider-tree.html) | [androidJvm]<br>fun [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Provider](-provider/index.html)&gt;.[toProviderTree](to-provider-tree.html)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ProviderTreeNode](-provider-tree-node/index.html)&gt;<br>Groups the providers by a few defining elements, creating a tree structure. Each level in the tree structure may have 1 to n children. |

