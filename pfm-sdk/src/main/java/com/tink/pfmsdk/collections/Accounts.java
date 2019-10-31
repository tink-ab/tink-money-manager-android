package com.tink.pfmsdk.collections;

import android.content.Context;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.tink.pfmsdk.ClientDataStorage;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import se.tink.core.models.account.Account;
import se.tink.core.models.transfer.Transfer;
import se.tink.core.models.transfer.TransferDestinationPerAccount;
import se.tink.helpers.CollectionsHelper;

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

	public static Account getMostLikelyFromAccount(List<Account> accounts,
		List<TransferDestinationPerAccount> transferDestinationsPerAccount, Transfer.Type type,
		Context context) {
		String mostLikelyFromAccountId = getMostLikelyFromAccountId(accounts, transferDestinationsPerAccount, type, context);
		if (mostLikelyFromAccountId == null) {
			return null;
		}

		return getAccountById(mostLikelyFromAccountId, accounts);
	}

	public static String getMostLikelyFromAccountId(List<Account> accounts,
		final List<TransferDestinationPerAccount> transferDestinationsPerAccount, final Transfer.Type type,
		Context context) {
		if (accounts == null || context == null) {
			return null;
		}

		final String latestTransferFromAccount = ClientDataStorage.sharedInstance(context)
			.getLatestTransferFromAccount();
		String result = null;
		if (latestTransferFromAccount != null) {
			boolean contains = CollectionsHelper.contains(accounts,
				input -> input.getId().equals(latestTransferFromAccount));

			if (contains) {
				result = latestTransferFromAccount;
			}
		}

		if (result == null) {
			Account account = CollectionsHelper.findFirst(accounts,
				input -> accountHasCorrectDestinationForType(input, transferDestinationsPerAccount, type));
			if (account != null) {
				result = account.getId();
			}
		}
		return result;
	}

	public static boolean accountHasCorrectDestinationForType(final Account account, List<TransferDestinationPerAccount> transferDestinationsPerAccount, final Transfer.Type type) {
		return Iterables.any(transferDestinationsPerAccount,
			transferDestinationPerAccount -> account.getId().equals(transferDestinationPerAccount.getAccountId())
				&& destinationsHasCorrectTransferDestinationForType(
				transferDestinationPerAccount, type));
	}


	private static boolean destinationsHasCorrectTransferDestinationForType(
		TransferDestinationPerAccount destinationPerAccount, final Transfer.Type type) {
		return Iterables.any(destinationPerAccount.getDestinations(),
			input -> {
				switch (type) {
					case TYPE_PAYMENT:
					case TYPE_EINVOICE:
						return input.isPayable() || input.isMatchesMultiple();
					case TYPE_BANK_TRANSFER:
						return input.isTransferable();
				}
				return false;
			});
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
