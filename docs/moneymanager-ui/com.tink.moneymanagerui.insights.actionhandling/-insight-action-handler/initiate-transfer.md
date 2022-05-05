---
title: initiateTransfer
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.insights.actionhandling](../index.html)/[InsightActionHandler](index.html)/[initiateTransfer](initiate-transfer.html)



# initiateTransfer



[androidJvm]\
open fun [initiateTransfer](initiate-transfer.html)(sourceUri: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, sourceAccountNumber: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, destinationUri: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, destinationAccountNumber: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, amount: [Amount](../../com.tink.model.misc/-amount/index.html)?, onComplete: (isActionDone: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)



Handle action where the user wants to make a transfer. This corresponds to [InsightAction.Type.CREATE_TRANSFER](../../com.tink.model.insights/-insight-action/-type/-c-r-e-a-t-e_-t-r-a-n-s-f-e-r/index.html) action type.



#### Return



true if the action will be handled, false otherwise



## Parameters


androidJvm

| | |
|---|---|
| sourceUri | URI for the source account that the transfer should be from |
| sourceAccountNumber | The account number to initiate a transfer from |
| destinationUri | URI for the destination account that the transfer should be to |
| destinationAccountNumber | The account number to initiate a transfer to |
| amount | The amount to be transferred |
| onComplete | The lambda block to invoke to indicate if the action has completed successfully or not |




