package se.tink.repository.service;

import com.google.common.collect.Lists;
import java.util.List;
import javax.inject.Inject;
import se.tink.converter.ModelConverter;
import se.tink.core.models.user.UserConfiguration;
import se.tink.repository.ObjectChangeObserver;

public class UserConfigurationServiceImpl implements UserConfigurationService {

	private StreamingService streamingService;
	private ModelConverter converter;
	private List<ObjectChangeObserver<UserConfiguration>> changeObserverers;

	@Inject
	public UserConfigurationServiceImpl(StreamingService streamingService,
		ModelConverter converter) {
		this.streamingService = streamingService;
		this.converter = converter;

		changeObserverers = Lists.newArrayList();

		startSubScribing();
	}

	@Override
	public void subscribe(ObjectChangeObserver<UserConfiguration> listener) {
		changeObserverers.add(listener);
	}

	@Override
	public void unsubscribe(ObjectChangeObserver<UserConfiguration> listener) {
		changeObserverers.remove(listener);
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

}
