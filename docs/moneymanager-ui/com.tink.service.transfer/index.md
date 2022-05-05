---
title: com.tink.service.transfer
---
//[moneymanager-ui](../../index.html)/[com.tink.service.transfer](index.html)



# Package com.tink.service.transfer



## Types


| Name | Summary |
|---|---|
| [CreateBeneficiaryDescriptor](-create-beneficiary-descriptor/index.html) | [androidJvm]<br>data class [CreateBeneficiaryDescriptor](-create-beneficiary-descriptor/index.html)(val ownerAccountId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val credentialsId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val accountNumber: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val accountNumberType: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Descriptor to create a beneficiary of a transfer |
| [CreateTransferDescriptor](-create-transfer-descriptor/index.html) | [androidJvm]<br>data class [CreateTransferDescriptor](-create-transfer-descriptor/index.html)(val amount: [Amount](../com.tink.model.misc/-amount/index.html), val sourceAccountUri: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val sourceMessage: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val beneficiaryUri: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val destinationMessage: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val credentialsId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) |
| [TransferService](-transfer-service/index.html) | [androidJvm]<br>interface [TransferService](-transfer-service/index.html) |

