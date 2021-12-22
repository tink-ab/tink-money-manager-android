package se.tink.commons.categories.enums

enum class CategoryTransferType(val type: String, val code: String) {
    TRANSFERS_EXCLUDE("exclude", "transfers:exclude"),
    TRANSFERS_OTHER("other", "transfers:other"),
    TRANSFERS_SAVINGS("savings", "transfers:savings");
}