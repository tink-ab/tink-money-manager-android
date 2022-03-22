package se.tink.android.codegen

import com.google.auto.service.AutoService
import com.google.common.base.CaseFormat
import se.tink.android.annotations.ContributesInsightViewProvider
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedOptions
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

private const val TAB = "    "
private const val ENUM_NAME_SUFFIX = "_VIEW_TYPE"
private const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"

@AutoService(Processor::class) // For registering the service
@SupportedOptions(KAPT_KOTLIN_GENERATED_OPTION_NAME)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
class InsightViewProviderDependencyGenerator : AbstractProcessor() {

    override fun process(
        set: MutableSet<out TypeElement>?,
        roundEnvironment: RoundEnvironment
    ): Boolean {

        val elements =
            roundEnvironment.getElementsAnnotatedWith(ContributesInsightViewProvider::class.java)

        val options = processingEnv.options
        val kotlinGenerated = options[KAPT_KOTLIN_GENERATED_OPTION_NAME]

        val packageName = "se.tink.insights"

        if (elements.size == 0) {
            print("Warning: no elements")
            return true
        }

        val basePath = packageName.replace('.', File.separatorChar)
        val kotlinGeneratedPath = kotlinGenerated?.replace("kaptKotlin", "kapt")
        val kaptKotlinGenerated = File(kotlinGeneratedPath)
        val folder = File(kaptKotlinGenerated, basePath).apply {
            if (!exists()) {
                mkdirs()
            }
        }

        val enumClassName = "InsightViewType"

        File(folder, "$enumClassName.kt").writer().buffered().use { writer ->
            with(writer) {
                appendln(
                    """package $packageName
                    |
                    |${elements.joinToString("") { "import ${it.enclosingElement}.${it.simpleName}\n" }}
                    |
                    |enum class $enumClassName {
                    |${elements.joinToString(",\n") { "$TAB${it.enumName()}" }};
                    |
                    |   fun toInt() = ordinal
                    |
                    |   companion object {
                    |       fun fromInt(viewType: Int) = values()[viewType]
                    |   }
                    |}
                    |
                    """.trimMargin()
                )
                for (element in elements) {
                    appendln("fun ${element.simpleName}.getViewType() = $enumClassName.${element.enumName()}")
                    appendln()
                }
            }
        }
        return true
    }

    private fun Element.enumName() = CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.UPPER_UNDERSCORE)
        .convert(simpleName.toString()) + ENUM_NAME_SUFFIX

    override fun getSupportedAnnotationTypes(): Set<String> {
        return setOf(ContributesInsightViewProvider::class.java.canonicalName)
    }
}
