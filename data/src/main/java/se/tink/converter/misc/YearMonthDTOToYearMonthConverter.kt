package se.tink.converter.misc

import se.tink.core.models.misc.YearMonth
import se.tink.modelConverter.AbstractConverter

typealias YearMonthDTO = se.tink.grpc.v1.models.YearMonth

class YearMonthDTOToYearMonthConverter : AbstractConverter<YearMonthDTO, YearMonth>() {
    override fun convert(source: YearMonthDTO): YearMonth =
        YearMonth(year = source.month, month = source.month)
}
