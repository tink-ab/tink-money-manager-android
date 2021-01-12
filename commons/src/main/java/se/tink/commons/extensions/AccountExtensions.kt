package se.tink.commons.extensions

import com.tink.model.account.Account
import com.tink.model.transaction.Transaction

fun Account.transactionsBelongingTo(accounts: Collection<Transaction>) =
    accounts.filter { it.accountId == this.id }.toSet()

fun List<Account>.transactionsBelongingToListOf(transactions: Collection<Transaction>) =
    this.map { it.transactionsBelongingTo(transactions) }
        .reduce { acc, set -> acc.plus(set) }
