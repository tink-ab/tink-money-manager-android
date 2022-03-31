package com.tink.moneymanagerui.mock

import com.tink.model.insights.InsightType
import org.json.JSONObject

object InsightMockFactory {

    val allTypes = listOf(
        InsightType.ACCOUNT_BALANCE_LOW,
        InsightType.AGGREGATION_REFRESH_PSD2_CREDENTIAL,
        InsightType.BUDGET_CLOSE_NEGATIVE,
        InsightType.BUDGET_CLOSE_POSITIVE,
        InsightType.BUDGET_OVERSPENT,
        InsightType.BUDGET_SUCCESS,
        InsightType.BUDGET_SUGGEST_CREATE_FIRST,
        InsightType.BUDGET_SUGGEST_CREATE_TOP_CATEGORY,
        InsightType.BUDGET_SUGGEST_CREATE_TOP_PRIMARY_CATEGORY,
        InsightType.BUDGET_SUMMARY_ACHIEVED,
        InsightType.BUDGET_SUMMARY_OVERSPENT,
        InsightType.CREDIT_CARD_LIMIT_CLOSE,
        InsightType.CREDIT_CARD_LIMIT_REACHED,
        InsightType.DOUBLE_CHARGE,
        InsightType.LARGE_EXPENSE,
        InsightType.LEFT_TO_SPEND_NEGATIVE,
        InsightType.LEFT_TO_SPEND_NEGATIVE_BEGINNING_MONTH,
        InsightType.LEFT_TO_SPEND_NEGATIVE_MID_MONTH,
        InsightType.LEFT_TO_SPEND_NEGATIVE_SUMMARY,
        InsightType.LEFT_TO_SPEND_POSITIVE_BEGINNING_MONTH,
        InsightType.LEFT_TO_SPEND_POSITIVE_FINAL_WEEK,
        InsightType.LEFT_TO_SPEND_POSITIVE_MID_MONTH,
        InsightType.LEFT_TO_SPEND_POSITIVE_SUMMARY_SAVINGS_ACCOUNT,
        InsightType.MONTHLY_SUMMARY_EXPENSES_BY_CATEGORY,
        InsightType.MONTHLY_SUMMARY_EXPENSE_TRANSACTIONS,
        InsightType.NEW_INCOME_TRANSACTION,
        InsightType.SINGLE_UNCATEGORIZED_TRANSACTION,
        InsightType.SPENDING_BY_CATEGORY_INCREASED,
        InsightType.SPENDING_BY_PRIMARY_CATEGORY_INCREASED,
        InsightType.SUGGEST_SET_UP_SAVINGS_ACCOUNT,
        InsightType.UNKNOWN,
        InsightType.WEEKLY_SUMMARY_EXPENSES_BY_CATEGORY,
        InsightType.WEEKLY_SUMMARY_EXPENSES_BY_DAY,
        InsightType.WEEKLY_SUMMARY_EXPENSE_TRANSACTIONS,
        InsightType.WEEKLY_UNCATEGORIZED_TRANSACTIONS
    )

    val supportedTypes = listOf(
        InsightType.ACCOUNT_BALANCE_LOW,
        InsightType.AGGREGATION_REFRESH_PSD2_CREDENTIAL,
        InsightType.BUDGET_CLOSE_NEGATIVE,
        InsightType.BUDGET_CLOSE_POSITIVE,
        InsightType.BUDGET_OVERSPENT,
        InsightType.BUDGET_SUCCESS,
        InsightType.BUDGET_SUGGEST_CREATE_FIRST,
        InsightType.BUDGET_SUGGEST_CREATE_TOP_CATEGORY,
        InsightType.BUDGET_SUGGEST_CREATE_TOP_PRIMARY_CATEGORY,
        InsightType.BUDGET_SUMMARY_ACHIEVED,
        InsightType.BUDGET_SUMMARY_OVERSPENT,
        InsightType.CREDIT_CARD_LIMIT_CLOSE,
        InsightType.CREDIT_CARD_LIMIT_REACHED,
        InsightType.DOUBLE_CHARGE,
        InsightType.LARGE_EXPENSE,
        InsightType.MONTHLY_SUMMARY_EXPENSES_BY_CATEGORY,
        InsightType.MONTHLY_SUMMARY_EXPENSE_TRANSACTIONS,
        InsightType.SINGLE_UNCATEGORIZED_TRANSACTION,
        InsightType.WEEKLY_SUMMARY_EXPENSES_BY_CATEGORY,
        InsightType.WEEKLY_SUMMARY_EXPENSES_BY_DAY,
        InsightType.WEEKLY_SUMMARY_EXPENSE_TRANSACTIONS,
        InsightType.WEEKLY_UNCATEGORIZED_TRANSACTIONS
    )

