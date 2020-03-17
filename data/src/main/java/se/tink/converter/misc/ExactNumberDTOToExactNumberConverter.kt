package se.tink.converter.misc

import com.tink.model.misc.ExactNumber
import se.tink.modelConverter.AbstractConverter

private typealias ExactNumberDTO = se.tink.grpc.v1.models.ExactNumber

class ExactNumberDTOToExactNumberConverter : AbstractConverter<ExactNumberDTO, ExactNumber>() {
    override fun convert(source: ExactNumberDTO) = source.toCoreModel()
}

fun ExactNumberDTO.toCoreModel(): ExactNumber = ExactNumber(unscaledValue, scale)
