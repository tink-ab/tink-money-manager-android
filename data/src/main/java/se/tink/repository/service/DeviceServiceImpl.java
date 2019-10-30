package se.tink.repository.service;

import androidx.annotation.Nullable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.inject.Inject;
import org.joda.time.DateTime;
import se.tink.converter.ModelConverter;
import se.tink.core.models.device.DeviceConfigurationResponse;
import se.tink.core.models.origin.SetOriginRequest;
import se.tink.core.models.origin.SetOriginRequest.AppsFlyerOrigin;
import se.tink.core.models.origin.SetOriginRequest.FacebookOrigin;
import se.tink.grpc.v1.models.DeviceAppsFlyerOrigin;
import se.tink.grpc.v1.models.DeviceFacebookOrigin;
import se.tink.grpc.v1.rpc.GetDeviceConfigurationRequest;
import se.tink.grpc.v1.rpc.GetDeviceConfigurationRequest.Builder;
import se.tink.grpc.v1.rpc.RegisterPushNotificationTokenResponse;
import se.tink.grpc.v1.rpc.SetOriginResponse;
import se.tink.grpc.v1.services.DeviceServiceGrpc.DeviceServiceStub;
import se.tink.repository.MutationHandler;
import se.tink.repository.SimpleStreamObserver;

public class DeviceServiceImpl implements DeviceService {

	private static final int RETRY_LIMIT = 3;

	private final DeviceServiceStub service;
	private ModelConverter converter;
	private Executor threadPoolExecutor = Executors.newSingleThreadExecutor();

	@Inject
	DeviceServiceImpl(DeviceServiceStub deviceServiceStub, ModelConverter converter) {
		this.service = deviceServiceStub;
		this.converter = converter;
	}

	@Override
	public void registerPushNotificationToken(String deviceId, String token,
		String notificationPublicKey,
		final MutationHandler<se.tink.core.models.device.pushnotifications.RegisterPushNotificationTokenResponse> handler) {

		se.tink.grpc.v1.rpc.RegisterPushNotificationTokenRequest.Builder request = se.tink.grpc.v1.rpc.RegisterPushNotificationTokenRequest
			.newBuilder();

		request.setDeviceId(deviceId);
		request.setNotificationToken(token);
		request.setNotificationPublicKey(notificationPublicKey);

		service.registerPushNotificationToken(request.build(),
			new SimpleStreamObserver<RegisterPushNotificationTokenResponse>(handler) {
				@Override
				public void onNext(RegisterPushNotificationTokenResponse value) {
					handler.onNext(converter.map(value,
						se.tink.core.models.device.pushnotifications.RegisterPushNotificationTokenResponse.class));
				}
			});
	}

	@Override
	public void getDeviceConfiguration(String deviceId, @Nullable String marketCode,
		final MutationHandler<DeviceConfigurationResponse> handler) {
		Builder request = GetDeviceConfigurationRequest.newBuilder();
		request.setDeviceId(deviceId);
		if(marketCode != null) {
			request.setMarketCode(marketCode);
		}

		service.getDeviceConfiguration(
			request.build(),
			new SimpleStreamObserver<se.tink.grpc.v1.rpc.DeviceConfigurationResponse>(handler) {
				@Override
				public void onNext(se.tink.grpc.v1.rpc.DeviceConfigurationResponse value) {
					handler.onNext(converter.map(value, DeviceConfigurationResponse.class));
				}
			});
	}

	@Override
	public void setOrigin(SetOriginRequest setOriginRequest, final MutationHandler<Void> handler) {
		setOrigin(setOriginRequest, handler, 0);
	}

	private void setOrigin(final SetOriginRequest setOriginRequest,
		final MutationHandler<Void> handler, final int retries) {
		if (retries < RETRY_LIMIT) {
			se.tink.grpc.v1.rpc.SetOriginRequest request = mapOriginRequest(setOriginRequest);
			service.setOrigin(
				request,
				new SimpleStreamObserver<SetOriginResponse>(handler) {
					@Override
					public void onNext(SetOriginResponse value) {
						handler.onNext(null);
					}

					@Override
					public void onError(Throwable t) {
						threadPoolExecutor.execute(new Runnable() {
							@Override
							public void run() {
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								setOrigin(setOriginRequest, handler, retries + 1);
							}
						});
					}
				}
			);
		}
	}

	private se.tink.grpc.v1.rpc.SetOriginRequest mapOriginRequest(SetOriginRequest source) {
		// Do not move this to a model mapper since the base model is an interface. Interfaces and
		// abstract classes are not handled well with model mapper.
		return se.tink.grpc.v1.rpc.SetOriginRequest.newBuilder()
			.setDeviceId(source.getDeviceId())
			.setOrganic(source.isOrganic())
			.setServiceName(source.getServiceName())
			.setExternalServiceId(source.getExternalServiceId())
			.setMediaSource(source.getMediaSource())
			.setCampaign(source.getCampaign())
			.setAgency(source.getAgency())
			.setClickTime(getTimestamp(source.getClickTime()))
			.setInstallTime(getTimestamp(source.getInstallTime()))
			.setFacebook(mapFacebookOrigin(source.getFacebookOrigin()))
			.setAppsFlyer(mapAppsFlyerOrigin(source.getAppsFlyerOrigin()))
			.build();
	}

	private int getTimestamp(DateTime source) {
		if (source == null) {
			return -1;
		} else {
			return (int) source.getMillis() / 1000;
		}
	}

	private DeviceFacebookOrigin mapFacebookOrigin(FacebookOrigin source) {
		return DeviceFacebookOrigin.newBuilder()
			.setCampaignId(source.getCampaignId())
			.setAdGroupId(source.getAdGroupId())
			.setAdGroupName(source.getAdGroupName())
			.setAdSetId(source.getAdSetId())
			.setAdSetName(source.getAdSetName())
			.setAdId(source.getAdId())
			.build();
	}

	private DeviceAppsFlyerOrigin mapAppsFlyerOrigin(AppsFlyerOrigin source) {
		return DeviceAppsFlyerOrigin.newBuilder()
			.setExtraParam1(source.getExtraParam(1))
			.setExtraParam2(source.getExtraParam(2))
			.setExtraParam3(source.getExtraParam(3))
			.setExtraParam4(source.getExtraParam(4))
			.setExtraParam5(source.getExtraParam(5))
			.build();
	}
}