    fun getInsightForType(type: InsightType): JSONObject {
        return when (type) {
            InsightType.ACCOUNT_BALANCE_LOW -> getAccountBalanceLow()
            InsightType.AGGREGATION_REFRESH_PSD2_CREDENTIAL -> getAggregateRefreshP2d2Credentials()
            InsightType.BUDGET_SUGGEST_CREATE_FIRST -> getBudgetSuggestCreateFirst()
            InsightType.BUDGET_SUGGEST_CREATE_TOP_CATEGORY -> getBudgetSuggestCreateTopCategory()
            InsightType.BUDGET_SUGGEST_CREATE_TOP_PRIMARY_CATEGORY -> getBudgetSuggestCreateTopPrimaryCategory()
            InsightType.CREDIT_CARD_LIMIT_CLOSE -> getCreditCardLimitClose()
            InsightType.CREDIT_CARD_LIMIT_REACHED -> getCreditCardLimitReached()
            InsightType.DOUBLE_CHARGE -> getDoubleCharge()
            InsightType.LARGE_EXPENSE -> getLargeExpense()
            InsightType.MONTHLY_SUMMARY_EXPENSES_BY_CATEGORY -> getMonthlySummaryExpenseByCategory()
            InsightType.MONTHLY_SUMMARY_EXPENSE_TRANSACTIONS -> getMonthlySummaryExpensesTransactions()
            InsightType.NEW_INCOME_TRANSACTION -> getNewIncomeTransaction()
            InsightType.SINGLE_UNCATEGORIZED_TRANSACTION -> getSingleUncategorizedTransaction()
            InsightType.WEEKLY_SUMMARY_EXPENSES_BY_CATEGORY -> getWeeklySummaryExpensesByCategory()
            InsightType.WEEKLY_SUMMARY_EXPENSES_BY_DAY -> getWeeklySummaryExpensesByDay()
            InsightType.WEEKLY_SUMMARY_EXPENSE_TRANSACTIONS -> getWeeklySummaryExpensesTransactions()
            InsightType.WEEKLY_UNCATEGORIZED_TRANSACTIONS -> getWeeklyUncategorizedTransactions()
            else -> getDefaultInsight(type)
        }
    }

    fun getInsightsForTypes(types: List<InsightType> = allTypes): List<JSONObject> =
        types.map { type -> getInsightForType(type) }

    fun getAccountBalanceLow(action: String = getAcknowledgeAction()): JSONObject {
        return JSONObject(
            """
            {
                "userId": 1234,
                "id": 1,
                "type": "ACCOUNT_BALANCE_LOW",
                "title": "You have a low balance on Private Account",
                "description": "Your balance is €10. Do you want to transfer more money to this account?",
                "createdTime": 1111111,
                 "insightActions": [$action, ${getDismissAction()}],
                "data": {
                    "type": "ACCOUNT_BALANCE_LOW",
                    "accountId": "c6f26025fbb949a08348e2f73f0ae12c",
                    "balance": {
                        "currencyCode": "EUR",
                        "amount": 2.42
                    }
                }
            }"""
        )
    }

