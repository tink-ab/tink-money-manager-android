package se.tink.converter.insights

import com.tink.model.insights.Insight
import org.junit.jupiter.api.Test
import se.tink.converter.ModelConverter
import se.tink.converter.ModelConverterImpl


internal class InsightConverterTests {

    private val modelConverter: ModelConverter = ModelConverterImpl()

// TODO: Move to core
//
//    @Test
//    fun `DTO default instance conversion does not crash`() {
//        modelConverter.map(InsightDTO.getDefaultInstance(), Insight::class.java)
//    }
//
//    @Test
//    fun `DTO full instance conversion does not crash`() {
//        modelConverter.map(InsightTestInstances.fullTestDTO, Insight::class.java)
//    }
}