package com.tink.moneymanagerui.mock

import org.json.JSONObject

object AccountMockFactory {

    fun getAccounts(list: List<JSONObject>) = JSONObject(
        """
        {
            "accounts": $list
        }
        """
    )

    fun getAccount(
        id: String = "SE2885222529285409533697",
        accountNumber: String = "SE2885222529285409533697",
        balance: Double = 2000000.0,
        currencyCode: String = "SEK",
        name: String = "Godis konto",
        type: String = "LOAN",
        ownership: Double = 1.0,
        excluded: Boolean = false,
        favored: Boolean = true,
    ): JSONObject {
        return JSONObject(
            """
        {
            "accountNumber": "$accountNumber",
            "availableCredit": 0.0,
            "balance": $balance,
            "bankId": "SE2885222529285409533697",
            "certainDate": 1640646000000,
            "credentialsId": "569f5710ff84442d9735a04fc218ca87",
            "excluded": $excluded,
            "favored": $favored,
            "id": "$id",
            "ownership": $ownership,
            "name": "$name",
            "payload": null,
            "type": "$type",
            "userId": "d4899403f1f947149d431f7ddf35ed97",
            "userModifiedExcluded": true,
            "userModifiedName": true,
            "userModifiedType": true,
            "identifiers": "[\"iban://SE2885222529285409533697\",\"se-pg://19533697\",\"se://1200112236\"]",
            "transferDestinations": null,
            "details": null,
            "images": {
                "icon": "https://cdn.tink.se/provider-images/demobank.png",
                "banner": null
            },
            "holderName": "John Doe",
            "closed": false,
            "flags": "[\"PSD2_PAYMENT_ACCOUNT\"]",
            "accountExclusion": "PFM_AND_SEARCH",
            "currencyCode": "$currencyCode",
            "currencyDenominatedBalance": {
                "unscaledValue": "$balance",
                "scale": -0,
                "currencyCode": "$currencyCode"
            },
            "refreshed": 1643375759000,
            "financialInstitutionId": "7cf96a1534fd407ebfd00ba534bae705",
            "firstSeen": 1643374220000,
            "iban": "SE2885222529285409533697"
        }
        """
        )
    }

    fun getListWithOneAccount(
        accountId: String = "ee7ddbd178494220bb184791783f4f63",
        accountName: String = "Checking account 1"
    ): JSONObject {
        return JSONObject(
            """
        {
           "accounts":[
              {
                 "accountNumber":"SE0798163589086646482694",
                 "availableCredit":0.0,
                 "balance":447945.0,
                 "bankId":"SE0798163589086646482694",
                 "certainDate":1640300400000,
                 "credentialsId":"b78bdd2240eb47b79f73dfda26e08450",
                 "excluded":false,
                 "favored":true,
                 "id":"$accountId",
                 "name":"$accountName",
                 "ownership":1.0,
                 "payload":null,
                 "type":"CHECKING",
                 "userId":"4ef21cc1f86c43919779de7dff374466",
                 "userModifiedExcluded":false,
                 "userModifiedName":false,
                 "userModifiedType":false,
                 "identifiers":"[\"iban://SE0798163589086646482694\"]",
                 "transferDestinations":null,
                 "details":null,
                 "images":{
                    "icon":"https://cdn.tink.se/provider-images/demobank.png",
                    "banner":null
                 },
                 "holderName":"John Doe",
                 "closed":false,
                 "flags":"[\"PSD2_PAYMENT_ACCOUNT\"]",
                 "accountExclusion":"NONE",
                 "currencyCode":"SEK",
                 "currencyDenominatedBalance":{
                    "unscaledValue":447945,
                    "scale":0,
                    "currencyCode":"SEK"
                 },
                 "refreshed":1643364861000,
                 "financialInstitutionId":"7cf96a1534fd407ebfd00ba534bae705",
                 "firstSeen":1643364771000,
                 "iban":"SE0798163589086646482694"
              }
           ]
        }
        """
        )
    }
}
