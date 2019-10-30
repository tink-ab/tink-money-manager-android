package se.tink.converter.settings

import se.tink.core.models.device.I18NSettings
import se.tink.modelConverter.AbstractConverter

class I18NSettingsConverter : AbstractConverter<se.tink.grpc.v1.models.I18NSettings, I18NSettings>() {

    override fun convert(source: se.tink.grpc.v1.models.I18NSettings): I18NSettings {
        val destination = I18NSettings()
        destination.localeCode = source.localeCode
        return destination
    }

    override fun getSourceClass(): Class<se.tink.grpc.v1.models.I18NSettings> {
        return se.tink.grpc.v1.models.I18NSettings::class.java
    }

    override fun getDestinationClass(): Class<I18NSettings> {
        return I18NSettings::class.java
    }
}
