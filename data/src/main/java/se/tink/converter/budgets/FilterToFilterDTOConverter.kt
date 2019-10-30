package se.tink.converter.budgets

import se.tink.core.models.budgets.Budget.Specification.Filter
import se.tink.modelConverter.AbstractConverter

class FilterToFilterDTOConverter : AbstractConverter<Filter, FilterDTO>() {
    override fun convert(source: Filter): FilterDTO {
        val builder = FilterDTO.newBuilder()
        source.let {
            builder
                .addAllAccounts(it.accounts.map {
                    FilterDTOAccount.newBuilder().setId(it.id).build()
                })
                .addAllCategories(it.categories.map {
                    FilterDTOCategory.newBuilder().setCode(it.code).build()
                })
                .addAllTags(it.tags.map {
                    FilterDTOTag.newBuilder().setKey(it.key).build()
                })
                .setFreeTextQuery(it.freeTextQuery)
        }
        return builder.build()
    }
}
