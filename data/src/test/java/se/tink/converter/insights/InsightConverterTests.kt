package se.tink.converter.insights

import org.junit.jupiter.api.Test
import se.tink.converter.ModelConverter
import se.tink.converter.ModelConverterImpl
import se.tink.core.models.insights.Insight


internal class InsightConverterTests {

    private val modelConverter: ModelConverter = ModelConverterImpl()


    @Test
    fun `DTO default instance conversion does not crash`() {
        modelConverter.map(InsightDTO.getDefaultInstance(), Insight::class.java)
    }

    @Test
    fun `DTO full instance conversion does not crash`() {
        modelConverter.map(InsightTestInstances.fullTestDTO, Insight::class.java)
    }
}