    fun getAggregateRefreshP2d2Credentials(refreshCredentials: String = "123"): JSONObject {
        return JSONObject(
            """
            {
                "userId": 1234,
                "id": 2,
                "type": "AGGREGATION_REFRESH_PSD2_CREDENTIAL",
                "title": "Your connection to Handelsbanken will expire soon",
                "description": "Reconnect to Handelsbanken to make sure your financial data stays up to date.",
                "createdTime": 1111111,
                "insightActions": [${getRefreshCredentialAction(refreshCredentials)}, ${getDismissAction()}],
                "type": "AGGREGATION_REFRESH_PSD2_CREDENTIAL",
                "data": {
                    "type": "AGGREGATION_REFRESH_PSD2_CREDENTIAL",
                    "credential": {
                        "id": "credential-id",
                        "provider": {
                            "name": "handelsbanken-ob",
                            "displayName": "Handelsbanken"
                        },
                        "sessionExpiryDate": 1566464477927
                    }
                }
            }"""
        )
    }

    fun getBudgetSuggestCreateFirst(): JSONObject {
        return JSONObject(
            """
            {
                "userId": 1234,
                "id": 1,
                "type": "BUDGET_SUGGEST_CREATE_FIRST",
                "title": "Set up your first budget to help you keep track of expenses",
                "description": "Creating budgets can help you stay on top of your spending – give it a go.",
                "createdTime": 1111111,
                "insightActions": []
            }"""
        )
    }

    fun getBudgetSuggestCreateTopCategory(): JSONObject {
        return JSONObject(
            """
            {
                "type": "BUDGET_SUGGEST_CREATE_TOP_CATEGORY",
                "userId": 1234,
                "id": 3,
                "title": "Set a budget for your top expense: Restaurants",
                "description": "You spent €1,000 on Restaurants last month. How about setting up a budget of €800 to help save more money?",
                "createdTime": 1111111,
                "insightActions": [ 
                    ${getCreateBudgetAction()},
                    ${getDismissAction()}
                ],
                "data": {
                    "type": "BUDGET_SUGGEST_CREATE_TOP_CATEGORY",
                    "categorySpending": {
                        "categoryCode": "expenses:food.coffee",
                        "spentAmount": {
                            "amount": 400,
                            "currencyCode": "EUR"
                        }
                    },
                    "suggestedBudgetAmount": {
                        "amount": 350,
                        "currencyCode": "EUR"
                    }
                }
            }"""
        )
    }

    fun getBudgetSuggestCreateTopPrimaryCategory(): JSONObject {
        return JSONObject(
            """
            {
                "type": "BUDGET_SUGGEST_CREATE_TOP_PRIMARY_CATEGORY",
                "userId": 1234,
                "id": 4,
                "title": "Set a budget for your top expense: Restaurant",
                "description": "You spent €345 on restaurant last month. How about setting up a budget of €310 to help save more money?",
                "createdTime": 1111111,
                "insightActions": [],
                "data": {
                    "type": "BUDGET_SUGGEST_CREATE_TOP_PRIMARY_CATEGORY",
                    "categorySpending": {
                        "categoryCode": "expenses:food",
                        "spentAmount": {
                            "amount": 400,
                            "currencyCode": "EUR"
                        }
                    },
                    "suggestedBudgetAmount": {
                        "amount": 360,
                        "currencyCode": "EUR"
                    }
                }
            }
            """
        )
    }

    fun getCreditCardLimitClose(): JSONObject {
        return JSONObject(
            """
            {
                "type": "CREDIT_CARD_LIMIT_CLOSE",
                "userId": 1234,
                "id": 21,
                "title": "You are close at exceeding the credit limit on American Express Gold Card",
                "description": "You have €34 of available credit on your card. Spend with care.",
                "createdTime": 1111111,
                "insightActions": [],
                "data": {
                    "type": "CREDIT_CARD_LIMIT_CLOSE",
                    "account": {
                        "accountId": "42f6f233ce394138897e9afff1464f5d",
                        "accountName": "Personal credit account"
                    },
                    "availableCredit": {
                        "amount": 100,
                        "currencyCode": "EUR"
                    }
                }
            }"""
        )
    }

