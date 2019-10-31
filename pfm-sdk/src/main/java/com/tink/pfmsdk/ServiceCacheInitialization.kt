package com.tink.pfmsdk

import se.tink.repository.service.AccountService
import se.tink.repository.service.CategoryService
import se.tink.repository.service.CredentialService
import se.tink.repository.service.FollowService
import se.tink.repository.service.PeriodService
import se.tink.repository.service.ProviderService
import se.tink.repository.service.SignableOperationService
import se.tink.repository.service.StatisticService
import se.tink.repository.service.TransactionService
import se.tink.repository.service.UserConfigurationService
import javax.inject.Inject


@Suppress("UNUSED_PARAMETER") //only created so the services get initialized
class ServiceCacheInitialization @Inject constructor(
    accountService: AccountService,
    categoryService: CategoryService,
    credentialService: CredentialService,
    followService: FollowService,
    periodService: PeriodService,
    providerService: ProviderService,
    statisticService: StatisticService,
    transactionService: TransactionService,
    userConfigurationService: UserConfigurationService,
    signableOperationService: SignableOperationService
)
