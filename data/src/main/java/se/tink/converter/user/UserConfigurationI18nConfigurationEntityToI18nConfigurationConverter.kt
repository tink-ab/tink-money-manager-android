package se.tink.converter.user

import se.tink.core.models.user.UserConfigurationI18NConfiguration
import se.tink.modelConverter.AbstractConverter
import se.tink.repository.cache.models.I18ConfigurationEntity

class UserConfigurationI18nConfigurationEntityToI18nConfigurationConverter :
    AbstractConverter<I18ConfigurationEntity, UserConfigurationI18NConfiguration>() {

    override fun convert(source: I18ConfigurationEntity): UserConfigurationI18NConfiguration {
        val destination = UserConfigurationI18NConfiguration()
        destination.currencyCode = source.currencyCode
        destination.localeCode = source.localeCode
        destination.marketCode = source.marketCode
        destination.timezoneCode = source.timezoneCode
        return destination
    }

    override fun getSourceClass(): Class<I18ConfigurationEntity> {
        return I18ConfigurationEntity::class.java
    }

    override fun getDestinationClass(): Class<UserConfigurationI18NConfiguration> {
        return UserConfigurationI18NConfiguration::class.java
    }
}
