package se.tink.repository.service;

import se.tink.converter.ModelConverter;
import se.tink.core.models.device.UpdateI18NSettingsRequest;
import se.tink.core.models.device.UpdateI18NSettingsResponse;
import se.tink.core.models.user.GetProfileResponse;
import se.tink.core.models.user.NotificationSettings;
import se.tink.core.models.user.PeriodSetting;
import se.tink.core.models.user.UserProfile;
import se.tink.grpc.v1.rpc.GetProfileRequest;
import se.tink.grpc.v1.rpc.NotificationSettingsRequest;
import se.tink.grpc.v1.rpc.NotificationSettingsResponse;
import se.tink.grpc.v1.rpc.PeriodSettingsRequest;
import se.tink.grpc.v1.rpc.PeriodSettingsResponse;
import se.tink.grpc.v1.rpc.UpdateNotificationSettingsRequest;
import se.tink.grpc.v1.rpc.UpdatePeriodSettingsRequest;
import se.tink.grpc.v1.services.SettingsServiceGrpc;
import se.tink.grpc.v1.services.UserServiceGrpc;
import se.tink.repository.MutationHandler;
import se.tink.repository.SimpleStreamObserver;

public class SettingsServiceImpl implements SettingsService {

	private final ModelConverter converter;
	private final SettingsServiceGrpc.SettingsServiceStub settingsServiceApi;
	private final UserServiceGrpc.UserServiceStub userServiceApi;

	public SettingsServiceImpl(
		SettingsServiceGrpc.SettingsServiceStub settingsServiceApi,
		UserServiceGrpc.UserServiceStub userServiceApi,
		ModelConverter converter) {
		this.settingsServiceApi = settingsServiceApi;
		this.converter = converter;
		this.userServiceApi = userServiceApi;
	}

	@Override
	public void getNotificationSettings(
		final MutationHandler<NotificationSettings> mutationHandler) {
		settingsServiceApi.getNotificationSettings(NotificationSettingsRequest.getDefaultInstance(),
			new SimpleStreamObserver<NotificationSettingsResponse>(mutationHandler) {
				@Override
				public void onNext(NotificationSettingsResponse value) {
					mutationHandler.onNext(
						converter.map(value.getNotificationSettings(), NotificationSettings.class));
				}
			});
	}

	@Override
	public void updateNotificationSettings(NotificationSettings notificationSettings,
		final MutationHandler<NotificationSettings> mutationHandler) {
		settingsServiceApi.updateNotificationSettings(converter.map(notificationSettings,
			UpdateNotificationSettingsRequest.class),
			new SimpleStreamObserver<NotificationSettingsResponse>(mutationHandler) {
				@Override
				public void onNext(NotificationSettingsResponse value) {
					mutationHandler.onNext(
						converter.map(value.getNotificationSettings(), NotificationSettings.class));
				}
			});
	}

	@Override
	public void getPeriodSettings(final MutationHandler<PeriodSetting> mutationHandler) {
		settingsServiceApi.getPeriodSettings(PeriodSettingsRequest.getDefaultInstance(),
			new SimpleStreamObserver<PeriodSettingsResponse>(mutationHandler) {
				@Override
				public void onNext(PeriodSettingsResponse value) {
					mutationHandler
						.onNext(converter.map(value.getPeriodSettings(), PeriodSetting.class));
				}
			});
	}

	@Override
	public void updatePeriodSettings(PeriodSetting periodSettings,
		final MutationHandler<PeriodSetting> mutationHandler) {
		settingsServiceApi.updatePeriodSettings(converter.map(periodSettings,
			UpdatePeriodSettingsRequest.class),
			new SimpleStreamObserver<PeriodSettingsResponse>(mutationHandler) {
				@Override
				public void onNext(PeriodSettingsResponse value) {
					mutationHandler.onNext(converter.map(
						value.getPeriodSettings(), PeriodSetting.class));
				}
			});
	}

	@Override
	public void updateI18NSettings(UpdateI18NSettingsRequest request,
		MutationHandler<se.tink.core.models.device.UpdateI18NSettingsResponse> mutationHandler) {
		settingsServiceApi.updateI18NSettings(
			converter.map(request, se.tink.grpc.v1.rpc.UpdateI18NSettingsRequest.class),
			new SimpleStreamObserver<se.tink.grpc.v1.rpc.UpdateI18NSettingsResponse>(
				mutationHandler) {
				@Override
				public void onNext(se.tink.grpc.v1.rpc.UpdateI18NSettingsResponse value) {
					mutationHandler.onNext(converter.map(value, UpdateI18NSettingsResponse.class));
				}
			}
		);
	}

	@Override
	public void getUserProfile(MutationHandler<UserProfile> mutationHandler) {
		userServiceApi.getProfile(
			GetProfileRequest.getDefaultInstance(),
			new SimpleStreamObserver<se.tink.grpc.v1.rpc.GetProfileResponse>(mutationHandler) {
				@Override
				public void onNext(se.tink.grpc.v1.rpc.GetProfileResponse value) {
					//noinspection unchecked
					mutationHandler.onNext(converter.map(value, GetProfileResponse.class).getUserProfile());
				}
			});
	}
}