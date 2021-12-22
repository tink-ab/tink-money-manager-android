package se.tink.android.repository.service;

import com.tink.model.user.UserProfile;
import com.tink.service.observer.ChangeObserver;

@Deprecated
public interface UserConfigurationService {

	void subscribe(ChangeObserver<UserProfile> listener);

	void unsubscribe(ChangeObserver<UserProfile> listener);

	void refreshUserConfiguration();
}
