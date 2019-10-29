package se.tink.core.extensions

import se.tink.core.models.account.Account
import se.tink.core.models.transaction.Transaction
import se.tink.core.models.transfer.TransferDestination

fun Account.transactionsBelongingTo(accounts: Collection<Transaction>) =
    accounts.filter { it.accountId == this.id }.toSet()

fun List<Account>.transactionsBelongingToListOf(transactions: Collection<Transaction>) =
    this.map { it.transactionsBelongingTo(transactions) }
        .reduce { acc, set -> acc.plus(set) }

fun Account.toTransferDestination(): TransferDestination =
    TransferDestination().also {
        it.name = name
        it.balance = balance
        it.displayAccountNumber = accountNumber
        it.uri = uri
        it.images = images
    }

fun List<Account>.findByUri(accountUri: String) = find { it.identifiers.contains(accountUri) }
