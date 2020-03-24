package se.tink.repository.service;

import com.tink.model.user.UserProfile;
import se.tink.repository.ObjectChangeObserver;

public interface UserConfigurationService extends TinkService {

	void subscribe(ObjectChangeObserver<UserProfile> listener);

	void unsubscribe(ObjectChangeObserver<UserProfile> listener);

	void refreshUserConfiguration();
}
