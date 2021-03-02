package com.tink.moneymanagerui

import se.tink.android.repository.service.UserConfigurationService
import javax.inject.Inject


@Suppress("UNUSED_PARAMETER") //only created so the services get initialized
internal class ServiceCacheInitialization @Inject constructor(
    userConfigurationService: UserConfigurationService
)
