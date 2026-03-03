---
title: TransactionUpdateEventBus
---
//[moneymanager-ui](../../../index.html)/[se.tink.android.repository.transaction](../index.html)/[TransactionUpdateEventBus](index.html)



# TransactionUpdateEventBus



[androidJvm]\
class [TransactionUpdateEventBus](index.html)@Injectconstructor(dispatcher: DispatcherProvider)



## Constructors


| | |
|---|---|
| [TransactionUpdateEventBus](-transaction-update-event-bus.html) | [androidJvm]<br>@Inject<br>fun [TransactionUpdateEventBus](-transaction-update-event-bus.html)(dispatcher: DispatcherProvider) |


## Functions


| Name | Summary |
|---|---|
| [postUpdate](post-update.html) | [androidJvm]<br>fun [postUpdate](post-update.html)(updatedTransaction: Transaction) |
| [subscribe](subscribe.html) | [androidJvm]<br>fun [subscribe](subscribe.html)(onTransactionUpdate: (Transaction) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): Job |

