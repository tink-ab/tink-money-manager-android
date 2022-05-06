---
title: initiateTransfer
---
//[moneymanager-ui](../../../index.html)/[com.tink.service.transfer](../index.html)/[TransferService](index.html)/[initiateTransfer](initiate-transfer.html)



# initiateTransfer



[androidJvm]\
abstract suspend fun [initiateTransfer](initiate-transfer.html)(descriptor: [CreateTransferDescriptor](../-create-transfer-descriptor/index.html)): [SignableOperation](../../com.tink.model.transfer/-signable-operation/index.html)



Initiates a new transfer



#### Return



A [SignableOperation](../../com.tink.model.transfer/-signable-operation/index.html) from which you can read the [status](../../com.tink.model.transfer/-signable-operation/-status/index.html) of the operation.



## Parameters


androidJvm

| | |
|---|---|
| descriptor | Information about the transfer that should be created |




