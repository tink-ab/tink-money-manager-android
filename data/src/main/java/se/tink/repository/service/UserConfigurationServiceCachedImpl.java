package se.tink.repository.service;

import com.google.common.collect.Lists;
import java.util.List;
import javax.inject.Inject;
import se.tink.core.models.user.GetProfileResponse;
import se.tink.core.models.user.UserConfiguration;
import se.tink.repository.ObjectChangeObserver;
import se.tink.repository.SimpleMutationHandler;
import se.tink.repository.TinkNetworkError;
import se.tink.repository.cache.Cache;

public class UserConfigurationServiceCachedImpl implements UserConfigurationService {

	private StreamingService streamingService;
	private List<ObjectChangeObserver<UserConfiguration>> changeObserverers;
	private Cache<UserConfiguration> cache;
	private UserService userService;

	@Inject
	public UserConfigurationServiceCachedImpl(StreamingService streamingService,
		UserService userService,
		Cache<UserConfiguration> cache) {
		this.streamingService = streamingService;
		this.userService = userService;
		this.cache = cache;

		changeObserverers = Lists.newArrayList();
		subscribeCacheAsStreamingObserver();

		startSubScribing();
	}

	@Override
	public void subscribe(ObjectChangeObserver<UserConfiguration> listener) {
		changeObserverers.add(listener);
		readFromCache(listener);
	}

	@Override
	public void unsubscribe(ObjectChangeObserver<UserConfiguration> listener) {
		changeObserverers.remove(listener);
	}

	@Override
	public void refreshUserConfiguration() {
		userService.getProfile(new SimpleMutationHandler<GetProfileResponse>() {

			@Override
			public void onError(TinkNetworkError error) {
				//TODO:PFMSDK Error handling
			}

			@Override
			public void onNext(GetProfileResponse item) {
				notifyOnRead(UserConfiguration.fromProfile(item.getUserProfile()));
			}

		});
	}

	private void notifyOnRead(UserConfiguration userConfiguration) {
		for (ObjectChangeObserver<UserConfiguration> observer : changeObserverers) {
			observer.onRead(userConfiguration);
		}
	}


	private void startSubScribing() {
		streamingService.subscribeForUserConfiguration(
			new ObjectChangeObserver<UserConfiguration>() {
				@Override
				public void onCreate(UserConfiguration item) {
					for (int i = 0; i < changeObserverers.size(); i++) {
						ObjectChangeObserver<UserConfiguration> changeObserver = changeObserverers
							.get(i);
						changeObserver.onCreate(item);
					}
				}

				@Override
				public void onRead(UserConfiguration item) {
					for (int i = 0; i < changeObserverers.size(); i++) {
						ObjectChangeObserver<UserConfiguration> changeObserver = changeObserverers
							.get(i);
						changeObserver.onRead(item);
					}
				}

				@Override
				public void onUpdate(UserConfiguration item) {
					for (int i = 0; i < changeObserverers.size(); i++) {
						ObjectChangeObserver<UserConfiguration> changeObserver = changeObserverers
							.get(i);
						changeObserver.onUpdate(item);
					}
				}

				@Override
				public void onDelete(UserConfiguration item) {
					for (int i = 0; i < changeObserverers.size(); i++) {
						ObjectChangeObserver<UserConfiguration> changeObserver = changeObserverers
							.get(i);
						changeObserver.onDelete(item);
					}
				}
			});
	}

	private void readFromCache(final ObjectChangeObserver<UserConfiguration> listener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				UserConfiguration userConfiguration = cache.read();
				if (userConfiguration != null && userConfiguration.getI18nConfiguration() != null) {
					listener.onRead(userConfiguration);
				}
			}
		}).start();
	}

	private void subscribeCacheAsStreamingObserver() {
		changeObserverers.add(new ObjectChangeObserver<UserConfiguration>() {
			@Override
			public void onCreate(UserConfiguration item) {
				cache.insert(item);
			}

			@Override
			public void onRead(UserConfiguration item) {
				cache.clearAndInsert(item);
			}

			@Override
			public void onUpdate(UserConfiguration item) {
				cache.update(item);
			}

			@Override
			public void onDelete(UserConfiguration item) {
				cache.delete(item);
			}
		});
	}

}
