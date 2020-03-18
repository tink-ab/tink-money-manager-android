package se.tink.converter.misc

import com.tink.model.time.YearMonth
import se.tink.modelConverter.AbstractConverter

typealias YearMonthDTO = se.tink.grpc.v1.models.YearMonth

class YearMonthDTOToYearMonthConverter : AbstractConverter<YearMonthDTO, YearMonth>() {
    override fun convert(source: YearMonthDTO): YearMonth =
        YearMonth(year = source.month, month = source.month)
}
