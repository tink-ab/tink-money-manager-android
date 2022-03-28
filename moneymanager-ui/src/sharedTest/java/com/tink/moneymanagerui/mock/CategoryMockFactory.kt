package com.tink.moneymanagerui.mock

import org.json.JSONObject

object CategoryMockFactory {

    fun getFoodCategories(): List<JSONObject> {
        return listOf(
            getExpensesCategory(),
            getIncomeCategory(),
            getTransferCategory(),
            getFoodCategory(),
            getFoodCoffeeCategory(),
            getFoodGroceriesCategory(),
            getFoodOtherCategory()
        )
    }

    private fun getExpensesCategory(): JSONObject {
        return JSONObject(
            """
            {
                "code": "expenses",
                "defaultChild": false,
                "id": "15be09f7fe82405da1aa498fa92121fa",
                "parent": null,
                "primaryName": "Utgifter",
                "searchTerms": null,
                "secondaryName": null,
                "sortOrder": 1,
                "type": "EXPENSES",
                "typeName": "Utgifter"
            }
        """
        )
    }

    private fun getIncomeCategory(): JSONObject {
        return JSONObject(
            """
            {
                "code": "income",
                "defaultChild": false,
                "id": "0d5ab7626aa746ffb638a862aa1a386a",
                "parent": null,
                "primaryName": "Inkomster",
                "searchTerms": null,
                "secondaryName": null,
                "sortOrder": 55,
                "type": "INCOME",
                "typeName": "Inkomst"
            }
        """
        )
    }

    private fun getTransferCategory(): JSONObject {
        return JSONObject(
            """
            {
                "code": "transfers",
                "defaultChild": false,
                "id": "bba1bed16c564a908882301e62434b69",
                "parent": null,
                "primaryName": "Överföringar",
                "searchTerms": null,
                "secondaryName": null,
                "sortOrder": 68,
                "type": "TRANSFERS",
                "typeName": "Överföringar"
            }
        """
        )
    }

    private fun getFoodCategory(): JSONObject {
        return JSONObject(
            """
            {
                "code": "expenses:food",
                "defaultChild": false,
                "id": "47ea44117c6543178b3fefae8ffada52",
                "parent": "15be09f7fe82405da1aa498fa92121fa",
                "primaryName": "Mat & Dryck",
                "searchTerms": null,
                "secondaryName": null,
                "sortOrder": 15,
                "type": "EXPENSES",
                "typeName": "Utgifter"
            },
        """
        )
    }

    private fun getFoodCoffeeCategory(): JSONObject {
        return JSONObject(
            """
            {
                "code": "expenses:food.coffee",
                "defaultChild": false,
                "id": "63a7e66150d44c67a3380265c86e1c26",
                "parent": "47ea44117c6543178b3fefae8ffada52",
                "primaryName": "Mat & Dryck",
                "searchTerms": null,
                "secondaryName": "Fika & Snacks",
                "sortOrder": 18,
                "type": "EXPENSES",
                "typeName": "Utgifter"
            }
        """
        )
    }

    private fun getFoodOtherCategory(): JSONObject {
        return JSONObject(
            """
            {
                "code": "expenses:food.other",
                "defaultChild": true,
                "id": "049ff3909f4d46fc9cb63a4f9b048ece",
                "parent": "47ea44117c6543178b3fefae8ffada52",
                "primaryName": "Mat & Dryck",
                "searchTerms": null,
                "secondaryName": "Mat & Dryck Övrigt",
                "sortOrder": 21,
                "type": "EXPENSES",
                "typeName": "Utgifter"
            }
        """
        )
    }

    private fun getFoodGroceriesCategory(): JSONObject {
        return JSONObject(
            """
            {        
                "code": "expenses:food.groceries",
                "defaultChild": false,
                "id": "7e88d58188ee49749adca59e152324b7",
                "parent": "067fa4c769774ae980435c76be328c0b",
                "primaryName": "Food & Drinks",
                "searchTerms": "food,lunch,snacks",
                "secondaryName": "Groceries",
                "sortOrder": 85,
                "type": "EXPENSES",
                "typeName": "Expenses"
            }
        """
        )
    }
}
