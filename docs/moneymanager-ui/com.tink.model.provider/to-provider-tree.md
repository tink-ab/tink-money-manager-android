---
title: toProviderTree
---
//[moneymanager-ui](../../index.html)/[com.tink.model.provider](index.html)/[toProviderTree](to-provider-tree.html)



# toProviderTree



[androidJvm]\
fun [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Provider](-provider/index.html)&gt;.[toProviderTree](to-provider-tree.html)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ProviderTreeNode](-provider-tree-node/index.html)&gt;



Groups the providers by a few defining elements, creating a tree structure. Each level in the tree structure may have 1 to n children.



#### Return



A tree of [ProviderTreeNode](-provider-tree-node/index.html) objects that will always follow the structure:



[FinancialInstitutionGroupNode](-provider-tree-node/-financial-institution-group-node/index.html) ->[FinancialInstitutionNode](-provider-tree-node/-financial-institution-node/index.html) ->[AccessTypeNode](-provider-tree-node/-access-type-node/index.html) ->[CredentialsTypeNode](-provider-tree-node/-credentials-type-node/index.html)




