package com.tink.pfmsdk.collections;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import se.tink.core.models.account.Account;

public class Accounts {

	private static final Accounts sharedInstance = new Accounts();

	public static Accounts getInstance() {
		return sharedInstance;
	}

	private Accounts() {
	}

	public static List<Account> filterAccountsByCredentialsId(List<Account> accounts,
		final String credentialsId) {

		Predicate<Account> predicate = input ->
			input != null && Objects.equals(input.getCredentialId(), credentialsId);

		Iterable<Account> filtered = Iterables.filter(accounts, predicate);
		return Lists.newArrayList(filtered);
	}

	public static List<Account> filterOutExcludedAndLoansAndClosed(List<Account> accounts) {
		Predicate<Account> predicate = input ->
			!input.isExcluded() && !input.isLoan() && !input.isClosed();
		Iterable<Account> filtered = Iterables.filter(accounts, predicate);
		return Lists.newArrayList(filtered);
	}

	public static List<Account> filterSavingsNonExcludedAndClosed(List<Account> accounts) {
		Predicate<Account> predicate = input ->
			input != null && !input.isExcluded() && !input.isClosed() && input.isSavings();
		Iterable<Account> filtered = Iterables.filter(accounts, predicate);
		return Lists.newArrayList(filtered);
	}

	public static List<Account> filterEverydayNonExcludedAndClosed(List<Account> accounts) {
		Predicate<Account> predicate = input ->
			input != null && !input.isExcluded() && !input.isClosed() && input.isEveryday();

		Iterable<Account> filtered = Iterables.filter(accounts, predicate);
		return Lists.newArrayList(filtered);
	}

	public static List<Account> filterLoanNonExcludedAndClosed(List<Account> accounts) {
		Predicate<Account> predicate = input ->
			input != null && !input.isExcluded() && !input.isClosed() && input.isLoan();

		Iterable<Account> filtered = Iterables.filter(accounts, predicate);
		return Lists.newArrayList(filtered);
	}

	public static List<Account> filterNonFavouredNonExcludedAndClosed(List<Account> accounts) {
		Predicate<Account> predicate = input ->
			input != null && !input.isExcluded() && !input.isClosed() && !input.isFavored();

		Iterable<Account> filtered = Iterables.filter(accounts, predicate);
		return Lists.newArrayList(filtered);
	}

	public static Account getAccountById(final String id, List<Account> accounts) {
		try {
			return Iterables.find(accounts, input -> input.getId().equals(id));
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	public static List<Account> add(List<Account> currentAccounts, List<Account> toAddAccounts) {
		List<Account> modifiedAccounts = copyList(currentAccounts);
		modifiedAccounts.addAll(toAddAccounts);
		return modifiedAccounts;
	}

	public static List<Account> replace(List<Account> currentAccounts,
		List<Account> toAddAccounts) {
		List<Account> modifiedAccounts = delete(currentAccounts, toAddAccounts);
		modifiedAccounts.addAll(toAddAccounts);
		return modifiedAccounts;
	}

	public static List<Account> delete(List<Account> currentAccounts,
		List<Account> toDeleteAccounts) {
		List<Account> modifiedAccounts = copyList(currentAccounts);
		modifiedAccounts.removeAll(toDeleteAccounts);
		return modifiedAccounts;
	}

	private static List<Account> copyList(List<Account> currentAccounts) {
		List<Account> modifiedAccounts;
		if (currentAccounts == null) {
			modifiedAccounts = Lists.newArrayList();
		} else {
			modifiedAccounts = Lists.newArrayList(currentAccounts);
		}
		return modifiedAccounts;
	}

	public static boolean hasTransactionalAccounts(Collection<Account> accounts) {
		for (Account account : accounts) {
			if (account.isTransactional()) {
				return true;
			}
		}
		return false;

	}
}