    fun getCreditCardLimitReached(): JSONObject {
        return JSONObject(
            """
            {
                "type": "CREDIT_CARD_LIMIT_REACHED",
                "userId": 1234,
                "id": 21,
                "title": "You just reached your credit limit on American Express Gold Card",
                "description": "Your credit card is maxed out. You’ll be able to use it again after paying your invoice.",
                "createdTime": 1111111,
                "insightActions": [],
                "data": {
                    "type": "CREDIT_CARD_LIMIT_REACHED",
                    "account": {
                        "accountId": "42f6f233ce394138897e9afff1464f5d",
                        "accountName": "Personal credit account"
                    }
                }
            }"""
        )
    }

    fun getMonthlySummaryExpenseByCategory(): JSONObject {
        return JSONObject(
            """
            {
                "type": "MONTHLY_SUMMARY_EXPENSES_BY_CATEGORY",
                "userId": 1234,
                "id": 5,
                "title": "Title",
                "description": "Description",
                "createdTime": 1111111,
                "insightActions": [],
                "data": {
                    "type": "MONTHLY_SUMMARY_EXPENSES_BY_CATEGORY",
                    "month": {
                        "month": 1,
                        "year": 2020 
                    },
                    "expensesByCategory": [
                        {
                            "categoryCode": "expenses:food.coffee",
                            "spentAmount": {
                                "currencyCode": "EUR",
                                "amount": 28.0
                            }
                        },
                        {
                            "categoryCode": "expenses:food.groceries",
                            "spentAmount": {
                                "currencyCode": "EUR",
                                "amount": 115.75
                            }
                        }
                    ]
                }
            }"""
        )
    }

    fun getDoubleCharge(transactionIds: List<String> = listOf()): JSONObject {
        return JSONObject(
            """
             {
                "userId": 1234,
                "id": 6,
                "type": "DOUBLE_CHARGE",
                "title": "You may have been double charged",
                "description": "You were charged €45 twice at Burger King",
                "createdTime": 1111111,
                "insightActions": [],
                "data": {
                    "type": "DOUBLE_CHARGE",
                    "transactionIds": $transactionIds
                }
            }
            """
        )
    }

    fun getLargeExpense(transactionId: String = "8a703fa458d144f9b802b09b26a43e89"): JSONObject {
        return JSONObject(
            """
             {
                "userId": 1234,
                "id": 7,
                "type": "LARGE_EXPENSE",
                "title": "Unusual large expense",
                "description": "You were charged for an unusually large expense at Stadium",
                "createdTime": 1111111,
                "insightActions": [],
                "data": {
                    "type": "LARGE_EXPENSE",
                    "transactionId": $transactionId,
                    "amount": {
                        "currencyCode": "EUR",
                        "amount": 1001.0
                    }
                }
            }
            """
        )
    }

    fun getMonthlySummaryExpensesTransactions(): JSONObject {
        return JSONObject(
            """
            {
                "userId": 1234,
                "id": 8,
                "title": "Title",
                "type": "MONTHLY_SUMMARY_EXPENSE_TRANSACTIONS",
                "description": "Description",
                "createdTime": 1111111,
                "data": {
                    "type": "MONTHLY_SUMMARY_EXPENSE_TRANSACTIONS",
                    "month": {
                        "year": 2020,
                        "month": 1
                    },
                    "transactionSummary": {
                        "totalExpenses": {
                        "currencyCode": "EUR",
                        "amount": 200
                        },
                        "commonTransactionsOverview": {
                            "totalNumberOfTransactions": 45,
                            "mostCommonTransactionDescription": "Pressbyrån",
                            "mostCommonTransactionCount": 6
                            },
                            "largestExpense": {
                                "id": "f2f6f273ce394138897e9afff1464f5d",
                                "date": 1569593745000,
                                "amount": {
                                "currencyCode": "EUR",
                                "amount": 120.3
                            },
                            "description": "IKEA"
                        }
                    }
                }
            }"""
        )
    }

    fun getNewIncomeTransaction(): JSONObject {
        return JSONObject(
            """
            {
                "userId": 1234,
                "id": 9,
                "type": "NEW_INCOME_TRANSACTION",
                "title": "Title",
                "description": "Description",
                "createdTime": 1111111,
                "data": {
                    "type": "NEW_INCOME_TRANSACTION",
                    "transactionId": "42f6f233ce394138897e9afff1464f5d",
                    "accountId": "c6f26025fbb949a08348e2f73f0ae12c"
                }
            }"""
        )
    }

