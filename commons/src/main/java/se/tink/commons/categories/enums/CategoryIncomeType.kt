package se.tink.commons.categories.enums

enum class CategoryIncomeType(val type: String, val code: String) {
    INCOME_BENEFITS("benefits", "income:benefits"),
    INCOME_FINANCIAL("financial", "income:financial"),
    INCOME_OTHER("other", "income:other"),
    INCOME_PENSION("pension", "income:pension"),
    INCOME_REFUND("refund", "income:refund"),
    INCOME_SALARY("salary", "income:salary");
}
