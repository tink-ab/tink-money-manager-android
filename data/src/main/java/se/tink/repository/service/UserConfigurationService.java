package se.tink.repository.service;

import se.tink.core.models.user.UserConfiguration;
import se.tink.repository.ObjectChangeObserver;

public interface UserConfigurationService extends TinkService {

	void subscribe(ObjectChangeObserver<UserConfiguration> listener);

	void unsubscribe(ObjectChangeObserver<UserConfiguration> listener);

	void refreshUserConfiguration();
}
