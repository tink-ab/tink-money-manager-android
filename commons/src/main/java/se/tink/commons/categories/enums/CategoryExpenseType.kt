package se.tink.commons.categories.enums

enum class CategoryExpenseType(val type: String, val code: String) {
    EXPENSES_ALL("allexpenses", "expenses"),
    EXPENSES_ENTERTAINMENT("entertainment", "expenses:entertainment"),
    EXPENSES_FOOD("fooddrinks", "expenses:food"),
    EXPENSES_HOME("home", "expenses:home"),
    EXPENSES_HOUSE("house", "expenses:house"),
    EXPENSES_MISC("other", "expenses:misc"),
    EXPENSES_SHOPPING("shopping", "expenses:shopping"),
    EXPENSES_TRANSPORT("transport", "expenses:transport"),
    EXPENSES_WELLNESS("health", "expenses:wellness");
}