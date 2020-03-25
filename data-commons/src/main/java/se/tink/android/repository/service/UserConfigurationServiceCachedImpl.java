package se.tink.android.repository.service;

import com.google.common.collect.Lists;
import com.tink.model.user.UserProfile;
import com.tink.service.handler.ResultHandler;
import com.tink.service.observer.ChangeObserver;
import com.tink.service.user.UserProfileService;
import java.util.List;
import javax.inject.Inject;

//TODO: Core setup - Reevaluate
public class UserConfigurationServiceCachedImpl implements UserConfigurationService {

	private List<ChangeObserver<UserProfile>> changeObservers;
	private UserProfileService userService;

	@Inject
	public UserConfigurationServiceCachedImpl(UserProfileService userService) {
		this.userService = userService;

		changeObservers = Lists.newArrayList();
	}

	@Override
	public void subscribe(ChangeObserver<UserProfile> listener) {
		changeObservers.add(listener);
	}

	@Override
	public void unsubscribe(ChangeObserver<UserProfile> listener) {
		changeObservers.remove(listener);
	}

	@Override
	public void refreshUserConfiguration() {
		userService.getProfile(new ResultHandler<>(
			this::notifyOnRead,
				error -> {
					//TODO("Core setup")
				}
			)
		);
	}

	private void notifyOnRead(UserProfile userProfile) {
		for (ChangeObserver<UserProfile> observer : changeObservers) {
			observer.onRead(userProfile);
		}
	}
}
