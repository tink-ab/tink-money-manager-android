package se.tink.repository.service;

import se.tink.core.models.account.Account;
import se.tink.repository.ChangeObserver;
import se.tink.repository.MutationHandler;

public interface AccountService extends TinkService {

	void subscribe(ChangeObserver<Account> listener);

	void unsubscribe(ChangeObserver<Account> listener);

	void update(Account account, MutationHandler<Account> handler);
}
