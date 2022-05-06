---
title: TransactionEnricher
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights.enrichment](../index.html)/[TransactionEnricher](index.html)



# TransactionEnricher



[androidJvm]\
class [TransactionEnricher](index.html)@Injectconstructor(transactionRepository: [TransactionRepository](../../se.tink.android.repository.transaction/-transaction-repository/index.html)) : InsightsEnricher



## Constructors


| | |
|---|---|
| [TransactionEnricher](-transaction-enricher.html) | [androidJvm]<br>@Inject<br>fun [TransactionEnricher](-transaction-enricher.html)(transactionRepository: [TransactionRepository](../../se.tink.android.repository.transaction/-transaction-repository/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [enrich](enrich.html) | [androidJvm]<br>open override fun [enrich](enrich.html)(input: [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Insight](../../com.tink.model.insights/-insight/index.html)&gt;&gt;): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Insight](../../com.tink.model.insights/-insight/index.html)&gt;&gt; |

