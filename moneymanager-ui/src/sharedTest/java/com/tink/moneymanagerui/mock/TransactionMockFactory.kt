package com.tink.moneymanagerui.mock

import org.json.JSONObject

object TransactionMockFactory {

    fun getTransaction(
        id: String = "8a703fa458d144f9b802b09b26a43e89",
        amount: String = "-100.0",
        description: String = "Netflix",
        date: String = "1640602800000",
        currencyCode: String = "SEK"
    ): JSONObject {
        return JSONObject(
            """
        {
            "accountId" : "4f034cc4629b4f72b6199d1d128af472",
            "amount" : $amount,
            "categoryId" : "075fab3ec31f43aa9d39675475c1fb1a",
            "categoryType" : "EXPENSES",
            "date" : $date,
            "description" : "$description",
            "formattedDescription" : "$description",
            "id" : "$id",
            "inserted" : 1642065935000,
            "lastModified" : 1642065948317,
            "merchantId" : null,
            "notes" : "",
            "originalAmount" : $amount,
            "originalDate" : $date,
            "originalDescription" : "Netflix",
            "payload" : { },
            "pending" : false,
            "timestamp" : 1642065935383,
            "type" : "DEFAULT",
            "userId" : "cdcd650a7b3a4a3a90e766194eace0f1",
            "upcoming" : false,
            "userModifiedAmount" : false,
            "userModifiedCategory" : false,
            "userModifiedDate" : false,
            "userModifiedDescription" : false,
            "userModifiedLocation" : false,
            "currencyDenominatedAmount" : {
                "unscaledValue" : ${amount + "0"},
                "scale" : 1,
                "currencyCode" : "$currencyCode"
            },
            "currencyDenominatedOriginalAmount" : {
                "unscaledValue" : -200,
                "scale" : 1,
                "currencyCode" : "$currencyCode"
            },
            "parts" : [ ],
            "internalPayload" : { },
            "partnerPayload" : { },
            "dispensableAmount" : $amount,
            "userModified" : false,
            "identifiers" : {
                "providerExternalId" : null
            }
        }
        """
        )
    }
}
