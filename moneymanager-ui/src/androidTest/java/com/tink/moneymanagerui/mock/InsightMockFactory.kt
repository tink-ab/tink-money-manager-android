package com.tink.moneymanagerui.mock

import com.tink.model.insights.*
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
        InsightType.DOUBLE_CHARGE,
        InsightType.LARGE_EXPENSE,
        InsightType.MONTHLY_SUMMARY_EXPENSES_BY_CATEGORY,
        InsightType.MONTHLY_SUMMARY_EXPENSE_TRANSACTIONS,
        InsightType.SINGLE_UNCATEGORIZED_TRANSACTION,
        InsightType.WEEKLY_SUMMARY_EXPENSES_BY_CATEGORY,
        InsightType.WEEKLY_SUMMARY_EXPENSES_BY_DAY,
        InsightType.WEEKLY_SUMMARY_EXPENSE_TRANSACTIONS,
        InsightType.WEEKLY_UNCATEGORIZED_TRANSACTIONS,
    )

    fun getInsightForType(type: InsightType): JSONObject {
        return when (type) {
            InsightType.ACCOUNT_BALANCE_LOW -> getAccountBalanceLow()
            InsightType.BUDGET_SUGGEST_CREATE_TOP_CATEGORY -> getBudgetSuggestCreateTopCategory()
            InsightType.BUDGET_SUGGEST_CREATE_TOP_PRIMARY_CATEGORY -> getBudgetSuggestCreateTopPrimaryCategory()
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


    private fun getAccountBalanceLow(): JSONObject {
        return JSONObject("""
            {
                "userId": 1234,
                "id": 1,
                "type": "ACCOUNT_BALANCE_LOW",
                "title": "Title",
                "description": "Description",
                "createdTime": 1111111,
                "insightActions": [],
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

    fun getBudgetSuggestCreateTopCategory(): JSONObject {
        return JSONObject("""
            {
                "type": "BUDGET_SUGGEST_CREATE_TOP_CATEGORY",
                "userId": 1234,
                "id": 1,
                "title": "Title",
                "description": "Description",
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

    private fun getBudgetSuggestCreateTopPrimaryCategory(): JSONObject {
        return JSONObject("""
            {
                "type": "BUDGET_SUGGEST_CREATE_TOP_PRIMARY_CATEGORY",
                "userId": 1234,
                "id": 1,
                "title": "Title",
                "description": "Description",
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

    private fun getMonthlySummaryExpenseByCategory(): JSONObject {
        return JSONObject("""
            {
                "type": "MONTHLY_SUMMARY_EXPENSES_BY_CATEGORY",
                "userId": 1234,
                "id": 1,
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

    private fun getMonthlySummaryExpensesTransactions(): JSONObject {
        return JSONObject("""
            {
                "userId": 1234,
                "id": 1,
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

    private fun getNewIncomeTransaction(): JSONObject {
        return JSONObject("""
            {
                "userId": 1234,
                "id": 1,
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
        return JSONObject("""
            {
                "userId": 1234,
                "id": 1,
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

    private fun getWeeklySummaryExpensesByCategory(): JSONObject {
        return JSONObject("""
            {
                "userId": 1234,
                "id": 1,
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

    private fun getWeeklySummaryExpensesByDay(): JSONObject {
        return JSONObject("""
            {
                "userId": 1234,
                "id": 1,
                "type": "WEEKLY_SUMMARY_EXPENSES_BY_DAY",
                "title": "Title",
                "description": "Description",
                "createdTime": 1111111,
                "data": {
                    "type": "WEEKLY_SUMMARY_EXPENSES_BY_DAY",
                    "week": {
                        "year": 2019,
                        "week": 43
                    },
                    "expenseStatisticsByDay": [
                        {
                            "date": [2019,10,27],
                            "expenseStatistics": {
                                "totalAmount": {
                                    "currencyCode": "EUR",
                                    "amount": 57
                                },
                                "averageAmount": {
                                    "currencyCode": "EUR",
                                    "amount": 60
                                }
                            }
                        },
                        {
                            "date": [2019,10,26],
                            "expenseStatistics": {
                                "totalAmount": {
                                    "currencyCode": "EUR",
                                    "amount": 63.5
                                },
                                "averageAmount": {
                                    "currencyCode": "EUR",
                                    "amount": 71
                                }
                            }
                        }
                    ]
                }
            }"""
        )
    }

    private fun getWeeklySummaryExpensesTransactions(): JSONObject {
        return JSONObject("""
            {
                "userId": 1234,
                "id": 1,
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
        return JSONObject("""
            {
                "userId": 1234,
                "id": 1,
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

    private fun getDefaultInsight(type: InsightType): JSONObject {
        return JSONObject("""
            {
                "userId": 1234,
                "id": 1,
                "type": "$type",
                "title": "Title",
                "description": "Description",
                "createdTime": 1111111
            }"""
        )
    }

    private fun getDismissAction() = """ 
        {
            "label": "Dismiss",
            "data":{ "type": "DISMISS" }
        }
    """

    private fun getCreateBudgetAction(): String {
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

    private fun getAcknowledgeAction(): String {
        return """
            {
              "label": "Ok",
              "data": { "type": "ACKNOWLEDGE" }
            }
        """
    }
}