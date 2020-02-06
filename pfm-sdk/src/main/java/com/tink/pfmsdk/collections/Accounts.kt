package com.tink.pfmsdk.collections

import com.google.common.base.Predicate
import com.google.common.collect.Iterables
import com.google.common.collect.Lists
import se.tink.core.models.account.Account
import java.util.NoSuchElementException

internal object Accounts {

    @JvmStatic
    fun filterAccountsByCredentialsId(
        accounts: List<Account>?,
        credentialsId: String?
    ): List<Account> {
        val predicate =
            Predicate<Account> { input: Account? ->
                input != null && input.credentialId == credentialsId
            }
        val filtered =
            Iterables.filter(
                accounts!!,
                predicate
            )
        return Lists.newArrayList(
            filtered
        )
    }

    @JvmStatic
    fun filterOutExcludedAndLoansAndClosed(accounts: List<Account>?): List<Account> {
        val predicate =
            Predicate<Account> { input: Account? -> !input!!.isExcluded && !input.isLoan && !input.isClosed }
        val filtered =
            Iterables.filter(
                accounts!!,
                predicate
            )
        return Lists.newArrayList(
            filtered
        )
    }

    @JvmStatic
    fun filterSavingsNonExcludedAndClosed(accounts: List<Account>?): List<Account> {
        val predicate =
            Predicate<Account> { input: Account? -> input != null && !input.isExcluded && !input.isClosed && input.isSavings }
        val filtered =
            Iterables.filter(
                accounts!!,
                predicate
            )
        return Lists.newArrayList(
            filtered
        )
    }

    @JvmStatic
    fun filterEverydayNonExcludedAndClosed(accounts: List<Account>?): List<Account> {
        val predicate =
            Predicate<Account> { input: Account? -> input != null && !input.isExcluded && !input.isClosed && input.isEveryday }
        val filtered =
            Iterables.filter(
                accounts!!,
                predicate
            )
        return Lists.newArrayList(
            filtered
        )
    }

    @JvmStatic
    fun filterLoanNonExcludedAndClosed(accounts: List<Account>?): List<Account> {
        val predicate =
            Predicate<Account> { input: Account? -> input != null && !input.isExcluded && !input.isClosed && input.isLoan }
        val filtered =
            Iterables.filter(
                accounts!!,
                predicate
            )
        return Lists.newArrayList(
            filtered
        )
    }

    @JvmStatic
    fun filterNonFavouredNonExcludedAndClosed(accounts: List<Account>?): List<Account> {
        val predicate =
            Predicate<Account> { input: Account? -> input != null && !input.isExcluded && !input.isClosed && !input.isFavored }
        val filtered =
            Iterables.filter(
                accounts!!,
                predicate
            )
        return Lists.newArrayList(
            filtered
        )
    }

    @JvmStatic
    fun getAccountById(
        id: String,
        accounts: List<Account>?
    ): Account? {
        return try {
            Iterables.find(
                accounts!!
            ) { input: Account? -> input!!.id == id }
        } catch (ex: NoSuchElementException) {
            null
        }
    }

    @JvmStatic
    fun add(
        currentAccounts: List<Account>,
        toAddAccounts: List<Account>
    ): List<Account> {
        val modifiedAccounts =
            copyList(currentAccounts)
        modifiedAccounts.addAll(toAddAccounts)
        return modifiedAccounts
    }

    @JvmStatic
    fun replace(
        currentAccounts: List<Account>,
        toAddAccounts: List<Account>
    ): List<Account> {
        val modifiedAccounts =
            delete(currentAccounts, toAddAccounts)
        modifiedAccounts.addAll(toAddAccounts)
        return modifiedAccounts
    }

    @JvmStatic
    fun delete(
        currentAccounts: List<Account>,
        toDeleteAccounts: List<Account>
    ): MutableList<Account> {
        val modifiedAccounts =
            copyList(currentAccounts)
        modifiedAccounts.removeAll(toDeleteAccounts)
        return modifiedAccounts
    }

    private fun copyList(currentAccounts: List<Account>): MutableList<Account> =
        Lists.newArrayList(currentAccounts)

    @JvmStatic
    fun hasTransactionalAccounts(accounts: Collection<Account>): Boolean {
        for (account in accounts) {
            if (account.isTransactional) {
                return true
            }
        }
        return false
    }
}