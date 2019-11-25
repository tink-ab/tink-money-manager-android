package se.tink.repository.service

import se.tink.core.models.account.Account
import se.tink.core.models.category.CategoryTree
import se.tink.core.models.credential.Credential
import se.tink.core.models.statistic.StatisticTree
import se.tink.core.models.transaction.Transaction
import se.tink.repository.MutationHandler
import javax.inject.Inject

interface DataRefreshHandler {
    fun refreshCategories(handler: MutationHandler<CategoryTree>)
    fun refreshStatistics(handler: MutationHandler<StatisticTree>)
    fun refreshTransactions(handler: MutationHandler<List<Transaction>>)
    fun refreshAccounts(handler: MutationHandler<List<Account>>)
    fun refreshCredentials(handler: MutationHandler<List<Credential>>)
}

class DataRefreshHandlerImpl @Inject constructor(
    private val categoryService: CategoryService,
    private val statisticService: StatisticService,
    private val transactionService: TransactionService,
    private val accountService: AccountService,
    private val credentialService: CredentialService
) : DataRefreshHandler {

    override fun refreshCategories(handler: MutationHandler<CategoryTree>) = categoryService.list(handler)

    override fun refreshStatistics(handler: MutationHandler<StatisticTree>) = statisticService.getStatistics(handler)

    override fun refreshTransactions(handler: MutationHandler<List<Transaction>>) {
        // TODO: PFMSDK: How do we do this?
    }

    override fun refreshAccounts(handler: MutationHandler<List<Account>>) {
        // TODO: PFMSDK: Do we need to do this?
    }

    override fun refreshCredentials(handler: MutationHandler<List<Credential>>) {
        // TODO: PFMSDK: Do we need to do this?
    }
}