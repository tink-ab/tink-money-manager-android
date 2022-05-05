---
title: TransactionUpdateEventBus
---
//[moneymanager-ui](../../../index.html)/[se.tink.android.repository.transaction](../index.html)/[TransactionUpdateEventBus](index.html)



# TransactionUpdateEventBus



[androidJvm]\
@ExperimentalCoroutinesApi



class [TransactionUpdateEventBus](index.html)@Injectconstructor(dispatcher: [DispatcherProvider](../../com.tink.service.util/-dispatcher-provider/index.html))



## Constructors


| | |
|---|---|
| [TransactionUpdateEventBus](-transaction-update-event-bus.html) | [androidJvm]<br>@Inject<br>fun [TransactionUpdateEventBus](-transaction-update-event-bus.html)(dispatcher: [DispatcherProvider](../../com.tink.service.util/-dispatcher-provider/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [postUpdate](post-update.html) | [androidJvm]<br>fun [postUpdate](post-update.html)(updatedTransaction: [Transaction](../../com.tink.model.transaction/-transaction/index.html)) |
| [subscribe](subscribe.html) | [androidJvm]<br>fun [subscribe](subscribe.html)(onTransactionUpdate: ([Transaction](../../com.tink.model.transaction/-transaction/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): Job |

