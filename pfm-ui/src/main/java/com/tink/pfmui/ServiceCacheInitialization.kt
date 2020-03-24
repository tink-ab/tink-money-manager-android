package com.tink.pfmui

import se.tink.repository.service.StatisticService
import se.tink.repository.service.TransactionService
import se.tink.repository.service.UserConfigurationService
import javax.inject.Inject


@Suppress("UNUSED_PARAMETER") //only created so the services get initialized
internal class ServiceCacheInitialization @Inject constructor(
    statisticService: StatisticService,
    transactionService: TransactionService,
    userConfigurationService: UserConfigurationService
)
