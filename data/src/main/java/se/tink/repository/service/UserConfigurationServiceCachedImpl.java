package se.tink.repository.service;

import com.google.common.collect.Lists;
import com.tink.model.user.UserProfile;
import com.tink.service.handler.ResultHandler;
import com.tink.service.user.UserProfileService;
import java.util.List;
import javax.inject.Inject;
import se.tink.repository.ObjectChangeObserver;

//TODO: Core setup - Reevaluate
public class UserConfigurationServiceCachedImpl implements UserConfigurationService {

	private List<ObjectChangeObserver<UserProfile>> changeObservers;
	private UserProfileService userService;

	@Inject
	public UserConfigurationServiceCachedImpl(UserProfileService userService) {
		this.userService = userService;

		changeObservers = Lists.newArrayList();
	}

	@Override
	public void subscribe(ObjectChangeObserver<UserProfile> listener) {
		changeObservers.add(listener);
	}

	@Override
	public void unsubscribe(ObjectChangeObserver<UserProfile> listener) {
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
		for (ObjectChangeObserver<UserProfile> observer : changeObservers) {
			observer.onRead(userProfile);
		}
	}
}
