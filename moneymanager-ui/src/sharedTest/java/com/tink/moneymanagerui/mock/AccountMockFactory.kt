package com.tink.moneymanagerui.mock

import org.json.JSONObject

object AccountMockFactory {

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
