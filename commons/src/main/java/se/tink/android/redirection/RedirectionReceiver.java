package se.tink.android.redirection;

import java.util.List;
import se.tink.core.models.misc.Amount;

public interface RedirectionReceiver {

	void showTransactionDetails(String transactionId);

	void showTransactionListForIds(List<String> transactionIds);

	void showAccountDetails(String accountId, boolean topOnly);

	void showTransfer(String transferId);

	void showTransfer(String sourceAccountId, String destinationAccountId, Amount amount);

	void showCategory(boolean topOnly);

	void showBudget(String id, String periodStart);
}
