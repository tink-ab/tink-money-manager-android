package com.tink.moneymanagerui.di

import dagger.Provides
import com.tink.annotations.PfmScope
import com.tink.service.user.UserProfileService
import dagger.Module
import se.tink.android.repository.service.UserConfigurationService
import se.tink.android.repository.service.UserConfigurationServiceCachedImpl
import org.conscrypt.Conscrypt
import java.security.Security

@Module
internal class ServiceModule {
    @Provides
    @PfmScope
    fun userConfigurationService(
        userService: UserProfileService?
    ): UserConfigurationService {
        return UserConfigurationServiceCachedImpl(userService!!)
    }

    init {
        Security.insertProviderAt(Conscrypt.newProvider("GmsCore_OpenSSL"), 1)
    }
}