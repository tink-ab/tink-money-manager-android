package se.tink.repository.service;

import se.tink.core.models.transfer.SignableOperation;
import se.tink.repository.ChangeObserver;

public interface SignableOperationService extends TinkService {

	void subscribe(ChangeObserver<SignableOperation> listener);

	void unsubscribe(ChangeObserver<SignableOperation> listener);
}
