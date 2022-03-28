package com.tink.moneymanagerui.di

import com.tink.annotations.PfmScope
import com.tink.service.user.UserProfileService
import com.tink.service.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import org.conscrypt.Conscrypt
import se.tink.android.repository.service.UserConfigurationService
import se.tink.android.repository.service.UserConfigurationServiceCachedImpl
import java.security.Security

@Module
internal class ServiceModule {
    @Provides
    @PfmScope
    fun userConfigurationService(
        userService: UserProfileService?,
        dispatcher: DispatcherProvider
    ): UserConfigurationService {
        return UserConfigurationServiceCachedImpl(userService!!, dispatcher)
    }

    init {
        Security.insertProviderAt(Conscrypt.newProvider("GmsCore_OpenSSL"), 1)
    }
}
