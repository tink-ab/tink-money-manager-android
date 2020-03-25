package com.tink.pfmui

import se.tink.repository.service.StatisticService
import com.tink.service.transaction.TransactionService
import se.tink.repository.service.UserConfigurationService
import javax.inject.Inject


@Suppress("UNUSED_PARAMETER") //only created so the services get initialized
internal class ServiceCacheInitialization @Inject constructor(
    statisticService: StatisticService,
    userConfigurationService: UserConfigurationService
)
