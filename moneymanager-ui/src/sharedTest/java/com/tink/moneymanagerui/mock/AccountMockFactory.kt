package com.tink.moneymanagerui.mock

import org.json.JSONObject

object AccountMockFactory {

    fun getAccounts(list: List<JSONObject>) = JSONObject("""
        {
            "accounts": $list
        }
        """)

    fun getAccount(
        id: String = "SE2885222529285409533697",
        accountNumber: String = "SE2885222529285409533697",
        balance: Double = 1000000.0,
        name: String = "Godis konto",
        type: String = "LOAN",
        ownership: Double = 1.0,
        excluded: Boolean = false,
        favored: Boolean = true,
    ): JSONObject {
        return JSONObject("""
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
            "currencyCode": "SEK",
            "currencyDenominatedBalance": {
                "unscaledValue": 1,
                "scale": -6,
                "currencyCode": "SEK"
            },
            "refreshed": 1643375759000,
            "financialInstitutionId": "7cf96a1534fd407ebfd00ba534bae705",
            "firstSeen": 1643374220000,
            "iban": "SE2885222529285409533697"
        }
        """
        )
    }
}