    fun getSingleUncategorizedTransaction(transactionId: String = "8a703fa458d144f9b802b09b26a43e89"): JSONObject {
        return JSONObject(
            """
            {
                "userId": 1234,
                "id": 10,
                "type": "SINGLE_UNCATEGORIZED_TRANSACTION",
                "title": "Singe uncategorized transaction",
                "description": "You have an uncategorized transaction",
                "createdTime": 1111111,
                "data": {
                    "type": "SINGLE_UNCATEGORIZED_TRANSACTION",
                    "transactionId": "$transactionId"
                }
            }"""
        )
    }

    fun getWeeklySummaryExpensesByCategory(): JSONObject {
        return JSONObject(
            """
            {
                "userId": 1234,
                "id": 11,
                "type": "WEEKLY_SUMMARY_EXPENSES_BY_CATEGORY",
                "title": "Title",
                "description": "Description",
                "createdTime": 1111111,
                
                "data": {
                    "type": "WEEKLY_SUMMARY_EXPENSES_BY_CATEGORY",
                    "week": {
                        "year": 2019,
                        "week": 43
                    },
                    "expensesByCategory": [
                        {
                            "categoryCode": "expenses:food.coffee",
                            "spentAmount": {
                                "currencyCode": "EUR",
                                "amount": 7.0
                            }
                        },
                        {
                            "categoryCode": "expenses:food.groceries",
                            "spentAmount": {
                                "currencyCode": "EUR",
                                "amount": 77.76
                            }
                        }
                    ]
                }
            }"""
        )
    }

    fun getWeeklySummaryExpensesByDay(): JSONObject {
        return JSONObject(
            """
                {
    "id": "2957caaa648f45d5b944befa61412b95",
    "userId": "328858b0e20744bc85df7ca70861cf90",
    "type": "WEEKLY_SUMMARY_EXPENSES_BY_DAY",
    "title": "Here’s your spending per day last week",
    "description": "Expenses last week.",
    "data": {
      "week": {
        "year": 2022,
        "week": 2
      },
      "expenseStatisticsByDay": [
        {
          "date": [
            2022,
            1,
            10
          ],
          "expenseStatistics": {
            "totalAmount": {
              "currencyCode": "EUR",
              "amount": 12.24
            },
            "averageAmount": {
              "currencyCode": "EUR",
              "amount": 12.24
            }
          }
        },
        {
          "date": [
            2022,
            1,
            11
          ],
          "expenseStatistics": {
            "totalAmount": {
              "currencyCode": "EUR",
              "amount": 3.0
            },
            "averageAmount": {
              "currencyCode": "EUR",
              "amount": 6.0
            }
          }
        },
        {
          "date": [
            2022,
            1,
            12
          ],
          "expenseStatistics": {
            "totalAmount": {
              "currencyCode": "EUR",
              "amount": 0.46
            },
            "averageAmount": {
              "currencyCode": "EUR",
              "amount": 1.46
            }
          }
        },
        {
          "date": [
            2022,
            1,
            13
          ],
          "expenseStatistics": {
            "totalAmount": {
              "currencyCode": "EUR",
              "amount": 20.54
            },
            "averageAmount": {
              "currencyCode": "EUR",
              "amount": 20.54
            }
          }
        },
        {
          "date": [
            2022,
            1,
            14
          ],
          "expenseStatistics": {
            "totalAmount": {
              "currencyCode": "EUR",
              "amount": 17.08
            },
            "averageAmount": {
              "currencyCode": "EUR",
              "amount": 10.08
            }
          }
        },
        {
          "date": [
            2022,
            1,
            15
          ],
          "expenseStatistics": {
            "totalAmount": {
              "currencyCode": "EUR",
              "amount": 10
            },
            "averageAmount": {
              "currencyCode": "EUR",
              "amount": 0
            }
          }
        },
        {
          "date": [
            2022,
            1,
            16
          ],
          "expenseStatistics": {
            "totalAmount": {
              "currencyCode": "EUR",
              "amount": 3.71
            },
            "averageAmount": {
              "currencyCode": "EUR",
              "amount": 3.71
            }
          }
        }
      ],
      "type": "WEEKLY_SUMMARY_EXPENSES_BY_DAY"
    },
    "createdTime": 1642501091101,
    "insightActions": [
      {
        "label": "OK, good to know",
        "data": {
          "type": "ACKNOWLEDGE"
        }
      }
    ]
  }
            """
        )
    }

