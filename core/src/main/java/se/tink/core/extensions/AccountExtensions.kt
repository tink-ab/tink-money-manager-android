package se.tink.core.extensions

import com.tink.model.account.Account
import se.tink.core.models.transaction.Transaction

fun Account.transactionsBelongingTo(accounts: Collection<Transaction>) =
    accounts.filter { it.accountId == this.id }.toSet()

fun List<Account>.transactionsBelongingToListOf(transactions: Collection<Transaction>) =
    this.map { it.transactionsBelongingTo(transactions) }
        .reduce { acc, set -> acc.plus(set) }
