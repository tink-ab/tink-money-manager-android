package se.tink.converter.misc

import se.tink.core.models.misc.YearWeek
import se.tink.modelConverter.AbstractConverter

typealias YearWeekDTO = se.tink.grpc.v1.models.YearWeek

class YearWeekDTOToYearWeekConverter : AbstractConverter<YearWeekDTO, YearWeek>() {
    override fun convert(source: YearWeekDTO): YearWeek =
        YearWeek(year = source.year, week = source.week)
}
