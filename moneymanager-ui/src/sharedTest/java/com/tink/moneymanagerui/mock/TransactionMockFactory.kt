package com.tink.moneymanagerui.mock

import org.json.JSONObject

object TransactionMockFactory {

    fun getTransactionsForAccount(
        accountId: String = "4f034cc4629b4f72b6199d1d128af472",
        numberOfTransactions: Int = 10
    ): JSONObject {

        val transactions = (1..numberOfTransactions).map {
            """
                {
                    "transaction": ${getTransaction(id = it.toString(), accountId = accountId)},
                    "type": "TRANSACTION"
                }
            """
        }

        return JSONObject(
            """
        {
            "count" : 10167,
            "net": 1288.45,
            "metrics" : {
              "COUNT" : 10167,
              "NET" : -101760.0,
              "SUM" : 101760.0,
              "AVG" : 10.00885216878135,
              "CATEGORIES" : {
                "b3963cfe6bf54c06b22eaa44e0a6cf3f" : 0.5325275157232704,
                "3574aa8a22d4480983b9d4c0451c2776" : 0.45646619496855345,
                "075fab3ec31f43aa9d39675475c1fb1a" : 0.005994496855345912,
                "6ddd66c5b7c84bd09c6c68d8b75a4314" : 0.0050117924528301884
              }
            },
            "periodAmounts" : [ {
              "key" : "2022-01",
              "value" : -91210.0
            }, {
              "key" : "2022-02",
              "value" : -10550.0
            }, {
              "key" : "2022-03",
              "value" : 0.0
            } ],
            "query" : {
              "accounts" : [ "225fa9e233794f208001d9863a518dd9" ],
              "categories" : [ ],
              "externalIds" : [ ],
              "minAmount" : null,
              "maxAmount" : null,
              "endDate" : null,
              "limit" : 0,
              "offset" : 0,
              "order" : "DESC",
              "queryString" : null,
              "sort" : "DATE",
              "startDate" : null,
              "transactionId" : null,
              "includeUpcoming" : true,
              "lastTransactionId" : null
            },
            "results" : $transactions
        }
        """
        )
    } //
//    = (1..numberOfTransactions).map {
//            getTransaction(id = it.toString(), accountId = accountId)
//        }

    fun getTransaction(
        id: String = "8a703fa458d144f9b802b09b26a43e89",
        accountId: String = "4f034cc4629b4f72b6199d1d128af472",
        amount: String = "-100.0",
        description: String = "Netflix",
        date: String = "1640602800000",
        currencyCode: String = "SEK"
    ): JSONObject {
        return JSONObject(
            """
        {
            "accountId" : "$accountId",
            "amount" : $amount,
            "categoryId" : "63a7e66150d44c67a3380265c86e1c26",
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
