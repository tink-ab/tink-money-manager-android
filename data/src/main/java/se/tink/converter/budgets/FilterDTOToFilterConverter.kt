package se.tink.converter.budgets
//
//import com.tink.model.budget.Budget.Specification.Filter
//import se.tink.modelConverter.AbstractConverter
//
//class FilterDTOToFilterConverter : AbstractConverter<FilterDTO, Filter>() {
//
//    override fun convert(source: FilterDTO): Filter {
//        return with(source) {
//            Filter(
//                freeTextQuery = freeTextQuery ?: "",
//                accounts = accountsOrEmpty(),
//                categories = categoriesOrEmpty(),
//                tags = tagsOrEmpty()
//            )
//        }
//    }
//
//    private fun FilterDTO?.categoriesOrEmpty(): List<Filter.Category> {
//        return this?.categoriesList?.map {
//            Filter.Category(it.code ?: "")
//        }.orEmpty()
//    }
//
//    private fun FilterDTO?.accountsOrEmpty(): List<Filter.Account> {
//        return this?.accountsList?.map {
//            Filter.Account(it.id ?: "")
//        }.orEmpty()
//    }
//
//    private fun FilterDTO?.tagsOrEmpty(): List<Filter.Tag> {
//        return this?.tagsList?.map {
//            Filter.Tag(it.key ?: "")
//        }.orEmpty()
//    }
//
//
//    override fun getSourceClass(): Class<FilterDTO> = FilterDTO::class.java
//    override fun getDestinationClass(): Class<Filter> = Filter::class.java
//}
