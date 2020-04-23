package se.tink.repository;

import se.tink.repository.service.SettingsService;
import se.tink.repository.service.StreamingService;
import se.tink.repository.service.StreamingServiceErrorHandler;

public class ServiceCoordinator {


	final StreamingService streamingService;
	private final SettingsService settingsService;

	private String clientKey;

	public ServiceCoordinator(StreamingService service, SettingsService settingsService) {
		streamingService = service;
		this.settingsService = settingsService;
	}

	public void initializeStreaming(StreamingServiceErrorHandler streamingServiceErrorHandler) {
		streamingService.start(streamingServiceErrorHandler);
	}

	public void logout() {
		streamingService.stopStreaming();
	}

	public void updateLocaleAndInitializeStreaming(
		final StreamingServiceErrorHandler streamingServiceErrorHandler, String locale, TinkNetworkErrorHandler errorHandler) {
//		final UpdateI18NSettingsRequest request = new UpdateI18NSettingsRequest();
//		request.setLocaleCode(locale);
//		settingsService.updateI18NSettings(request,
//			ErrorUtils
//				.withErrorHandler(errorHandler, new SimpleMutationHandler<UpdateI18NSettingsResponse>() {
//					@Override
//					public void onError(TinkNetworkError error) {
//						Timber.e(error);
//					}
//
//					@Override
//					public void onNext(UpdateI18NSettingsResponse item) {
//						initializeStreaming(streamingServiceErrorHandler);
//					}
//				}));
	}
}
