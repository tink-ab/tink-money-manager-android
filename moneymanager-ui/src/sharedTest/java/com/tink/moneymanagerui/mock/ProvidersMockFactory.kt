package com.tink.moneymanagerui.mock

import org.json.JSONObject

object ProvidersMockFactory {

    fun getProviders(): JSONObject {
        return JSONObject(
            """
{
    "providers": [
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "CREDIT_CARDS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "American Express",
            "fields": [],
            "financialInstitutionId": "b6e0ca903b3b4f3e920ee602d3b919eb",
            "financialInstitutionName": "American Express",
            "groupDisplayName": "American Express",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/americanexpress.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-amex-ob",
            "passwordHelpText": "You will be redirected to your card issuer to complete the authentication. American Express authentication is experiencing issues, if it doesn't complete successfully, please try again.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "LOANS",
                "IDENTITY_DATA",
                "MORTGAGE_AGGREGATION",
                "INVESTMENTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Avanza",
            "displayDescription": "Mobile BankID",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "576dad6f22594374bbada2990b4a2d31",
            "financialInstitutionName": "Avanza",
            "groupDisplayName": "Avanza",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/avanza.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "avanza-bankid",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BROKER",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "IDENTITY_DATA",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Bank Norwegian",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "824b9a1381fa5346acd64c5e8773d403",
            "financialInstitutionName": "Bank Norwegian",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/norwegianbank.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "norwegian-bankid",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "CREDIT_CARDS",
                "IDENTITY_DATA",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "Bank Norwegian",
            "fields": [],
            "financialInstitutionId": "824b9a1381fa5346acd64c5e8773d403",
            "financialInstitutionName": "Bank Norwegian",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/se/se-norwegianbank.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-norwegian-ob",
            "passwordHelpText": "",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "LOANS",
                "IDENTITY_DATA"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "CSN",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "a31f72c5214147438376027cad36745a",
            "financialInstitutionName": "CSN",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/csn.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "csn-bankid",
            "passwordHelpText": "To connect your service provider, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": false,
            "type": "OTHER",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "CREDIT_CARDS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "Circle K Mastercard",
            "fields": [],
            "financialInstitutionId": "9daf31b6c107590285b1426e22c52902",
            "financialInstitutionName": "Circle K Mastercard",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/circle_k.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-statoilmastercard-ob",
            "passwordHelpText": "You will be redirected to your card issuer to complete the authentication.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "IDENTITY_DATA"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Circle K Mastercard",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "9daf31b6c107590285b1426e22c52902",
            "financialInstitutionName": "Circle K Mastercard",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/circle_k.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "statoilmastercard-bankid",
            "passwordHelpText": "To connect your credit card, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "IDENTITY_DATA",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Collector Bank",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "74f446d8e3124533944d93f35115e398",
            "financialInstitutionName": "Collector Bank",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/collector.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "collector-bankid",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "IDENTITY_DATA"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Coop Mastercard Mer",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "13a17fe6c0495b6e97de3199930d663b",
            "financialInstitutionName": "Coop Mastercard Mer",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/coop.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "coop-bankid",
            "passwordHelpText": "To connect your credit card, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "CREDIT_CARDS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "Coop Mastercard Mer",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "13a17fe6c0495b6e97de3199930d663b",
            "financialInstitutionName": "Coop Mastercard Mer",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/coop.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-coop-ob",
            "passwordHelpText": "You will be redirected to your card issuer to complete the authentication.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "LOANS",
                "PAYMENTS",
                "TRANSFERS",
                "CHECKING_ACCOUNTS",
                "IDENTITY_DATA",
                "INVESTMENTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [
                "PIS_SE_BANK_TRANSFERS",
                "PIS_SE_BG",
                "PIS_SE_PG",
                "PIS_FUTURE_DATE"
            ],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Danske Bank",
            "displayDescription": "Mobile BankID",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "564cdcaad29e45489fd66b8ed1b6d8cb",
            "financialInstitutionName": "Danske Bank",
            "groupDisplayName": "Danske Bank",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/danskebank.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "danskebank-bankid",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "CHECKING_ACCOUNTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "PASSWORD",
            "currency": "SEK",
            "displayName": "Danske Bank",
            "fields": [],
            "financialInstitutionId": "564cdcaad29e45489fd66b8ed1b6d8cb",
            "financialInstitutionName": "Danske Bank",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/danskebank.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "se-danskebank-ob",
            "passwordHelpText": "You will be redirected to your bank to complete the authentication.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "IDENTITY_DATA"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Eurocard",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "e1a9656ea21e4da08a836c93455c5f13",
            "financialInstitutionName": "Eurocard",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/eurocard.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "eurocard-bankid",
            "passwordHelpText": "To connect your credit card, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "CREDIT_CARDS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "Eurocard",
            "fields": [],
            "financialInstitutionId": "e1a9656ea21e4da08a836c93455c5f13",
            "financialInstitutionName": "Eurocard",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/eurocard.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-eurocard-ob",
            "passwordHelpText": "You will be redirected to your card issuer to complete the authentication.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "CREDIT_CARDS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "Finnair Plus Mastercard",
            "fields": [],
            "financialInstitutionId": "f5cd7c80bb114b5e97a1fe42dbfb77a2",
            "financialInstitutionName": "Finnair Plus Mastercard",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/finnair.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-finnairmastercard-ob",
            "passwordHelpText": "You will be redirected to your card issuer to complete the authentication.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "LOANS",
                "PAYMENTS",
                "TRANSFERS",
                "CHECKING_ACCOUNTS",
                "IDENTITY_DATA",
                "MORTGAGE_AGGREGATION",
                "INVESTMENTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [
                "PIS_SE_BANK_TRANSFERS",
                "PIS_SE_BG",
                "PIS_SE_PG",
                "PIS_FUTURE_DATE"
            ],
            "credentialsType": "PASSWORD",
            "currency": "SEK",
            "displayName": "Handelsbanken",
            "displayDescription": "Card reader",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                },
                {
                    "defaultValue": null,
                    "description": "Personal code",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "NNNN",
                    "immutable": false,
                    "masked": true,
                    "maxLength": 4,
                    "minLength": 4,
                    "name": "password",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "([0-9]{4})",
                    "patternError": "Please enter four digits.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": true,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "ea3b5ac45e274e62b3fd1181aa3aca9c",
            "financialInstitutionName": "Handelsbanken",
            "groupDisplayName": "Handelsbanken",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/handelsbanken.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "handelsbanken",
            "passwordHelpText": "You use your card reader and personal code the same way as you would in the bank's mobile app.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "LOANS",
                "PAYMENTS",
                "TRANSFERS",
                "CHECKING_ACCOUNTS",
                "IDENTITY_DATA",
                "MORTGAGE_AGGREGATION",
                "INVESTMENTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [
                "PIS_SE_BANK_TRANSFERS",
                "PIS_SE_BG",
                "PIS_SE_PG",
                "PIS_FUTURE_DATE"
            ],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Handelsbanken",
            "displayDescription": "Mobile BankID",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "ea3b5ac45e274e62b3fd1181aa3aca9c",
            "financialInstitutionName": "Handelsbanken",
            "groupDisplayName": "Handelsbanken",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/handelsbanken.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "handelsbanken-bankid",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "BUSINESS",
            "capabilities": [
                "CHECKING_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Handelsbanken",
            "displayDescription": "Mobile BankID",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Organization number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "XXXXXX-XXXX",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 11,
                    "minLength": 11,
                    "name": "psu-corporate-id",
                    "numeric": false,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "[0-9]{2}[2-9]{1}[0-9]{3}-[0-9]{4}",
                    "patternError": "Please enter a valid organization number. Note that sole traders are currently not supported.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "ea3b5ac45e274e62b3fd1181aa3aca9c",
            "financialInstitutionName": "Handelsbanken",
            "groupDisplayName": "Handelsbanken",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/handelsbanken.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "handelsbanken-business-bankid",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "BUSINESS"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "BUSINESS",
            "releaseStatus": "BETA",
            "authenticationFlow": "DECOUPLED",
            "capabilities": [
                "CHECKING_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Handelsbanken",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                },
                {
                    "defaultValue": null,
                    "description": "Organization number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "XXXXXX-XXXX",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 11,
                    "minLength": 11,
                    "name": "psu-corporate-id",
                    "numeric": false,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "[0-9]{2}[2-9]{1}[0-9]{3}-[0-9]{4}",
                    "patternError": "Please enter a valid organization number. Note that sole traders are currently not supported.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "ea3b5ac45e274e62b3fd1181aa3aca9c",
            "financialInstitutionName": "Handelsbanken",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/handelsbanken.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "se-handelsbanken-business-ob",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID. If you are a sole trader please login via the personal connection.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "BUSINESS"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "DECOUPLED",
            "capabilities": [
                "CREDIT_CARDS",
                "CHECKING_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Handelsbanken",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "ea3b5ac45e274e62b3fd1181aa3aca9c",
            "financialInstitutionName": "Handelsbanken",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/handelsbanken.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "se-handelsbanken-ob",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "LOANS",
                "PAYMENTS",
                "TRANSFERS",
                "CHECKING_ACCOUNTS",
                "IDENTITY_DATA",
                "MORTGAGE_AGGREGATION",
                "INVESTMENTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [
                "PIS_SE_BANK_TRANSFERS",
                "PIS_SE_BG",
                "PIS_SE_PG",
                "PIS_FUTURE_DATE"
            ],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "ICA Banken",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "edcfe750f47948bf8334287c5054b88e",
            "financialInstitutionName": "ICA Banken",
            "groupDisplayName": "ICA Banken",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/se/se-icabanken.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "icabanken-bankid",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "CHECKING_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "ICA Banken",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "edcfe750f47948bf8334287c5054b88e",
            "financialInstitutionName": "ICA Banken",
            "groupDisplayName": "ICA Banken",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/se/se-icabanken.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "se-icabanken-ob",
            "passwordHelpText": "You will be redirected to your bank to complete the authentication.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "IDENTITY_DATA"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "INGO Mastercard (JET)",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "7714ff23c1084468a67eaa3f006a7ad8",
            "financialInstitutionName": "Ingo Mastercard",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/ingo.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "jetmastercard-bankid",
            "passwordHelpText": "To connect your credit card, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "CREDIT_CARDS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "INGO Mastercard (JET)",
            "fields": [],
            "financialInstitutionId": "7714ff23c1084468a67eaa3f006a7ad8",
            "financialInstitutionName": "Ingo Mastercard",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/ingo.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-jetmastercard-ob",
            "passwordHelpText": "You will be redirected to your card issuer to complete the authentication.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "IDENTITY_DATA"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Ikanokortet",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "7f393aa9d7cf4550bf64d9582100fadf",
            "financialInstitutionName": "Ikanokortet",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/ikano.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "ikanokort-bankid",
            "passwordHelpText": "To connect your credit card, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "LOANS",
                "PAYMENTS",
                "TRANSFERS",
                "CHECKING_ACCOUNTS",
                "IDENTITY_DATA",
                "MORTGAGE_AGGREGATION",
                "INVESTMENTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [
                "PIS_SE_BANK_TRANSFERS",
                "PIS_SE_BG",
                "PIS_SE_PG",
                "PIS_FUTURE_DATE"
            ],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Länsförsäkringar",
            "displayDescription": "Mobile BankID",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "6fc0e59bc4c35a3a8e42cee139afe63c",
            "financialInstitutionName": "Länsförsäkringar",
            "groupDisplayName": "Länsförsäkringar",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/lansforsakringar.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "lansforsakringar-bankid",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "DECOUPLED",
            "capabilities": [
                "CREDIT_CARDS",
                "PAYMENTS",
                "TRANSFERS",
                "CHECKING_ACCOUNTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [
                "PIS_BULK_PAYMENTS",
                "PIS_SE_BANK_TRANSFERS",
                "PIS_SE_BG",
                "PIS_SE_PG",
                "PIS_FUTURE_DATE"
            ],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Länsförsäkringar",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "6fc0e59bc4c35a3a8e42cee139afe63c",
            "financialInstitutionName": "Länsförsäkringar",
            "groupDisplayName": "Länsförsäkringar",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/lansforsakringar.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "se-lansforsakringar-ob",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "CREDIT_CARDS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "Mervärde",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "a0991c8030654404b2f12cbdd24bbc46",
            "financialInstitutionName": "Mervärde",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/lo_mervarde.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-mervardemastercard-ob",
            "passwordHelpText": "You will be redirected to your card issuer to complete the authentication.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "IDENTITY_DATA"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "More Golf Mastercard",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "8377de7fc6505ad489ba97416f86fd2c",
            "financialInstitutionName": "More Golf Mastercard",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/se-moregolf.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "moregolfmastercard-bankid",
            "passwordHelpText": "To connect your credit card, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "CREDIT_CARDS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "More Golf Mastercard",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "8377de7fc6505ad489ba97416f86fd2c",
            "financialInstitutionName": "More Golf Mastercard",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/se-moregolf.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-moregolfmastercard-ob",
            "passwordHelpText": "You will be redirected to your card issuer to complete the authentication.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "TRANSFERS",
                "CHECKING_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "N26",
            "fields": [],
            "financialInstitutionId": "651267d7eae64421a0ca352afa18525d",
            "financialInstitutionName": "N26",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/se/se-n26.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-n26-ob",
            "passwordHelpText": "",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "IDENTITY_DATA"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "NK Nyckeln Mastercard",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "9526debd22445e3d85affa88b0572689",
            "financialInstitutionName": "NK Nyckeln Mastercard",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/nk.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "nknyckelnmastercard-bankid",
            "passwordHelpText": "To connect your credit card, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "CREDIT_CARDS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "NK Nyckeln Mastercard",
            "fields": [],
            "financialInstitutionId": "9526debd22445e3d85affa88b0572689",
            "financialInstitutionName": "NK Nyckeln Mastercard",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/nk.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-nknyckelnmastercard-ob",
            "passwordHelpText": "You will be redirected to your card issuer to complete the authentication.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "LOANS",
                "TRANSFERS",
                "CHECKING_ACCOUNTS",
                "IDENTITY_DATA",
                "MORTGAGE_AGGREGATION",
                "INVESTMENTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [
                "PIS_SE_BANK_TRANSFERS",
                "PIS_SE_BG",
                "PIS_SE_PG",
                "PIS_FUTURE_DATE"
            ],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Nordea",
            "displayDescription": "Mobile BankID",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "dde2463acf40501389de4fca5a3693a4",
            "financialInstitutionName": "Nordea",
            "groupDisplayName": "Nordea",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/nordea.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "nordea-bankid",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "BUSINESS",
            "capabilities": [
                "CHECKING_ACCOUNTS",
                "IDENTITY_DATA",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Nordea",
            "displayDescription": "Mobile BankID",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                },
                {
                    "defaultValue": null,
                    "description": "Organization number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "XXXXXX-XXXX",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 11,
                    "minLength": 11,
                    "name": "psu-corporate-id",
                    "numeric": false,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "[0-9]{6}-[0-9]{4}",
                    "patternError": "Please enter a valid organization number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "dde2463acf40501389de4fca5a3693a4",
            "financialInstitutionName": "Nordea",
            "groupDisplayName": "Nordea",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/nordea.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "nordea-business-bankid",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "BUSINESS"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "BUSINESS",
            "authenticationFlow": "DECOUPLED",
            "capabilities": [
                "CHECKING_ACCOUNTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Nordea",
            "displayDescription": "Mobile BankID",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                },
                {
                    "defaultValue": null,
                    "description": "Organization number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "XXXXXX-XXXX",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 10,
                    "name": "psu-corporate-id",
                    "numeric": false,
                    "oneOf": false,
                    "optional": true,
                    "options": null,
                    "pattern": "[0-9]{6}-[0-9]{4}",
                    "patternError": "Please enter a valid organization number or leave empty if you are sole trader.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "dde2463acf40501389de4fca5a3693a4",
            "financialInstitutionName": "Nordea",
            "groupDisplayName": "Nordea",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/nordea.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-nordea-business-ob",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID. Please enter a valid organization number or leave empty if you are sole trader.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "BUSINESS"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "DECOUPLED",
            "capabilities": [
                "CREDIT_CARDS",
                "CHECKING_ACCOUNTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Nordea",
            "displayDescription": "Mobile BankID",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "dde2463acf40501389de4fca5a3693a4",
            "financialInstitutionName": "Nordea",
            "groupDisplayName": "Nordea",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/nordea.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "se-nordea-ob",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "IDENTITY_DATA"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Nordic Choice Club Mastercard",
            "displayDescription": "Mobile BankID",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "76417c8d2faa4e4a9e75be61001cc81c",
            "financialInstitutionName": "Nordic Choice Club Mastercard",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/nordicchoice.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "choicemastercard-bankid",
            "passwordHelpText": "To connect your credit card, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "CREDIT_CARDS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "Nordic Choice Club Mastercard",
            "fields": [],
            "financialInstitutionId": "76417c8d2faa4e4a9e75be61001cc81c",
            "financialInstitutionName": "Nordic Choice Club Mastercard",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/nordicchoice.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-choicemastercard-ob",
            "passwordHelpText": "You will be redirected to your card issuer to complete the authentication.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "IDENTITY_DATA",
                "INVESTMENTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "PASSWORD",
            "currency": "SEK",
            "displayName": "Nordnet",
            "displayDescription": "Password",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Username",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": null,
                    "immutable": true,
                    "masked": false,
                    "maxLength": null,
                    "minLength": null,
                    "name": "username",
                    "numeric": false,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": null,
                    "patternError": null,
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                },
                {
                    "defaultValue": null,
                    "description": "Password",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": null,
                    "immutable": false,
                    "masked": true,
                    "maxLength": null,
                    "minLength": null,
                    "name": "password",
                    "numeric": false,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": null,
                    "patternError": null,
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": true,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "bae94a56755357c29617735ec1ad7964",
            "financialInstitutionName": "Nordnet",
            "groupDisplayName": "Nordnet",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/nordnet.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "nordnet",
            "passwordHelpText": "Use the same username and password as you would in the bank's mobile app.",
            "popular": true,
            "status": "ENABLED",
            "transactional": false,
            "type": "BROKER",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "IDENTITY_DATA",
                "INVESTMENTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Nordnet",
            "displayDescription": "Mobile BankID",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "bae94a56755357c29617735ec1ad7964",
            "financialInstitutionName": "Nordnet",
            "groupDisplayName": "Nordnet",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/nordnet.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "nordnet-bankid",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.",
            "popular": true,
            "status": "ENABLED",
            "transactional": false,
            "type": "BROKER",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "IDENTITY_DATA"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Opel Mastercard",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "71dae66b338c5db4b01f767b4505bcfb",
            "financialInstitutionName": "Opel Mastercard",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/opel.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "opelmastercard-bankid",
            "passwordHelpText": "To connect your credit card, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "CREDIT_CARDS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "Opel Mastercard",
            "fields": [],
            "financialInstitutionId": "71dae66b338c5db4b01f767b4505bcfb",
            "financialInstitutionName": "Opel Mastercard",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/opel.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-opelmastercard-ob",
            "passwordHelpText": "You will be redirected to your card issuer to complete the authentication.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "IDENTITY_DATA"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Preem",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "cc1fc13916365b0882bced0ffaf8f61e",
            "financialInstitutionName": "Preem",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/preem.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "preem-bankid",
            "passwordHelpText": "To connect your credit card, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "CREDIT_CARDS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "Quintessentially The Credit Card",
            "fields": [],
            "financialInstitutionId": "8f58c929df054605b76c9176fc6b70c3",
            "financialInstitutionName": "Quintessentially The Credit Card",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/quintessentially.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-quintessentially-ob",
            "passwordHelpText": "You will be redirected to your card issuer to complete the authentication.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "CREDIT_CARDS",
                "CHECKING_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "Resurs Bank",
            "fields": [],
            "financialInstitutionId": "dcc01ffce499485f89fa008153354018",
            "financialInstitutionName": "Resurs Bank",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/resurs_bank.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "se-resursbank-ob",
            "passwordHelpText": "You will be redirected to your bank to complete the authentication.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "LIST_BENEFICIARIES",
                "TRANSFERS",
                "CHECKING_ACCOUNTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "PASSWORD",
            "currency": "SEK",
            "displayName": "Revolut",
            "fields": [],
            "financialInstitutionId": "d43544cf54ed439fa3937c0eecb41c78",
            "financialInstitutionName": "Revolut",
            "groupDisplayName": "",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/uk/uk-revolut.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "se-revolut-ob",
            "passwordHelpText": "You will be redirected to your bank to complete the authentication.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "IDENTITY_DATA"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "SAAB Mastercard",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "889a427a38d55b5f93aab55822f1c8d6",
            "financialInstitutionName": "SAAB Mastercard",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/saab.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "saabmastercard-bankid",
            "passwordHelpText": "To connect your credit card, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "CREDIT_CARDS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "SAAB Mastercard",
            "fields": [],
            "financialInstitutionId": "889a427a38d55b5f93aab55822f1c8d6",
            "financialInstitutionName": "SAAB Mastercard",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/saab.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-saabmastercard-ob",
            "passwordHelpText": "You will be redirected to your card issuer to complete the authentication.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "IDENTITY_DATA"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "SAS EuroBonus Mastercard",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "feb105ee583c5f8c86c16eafcb921d56",
            "financialInstitutionName": "SAS EuroBonus Mastercard",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/sas.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "saseurobonusmastercard-bankid",
            "passwordHelpText": "To connect your credit card, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "CREDIT_CARDS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "SAS EuroBonus Mastercard",
            "fields": [],
            "financialInstitutionId": "feb105ee583c5f8c86c16eafcb921d56",
            "financialInstitutionName": "SAS EuroBonus Mastercard",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/sas.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-saseurobonusmastercard-ob",
            "passwordHelpText": "You will be redirected to your card issuer to complete the authentication.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "LOANS",
                "IDENTITY_DATA",
                "MORTGAGE_AGGREGATION",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "SBAB",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "9844198a59965cae9d2705911a054a60",
            "financialInstitutionName": "SBAB",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/sbab.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "sbab-bankid",
            "passwordHelpText": "Please note that fetching of insurance information is not supported.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "DECOUPLED",
            "capabilities": [
                "TRANSFERS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [
                "PIS_SE_BANK_TRANSFERS",
                "PIS_FUTURE_DATE"
            ],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "SBAB",
            "displayDescription": "Mobile BankID",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "9844198a59965cae9d2705911a054a60",
            "financialInstitutionName": "SBAB",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/sbab.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "se-sbab-ob",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "LOANS",
                "PAYMENTS",
                "TRANSFERS",
                "CHECKING_ACCOUNTS",
                "IDENTITY_DATA",
                "MORTGAGE_AGGREGATION",
                "INVESTMENTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [
                "PIS_SE_BANK_TRANSFERS",
                "PIS_SE_BG",
                "PIS_SE_PG",
                "PIS_FUTURE_DATE"
            ],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "SEB",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "e6c0865084fa5dd1af4be542be1aa362",
            "financialInstitutionName": "SEB",
            "groupDisplayName": "SEB",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/seb.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "seb-bankid",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "BUSINESS",
            "capabilities": [
                "CHECKING_ACCOUNTS",
                "IDENTITY_DATA",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "SEB",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                },
                {
                    "defaultValue": null,
                    "description": "Organization number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "XXXXXX-XXXX",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 11,
                    "minLength": 11,
                    "name": "psu-corporate-id",
                    "numeric": false,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "[0-9]{2}[2-9]{1}[0-9]{3}-[0-9]{4}",
                    "patternError": "Please enter a valid organization number. Note that sole traders are currently not supported.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "e6c0865084fa5dd1af4be542be1aa362",
            "financialInstitutionName": "SEB",
            "groupDisplayName": "SEB",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/seb.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "seb-business-bankid",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "BUSINESS"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "BUSINESS",
            "authenticationFlow": "DECOUPLED",
            "capabilities": [
                "CHECKING_ACCOUNTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "SEB",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Organization number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "XXXXXXXXXX",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 14,
                    "minLength": 10,
                    "name": "psu-corporate-id",
                    "numeric": false,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "[0-9]{10,14}",
                    "patternError": "Please enter a valid organization number without a dash.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "e6c0865084fa5dd1af4be542be1aa362",
            "financialInstitutionName": "SEB",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/seb.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "se-seb-business-ob",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID. Note that sole traders are not supported by SEB at the moment.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "BUSINESS"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "DECOUPLED",
            "capabilities": [
                "TRANSFERS",
                "CHECKING_ACCOUNTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [
                "PIS_SE_BANK_TRANSFERS",
                "PAYMENT_CANCELLATION",
                "PIS_SE_BG",
                "PIS_SE_PG",
                "PIS_FUTURE_DATE"
            ],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "SEB",
            "fields": [],
            "financialInstitutionId": "e6c0865084fa5dd1af4be542be1aa362",
            "financialInstitutionName": "SEB",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/seb.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "se-seb-ob",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID. Note that sole traders are not supported by SEB at the moment.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": true,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "IDENTITY_DATA"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "SEB Wallet Mastercard",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "c20fc644294f5819972b087d9ff89b79",
            "financialInstitutionName": "SEB Wallet Mastercard",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/seb_wallet_mastercard_v2.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "sebwalletmastercard-bankid",
            "passwordHelpText": "To connect your credit card, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "CREDIT_CARDS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "SEB Wallet Mastercard",
            "fields": [],
            "financialInstitutionId": "c20fc644294f5819972b087d9ff89b79",
            "financialInstitutionName": "SEB Wallet Mastercard",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/seb_wallet_mastercard.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-sebwalletmastercard-ob",
            "passwordHelpText": "You will be redirected to your card issuer to complete the authentication.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "CREDIT_CARDS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "SJ Prio Mastercard",
            "fields": [],
            "financialInstitutionId": "515bf78241815f1ca4355a3ce29e6124",
            "financialInstitutionName": "SJ Prio Mastercard",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/sjprio.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-sjpriomastercard-ob",
            "passwordHelpText": "You will be redirected to your card issuer to complete the authentication.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "IDENTITY_DATA"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "SJ Prio Mastercard",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "515bf78241815f1ca4355a3ce29e6124",
            "financialInstitutionName": "SJ Prio Mastercard",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/sjprio.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "sjpriomastercard-bankid",
            "passwordHelpText": "To connect your credit card, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "IDENTITY_DATA"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Seatkortet",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "f70dd408edc05e31948022ae99c28d0e",
            "financialInstitutionName": "Seatkortet",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/seat.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "seatkortet-bankid",
            "passwordHelpText": "To connect your credit card, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "IDENTITY_DATA"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Shell Mastercard",
            "displayDescription": "Mobile BankID",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "c49a71ef2ed2581daecdbe945497783a",
            "financialInstitutionName": "Shell Mastercard",
            "groupDisplayName": "Shell Mastercard",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/shell.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "shellmastercard-bankid",
            "passwordHelpText": "To connect your credit card, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "TRANSFERS",
                "CHECKING_ACCOUNTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "Skandia",
            "displayDescription": "Redirect",
            "fields": [],
            "financialInstitutionId": "6c0eb49356b15092b9e410436b89c313",
            "financialInstitutionName": "Skandia",
            "groupDisplayName": "Skandia",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/se/se-skandiabanken.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-skandiabanken-ob",
            "passwordHelpText": "You will be redirected to your bank to complete the authentication.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "PAYMENTS",
                "TRANSFERS",
                "CHECKING_ACCOUNTS",
                "IDENTITY_DATA",
                "INVESTMENTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [
                "PIS_SE_BG",
                "PIS_SE_PG",
                "PIS_FUTURE_DATE"
            ],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Skandia",
            "fields": [],
            "financialInstitutionId": "6c0eb49356b15092b9e410436b89c313",
            "financialInstitutionName": "Skandia",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/se/se-skandiabanken.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "skandiabanken-ssn-bankid",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "IDENTITY_DATA"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Skodakortet",
            "displayDescription": "Mobile BankID",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "fac3fb2e8ee7581c82eb6b30095adacc",
            "financialInstitutionName": "Skodakortet",
            "groupDisplayName": "Skodakortet",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/skoda.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "skodakortet-bankid",
            "passwordHelpText": "To connect your credit card, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "CHECKING_ACCOUNTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "Sparbanken Syd",
            "fields": [],
            "financialInstitutionId": "62231f7e9bce5834842b6a713caa5c8c",
            "financialInstitutionName": "Sparbanken Syd",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/sparbanken_syd.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-sparbankensyd-ob",
            "passwordHelpText": "You will be redirected to your bank to complete the authentication.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "LOANS",
                "CHECKING_ACCOUNTS",
                "IDENTITY_DATA",
                "INVESTMENTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Sparbanken Syd",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "62231f7e9bce5834842b6a713caa5c8c",
            "financialInstitutionName": "Sparbanken Syd",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/sparbanken_syd.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "sparbankensyd-bankid",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "LOANS",
                "CHECKING_ACCOUNTS",
                "IDENTITY_DATA",
                "MORTGAGE_AGGREGATION",
                "INVESTMENTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Sparbankerna",
            "displayDescription": "Mobile BankID",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "a0afa9bbc85c52aba1b1b8d6a04bc57c",
            "financialInstitutionName": "Sparbankerna",
            "groupDisplayName": "Sparbankerna",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/se/se-savingsbank.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "savingsbank-bankid",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.\nPlease keep in mind that if you want to add Swedbank, you need to select Swedbank, not Sparbankerna.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": true,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "BUSINESS",
            "capabilities": [
                "CHECKING_ACCOUNTS",
                "IDENTITY_DATA",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Sparbankerna",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                },
                {
                    "defaultValue": null,
                    "description": "Organization number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "XXXXXX-XXXX",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 11,
                    "minLength": 11,
                    "name": "psu-corporate-id",
                    "numeric": false,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "[0-9]{6}-[0-9]{4}",
                    "patternError": "Please enter a valid organization number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "a0afa9bbc85c52aba1b1b8d6a04bc57c",
            "financialInstitutionName": "Sparbankerna",
            "groupDisplayName": "Sparbankerna",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/se/se-savingsbank.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "savingsbank-business-bankid",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.\nPlease keep in mind that if you want to add Swedbank, you need to select Swedbank, not Sparbankerna.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "BUSINESS"
                }
            ],
            "hasAuthenticationOptions": true,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "LOANS",
                "CHECKING_ACCOUNTS",
                "IDENTITY_DATA",
                "MORTGAGE_AGGREGATION",
                "INVESTMENTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "PASSWORD",
            "currency": "SEK",
            "displayName": "Sparbankerna",
            "displayDescription": "Security token",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "a0afa9bbc85c52aba1b1b8d6a04bc57c",
            "financialInstitutionName": "Sparbankerna",
            "groupDisplayName": "Sparbankerna",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/se/se-savingsbank.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "savingsbank-token",
            "passwordHelpText": "You need access to your Security token to complete the authentication.\nPlease keep in mind that if you want to add Swedbank, you need to select Swedbank, not Sparbankerna.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": true,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "BUSINESS",
            "releaseStatus": "BETA",
            "authenticationFlow": "DECOUPLED",
            "capabilities": [
                "CHECKING_ACCOUNTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Sparbankerna",
            "displayDescription": "Mobile BankID",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                },
                {
                    "defaultValue": null,
                    "description": "Organization number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "XXXXXX-XXXX",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 11,
                    "minLength": 11,
                    "name": "psu-corporate-id",
                    "numeric": false,
                    "oneOf": false,
                    "optional": true,
                    "options": null,
                    "pattern": "[0-9]{6}-[0-9]{4}",
                    "patternError": "Please enter a valid organization number or leave empty if you are sole trader.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "a0afa9bbc85c52aba1b1b8d6a04bc57c",
            "financialInstitutionName": "Sparbankerna",
            "groupDisplayName": "Sparbankerna",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/se/se-savingsbank.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "se-savingsbank-business-ob",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID. Please enter a valid organization number or leave empty if you are sole trader.\nPlease keep in mind that if you want to add a Sparbank, you need to select Sparbankerna, not Swedbank.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "BUSINESS"
                }
            ],
            "hasAuthenticationOptions": true,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "DECOUPLED",
            "capabilities": [
                "TRANSFERS",
                "CHECKING_ACCOUNTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [
                "PIS_SE_BANK_TRANSFERS",
                "PIS_SE_BG",
                "PIS_SE_PG",
                "PIS_FUTURE_DATE"
            ],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Sparbankerna",
            "displayDescription": "Mobile BankID",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "a0afa9bbc85c52aba1b1b8d6a04bc57c",
            "financialInstitutionName": "Sparbankerna",
            "groupDisplayName": "Sparbankerna",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/se/se-savingsbank.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "se-savingsbank-ob",
            "passwordHelpText": "You will be redirected to your bank to complete the authentication.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": true,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "BUSINESS",
            "releaseStatus": "BETA",
            "authenticationFlow": "DECOUPLED",
            "capabilities": [
                "CHECKING_ACCOUNTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Swedbank",
            "displayDescription": "Mobile BankID",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                },
                {
                    "defaultValue": null,
                    "description": "Organization number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "XXXXXX-XXXX",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 11,
                    "minLength": 11,
                    "name": "psu-corporate-id",
                    "numeric": false,
                    "oneOf": false,
                    "optional": true,
                    "options": null,
                    "pattern": "[0-9]{6}-[0-9]{4}",
                    "patternError": "Please enter a valid organization number or leave empty if you are sole trader.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "6c1749b4475e5677a83e9fa4bb60a18a",
            "financialInstitutionName": "Swedbank",
            "groupDisplayName": "Swedbank",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/swedbank.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "se-swedbank-business-ob",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID. Please enter a valid organization number or leave empty if you are sole trader.\nPlease keep in mind that if you want to add a Sparbank, you need to select Sparbankerna, not Swedbank.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "BUSINESS"
                }
            ],
            "hasAuthenticationOptions": true,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "DECOUPLED",
            "capabilities": [
                "TRANSFERS",
                "CHECKING_ACCOUNTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [
                "PIS_SE_BANK_TRANSFERS",
                "PIS_SE_BG",
                "PIS_SE_PG",
                "PIS_FUTURE_DATE"
            ],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Swedbank",
            "displayDescription": "Mobile BankID",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "6c1749b4475e5677a83e9fa4bb60a18a",
            "financialInstitutionName": "Swedbank",
            "groupDisplayName": "Swedbank",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/se/se-swedbank.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "se-swedbank-ob",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": true,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "LOANS",
                "CHECKING_ACCOUNTS",
                "IDENTITY_DATA",
                "MORTGAGE_AGGREGATION",
                "INVESTMENTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Swedbank",
            "displayDescription": "Mobile BankID",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "6c1749b4475e5677a83e9fa4bb60a18a",
            "financialInstitutionName": "Swedbank",
            "groupDisplayName": "Swedbank",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/se/se-swedbank.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "swedbank-bankid",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.\nPlease keep in mind that if you want to add a Sparbank, you need to select Sparbankerna, not Swedbank.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": true,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "BUSINESS",
            "capabilities": [
                "CHECKING_ACCOUNTS",
                "IDENTITY_DATA",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Swedbank",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                },
                {
                    "defaultValue": null,
                    "description": "Organization number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "XXXXXX-XXXX",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 11,
                    "minLength": 11,
                    "name": "psu-corporate-id",
                    "numeric": false,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "[0-9]{6}-[0-9]{4}",
                    "patternError": "Please enter a valid organization number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "6c1749b4475e5677a83e9fa4bb60a18a",
            "financialInstitutionName": "Swedbank",
            "groupDisplayName": "Swedbank",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/se/se-swedbank.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "swedbank-business-bankid",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.\nPlease keep in mind that if you want to add a Sparbank, you need to select Sparbankerna, not Swedbank.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "BUSINESS"
                }
            ],
            "hasAuthenticationOptions": true,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "LOANS",
                "CHECKING_ACCOUNTS",
                "IDENTITY_DATA",
                "MORTGAGE_AGGREGATION",
                "INVESTMENTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "PASSWORD",
            "currency": "SEK",
            "displayName": "Swedbank",
            "displayDescription": "Security token",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "6c1749b4475e5677a83e9fa4bb60a18a",
            "financialInstitutionName": "Swedbank",
            "groupDisplayName": "Swedbank",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/se/se-swedbank.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "swedbank-token",
            "passwordHelpText": "You need access to your Security token to complete the authentication.\nPlease keep in mind that if you want to add a Sparbank, you need to select Sparbankerna, not Swedbank.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": true,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "LOANS",
                "CHECKING_ACCOUNTS",
                "IDENTITY_DATA",
                "MORTGAGE_AGGREGATION",
                "INVESTMENTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "PASSWORD",
            "currency": "SEK",
            "displayName": "Test Multi-Supplemental",
            "displayDescription": "Password",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Username",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": null,
                    "immutable": true,
                    "masked": false,
                    "maxLength": null,
                    "minLength": null,
                    "name": "username",
                    "numeric": false,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": null,
                    "patternError": null,
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "3590cce61e1256dd9cb2c32bfacb713b",
            "financialInstitutionName": "Test Multi-Supplemental",
            "groupDisplayName": "Test Multi-Supplemental",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/tink.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-test-multi-supplemental",
            "passwordHelpText": "Use the same username and password as you would in the bank's mobile app.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "TEST",
            "financialServices": [
                {
                    "segment": "PERSONAL",
                    "shortName": "Personal Banking"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "LOANS",
                "TRANSFERS",
                "CHECKING_ACCOUNTS",
                "IDENTITY_DATA",
                "MORTGAGE_AGGREGATION",
                "INVESTMENTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "PASSWORD",
            "currency": "SEK",
            "displayName": "Test Password",
            "displayDescription": "Password",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Username",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": null,
                    "immutable": true,
                    "masked": false,
                    "maxLength": null,
                    "minLength": null,
                    "name": "username",
                    "numeric": false,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": null,
                    "patternError": null,
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                },
                {
                    "defaultValue": null,
                    "description": "Password",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": null,
                    "immutable": false,
                    "masked": true,
                    "maxLength": null,
                    "minLength": null,
                    "name": "password",
                    "numeric": false,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": null,
                    "patternError": null,
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": true,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "4d0c65519a5e5a0d80e218a92f9ae1d6",
            "financialInstitutionName": "Test Password",
            "groupDisplayName": "Test Password",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/tink.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-test-password",
            "passwordHelpText": "Use the same username and password as you would in the bank's mobile app.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "TEST",
            "financialServices": [
                {
                    "segment": "PERSONAL",
                    "shortName": "Personal Banking"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "CREDIT_CARDS",
                "TRANSFERS",
                "CHECKING_ACCOUNTS",
                "IDENTITY_DATA",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "Tink Demo Bank",
            "displayDescription": "Redirect",
            "fields": [],
            "financialInstitutionId": "7cf96a1534fd407ebfd00ba534bae705",
            "financialInstitutionName": "Tink Demo Bank",
            "groupDisplayName": "Tink Demo Bank",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/demobank.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-demobank-open-banking-redirect",
            "passwordHelpText": "Use the same username and password as you would in the bank's mobile app.",
            "popular": true,
            "status": "ENABLED",
            "transactional": true,
            "type": "TEST",
            "financialServices": [
                {
                    "segment": "PERSONAL",
                    "shortName": "Personal Banking"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "IDENTITY_DATA"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Volkswagenkortet",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "3f3946d7ba9c595181d6a353d7cbf118",
            "financialInstitutionName": "Volkswagenkortet",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/volkswagen.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "volkswagenkortet-bankid",
            "passwordHelpText": "To connect your credit card, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "CREDIT_CARDS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "Volvofinans Bank",
            "fields": [],
            "financialInstitutionId": "c591fc6deb125e67be12a57d58642766",
            "financialInstitutionName": "Volvofinans Bank",
            "groupDisplayName": "Volvofinans Bank",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/volvofinans.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-volvofinans-ob",
            "passwordHelpText": "You will be redirected to your bank to complete the authentication.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "LOANS",
                "IDENTITY_DATA",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Volvofinans Bank",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "c591fc6deb125e67be12a57d58642766",
            "financialInstitutionName": "Volvofinans Bank",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/volvofinans.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "volvofinans-bankid",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "IDENTITY_DATA"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "re:member",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "88e1cdcf576153f2b5b661b1e1cb3ceb",
            "financialInstitutionName": "re:member",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/se-remember.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "remembermastercard-bankid",
            "passwordHelpText": "To connect your credit card, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "CREDIT_CARDS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "re:member",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Social security number",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": "YYYYMMDDNNNN",
                    "immutable": true,
                    "masked": false,
                    "maxLength": 12,
                    "minLength": 12,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": "^(?:(?:(?:(?:19|20)(?:0[48]|[2468][048]|[13579][26]))|2000)0229|(?:(?:19|20)[0-9]{2}(?:(?:(?:0[13578]|1[02])(?:[123]0|[012][1-9]|31))|(?:(?:0[469]|11)(?:[123]0|[012][1-9]))|02(?:[12]0|[012][1-8]|[01]9))))[0-9]{4}${'$'}",
                    "patternError": "Please enter a valid social security number.",
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "88e1cdcf576153f2b5b661b1e1cb3ceb",
            "financialInstitutionName": "re:member",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/se-remember.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-remembermastercard-ob",
            "passwordHelpText": "You will be redirected to your card issuer to complete the authentication.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "CREDIT_CARD",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OTHER",
            "authenticationUserType": "PERSONAL",
            "capabilities": [
                "CREDIT_CARDS",
                "LOANS",
                "CHECKING_ACCOUNTS",
                "IDENTITY_DATA",
                "INVESTMENTS",
                "SAVINGS_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "MOBILE_BANKID",
            "currency": "SEK",
            "displayName": "Ålandsbanken",
            "fields": [
                {
                    "defaultValue": null,
                    "description": "Username",
                    "exposed": true,
                    "children": null,
                    "group": null,
                    "helpText": null,
                    "hint": null,
                    "immutable": true,
                    "masked": false,
                    "maxLength": null,
                    "minLength": null,
                    "name": "username",
                    "numeric": true,
                    "oneOf": false,
                    "optional": false,
                    "options": null,
                    "pattern": null,
                    "patternError": null,
                    "style": null,
                    "type": null,
                    "value": null,
                    "sensitive": false,
                    "checkbox": false,
                    "additionalInfo": null,
                    "selectOptions": null
                }
            ],
            "financialInstitutionId": "d6bc9c301aa444089400b090a943418e",
            "financialInstitutionName": "Ålandsbanken",
            "groupDisplayName": "Ålandsbanken",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/alandsbanken_v2.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": true,
            "name": "se-alandsbanken-bankid",
            "passwordHelpText": "To connect your bank, you need to identify yourself using Mobile BankID.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        },
        {
            "accessType": "OPEN_BANKING",
            "authenticationUserType": "PERSONAL",
            "authenticationFlow": "REDIRECT",
            "capabilities": [
                "CREDIT_CARDS",
                "CHECKING_ACCOUNTS"
            ],
            "pisCapabilities": [],
            "credentialsType": "THIRD_PARTY_APP",
            "currency": "SEK",
            "displayName": "Ålandsbanken",
            "fields": [],
            "financialInstitutionId": "d6bc9c301aa444089400b090a943418e",
            "financialInstitutionName": "Ålandsbanken",
            "groupDisplayName": "Ålandsbanken",
            "images": {
                "icon": "https://cdn.tink.se/provider-images/alandsbanken_v2.png",
                "banner": null
            },
            "market": "SE",
            "multiFactor": false,
            "name": "se-alandsbanken-ob",
            "passwordHelpText": "You will be redirected to your bank to complete the authentication.",
            "popular": false,
            "status": "ENABLED",
            "transactional": true,
            "type": "BANK",
            "financialServices": [
                {
                    "segment": "PERSONAL"
                }
            ],
            "hasAuthenticationOptions": false,
            "healthStatus": "HEALTHY"
        }
    ]
}
        """
        )
    }
}