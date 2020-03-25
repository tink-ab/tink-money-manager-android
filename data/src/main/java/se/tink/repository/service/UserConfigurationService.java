package se.tink.repository.service;

import com.tink.model.user.UserProfile;
import com.tink.service.observer.ChangeObserver;

public interface UserConfigurationService {

	void subscribe(ChangeObserver<UserProfile> listener);

	void unsubscribe(ChangeObserver<UserProfile> listener);

	void refreshUserConfiguration();
}
