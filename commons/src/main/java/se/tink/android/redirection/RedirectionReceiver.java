package se.tink.android.redirection;

import java.util.List;
import java.util.Map;
import se.tink.core.models.misc.Amount;

public interface RedirectionReceiver {

	void showOverview();

	void showFeed();

	void showAccountsList();

	void showProfile();

	void showTransactionDetails(String transactionId);

	void showTransactionListForIds(List<String> transactionIds);

	void showLeftToSpend(String periodKey, boolean topOnly);

	void showAccountDetails(String accountId, boolean topOnly);

	void showIdControlDetail(String id, boolean topOnly);

	void showIdControlList(boolean topOnly);

	void showSearch(String query, boolean topOnly);

	void showTransfer(String transferId);

	void showTransfer(String sourceAccountId, String destinationAccountId, Amount amount);

	void showCredentialsDetail(String id, boolean topOnly);

	void showProviders(boolean topOnly);

	void showAddProvider(String name, boolean topOnly);

	void showCategory(boolean topOnly);

	void showRateThisApp(boolean agreedToRate);

	void showSuggestions();

	void showBudget(String id, String periodStart);

	void handleThirdPartyCallbackResult(String state, Map<String, String> parameters);

	void handleIceCreamHackVisibility();
}