    fun getWeeklySummaryExpensesTransactions(): JSONObject {
        return JSONObject(
            """
            {
                "userId": 1234,
                "id": 13,
                "type": "WEEKLY_SUMMARY_EXPENSE_TRANSACTIONS",
                "title": "Title",
                "description": "Description",
                "createdTime": 1111111,
                "data": {
                    "type": "WEEKLY_SUMMARY_EXPENSE_TRANSACTIONS",
                    "week": {
                        "year": 2019,
                        "week": 26
                    },
                    "transactionSummary": {
                        "totalExpenses": {
                            "currencyCode": "EUR",
                            "amount": 71
                        },
                        "commonTransactionsOverview": {
                            "totalNumberOfTransactions": 12,
                            "mostCommonTransactionDescription": "Pressbyrån",
                            "mostCommonTransactionCount": 3
                        },
                        "largestExpense": {
                            "id": "42f6f233ce394138897e9afff1464f5d",
                            "date": 1569593745000,
                            "amount": {
                                "currencyCode": "EUR",
                                "amount": 60
                            },
                            "description": "Super Spa"
                        }
                    }
                }
            }"""
        )
    }

    fun getWeeklyUncategorizedTransactions(transactionIds: List<String> = listOf()): JSONObject {
        return JSONObject(
            """
            {
                "userId": 1234,
                "id": 14,
                "type": "WEEKLY_UNCATEGORIZED_TRANSACTIONS",
                "title": "Weekly uncategorized transactions",
                "description": "You have uncategorized transactions, do you want to categorize them?",
                "createdTime": 1111111,
                "data": {
                    "type": "WEEKLY_UNCATEGORIZED_TRANSACTIONS",
                    "week": {
                        "year": 2019,
                        "week": 43
                    },
                    "transactionIds": $transactionIds
                }
            }"""
        )
    }

    fun getDefaultInsight(type: InsightType): JSONObject {
        return JSONObject(
            """
            {
                "userId": 1234,
                "id": 15,
                "type": "$type",
                "title": "Title",
                "description": "Description",
                "createdTime": 1111111
            }"""
        )
    }

    fun getDismissAction() = """ 
        {
            "label": "Dismiss",
            "data":{ "type": "DISMISS" }
        }
    """

    fun getCreateBudgetAction(): String {
        return """
            {
                "label": "Create budget",
                "data": {
                    "type": "CREATE_BUDGET",
                    "budgetSuggestion": {
                        "filter": {
                            "categories": ["expenses:food.bars"],
                            "accounts": ["d2b49640cbba4d8899a4886b6e8892f8"]
                        }
                    },
                    "amount": {
                        "currencyCode": "EUR",
                        "amount": 300.0
                    },
                    "periodicityType": "BUDGET_PERIODICITY_TYPE_RECURRING",
                    "recurringPeriodicityData": {
                        "periodUnit": "MONTH"
                    }
                }
            }
        """
    }

    fun getAcknowledgeAction(): String {
        return """
            {
              "label": "Ok",
              "data": { "type": "ACKNOWLEDGE" }
            }
        """
    }

    fun getRefreshCredentialAction(credentialsId: String = "123"): String {
        return """
            {
                "label": "Refresh",
                "data": { 
                    "type": "REFRESH_CREDENTIAL",
                     "credentialId": $credentialsId
                }
            }
        """
    }

    fun getViewAccountAction(accountId: String = "d2b49640cbba4d8899a4886b6e8892f8"): String {
        return """
            {
                "label": "See details",
                "data": { 
                    "type": "VIEW_ACCOUNT",
                    "accountId": $accountId
                }
            }
        """
    }
}
