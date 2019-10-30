package se.tink.repository.service;

import java.util.List;
import se.tink.core.models.provider.Provider;
import se.tink.repository.ChangeObserver;
import se.tink.repository.MutationHandler;

public interface ProviderService extends TinkService {

	void subscribe(ChangeObserver<Provider> listener);

	void unsubscribe(ChangeObserver<Provider> listener);

	void listSuggestions(MutationHandler<List<Provider>> handler);

	void listProviders(MutationHandler<List<Provider>> mutationHandler, boolean includeDemoProviders);
}
