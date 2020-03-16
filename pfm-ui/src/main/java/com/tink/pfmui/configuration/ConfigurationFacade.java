package com.tink.pfmui.configuration;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.Nullable;
import com.google.common.base.Optional;
import com.tink.annotations.PfmScope;
import com.tink.pfmui.ClientDataStorage;
import com.tink.pfmui.R;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import se.tink.android.di.application.ApplicationScoped;
import se.tink.core.models.device.AuthenticationMethod;
import se.tink.core.models.device.DeviceConfigurationMarket;
import se.tink.core.models.device.DeviceConfigurationResponse;
import se.tink.core.models.origin.SetOriginRequest;
import se.tink.repository.SimpleMutationHandler;
import se.tink.repository.TinkNetworkError;
import se.tink.repository.service.DeviceService;
import timber.log.Timber;

@PfmScope
class ConfigurationFacade {
	private final Context context;
	private final ConfigurationCache cache;
	private final List<ConfigurationReceivedListener> listeners = new LinkedList<>();

	private final BehaviorSubject<SetOriginRequest> setOriginRequestSubject = BehaviorSubject
		.create();

	private final Observable<DeviceConfigurationResponse> configurationObservable;

	@Inject
	ConfigurationFacade(@ApplicationScoped final Context context, final ConfigurationCache cache,
		final DeviceService deviceService,
		final MarketProvider marketProvider
	) {

		this.context = context;
		this.cache = cache;


		configurationObservable = setOriginRequestSubject
			.subscribeOn(Schedulers.io())
			.map(Optional::of)
			// TODO: PFMDK: Do we need this?
			//.ambWith(new GetOriginFromStore(context, appsFlyerStore).toObservable())
			.ambWith(Observable.just(Optional.<SetOriginRequest>absent()).delay(1, TimeUnit.SECONDS))
			.flatMapSingle(
				(Function<Optional<SetOriginRequest>, SingleSource<Optional<SetOriginRequest>>>) request -> {
					// TODO: PFMDK: Do we need this?
					//if (request.isPresent() && !appsFlyerStore.hasSetData()) {
					if (request.isPresent()) {
						return new SetOriginCompletable(deviceService, request.get())
							.toSingleDefault(request);
					} else {
						return Single.just(Optional.<SetOriginRequest>absent());
					}
				})
			.flatMap(
				(Function<Optional<SetOriginRequest>, ObservableSource<DeviceConfigurationResponse>>) request ->
					new GetConfigurationObservable(context, deviceService, marketProvider)
					.doOnNext(cache::storeConfiguration))
			.ambWith(new GetConfigurationFromCacheOrDefault(context, cache, marketProvider)
				.toObservable()
				.delay(5, TimeUnit.SECONDS))
			.replay(1)
			.autoConnect();
	}

	// TODO: PFMDK: Do we need this?
//	public void setConversionData(Map<String, String> conversionData) {
//		appsFlyerStore.storeConversionData(conversionData);
//		setOriginRequestSubject
//			.onNext(new SetOriginRequestAppsFlyerMapAdapter(context, conversionData));
//	}

	public void requestConfiguration(final ConfigurationReceivedListener callback) {
		addConfigurationReceivedListener(new ConfigurationReceivedListener() {
			@Override
			public void onConfigurationReceived(
				@Nullable DeviceConfigurationResponse deviceConfigurationResponse) {
				callback.onConfigurationReceived(deviceConfigurationResponse);
				synchronized (listeners) {
					listeners.remove(this);
				}
			}
		});
		requestConfiguration();
	}

	@SuppressLint("CheckResult")
	@SuppressWarnings("WeakerAccess")
	public void requestConfiguration() {
		configurationObservable
			.subscribe(
				this::notifyListeners,
				throwable -> notifyListeners(null)
			);
	}

	private void notifyListeners(@Nullable DeviceConfigurationResponse response) {
		synchronized (listeners) {
			for (int i = listeners.size() - 1; i >= 0; i--) {
				listeners.get(i).onConfigurationReceived(response);
			}
		}
	}

	@SuppressWarnings("WeakerAccess")
	public void addConfigurationReceivedListener(ConfigurationReceivedListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);
			listeners.add(listener);
		}
	}

	public interface ConfigurationReceivedListener {

		void onConfigurationReceived(
			@Nullable DeviceConfigurationResponse deviceConfigurationResponse);
	}

	private static class SetOriginCompletable extends Completable {

		private final DeviceService service;
		private final SetOriginRequest request;

		SetOriginCompletable(DeviceService service, SetOriginRequest request) {

			this.service = service;
			this.request = request;
		}

		@Override
		protected void subscribeActual(final CompletableObserver s) {
			service.setOrigin(request, new SimpleMutationHandler<Void>() {
				@Override
				public void onNext(Void item) {
					s.onComplete();
				}

				@Override
				public void onError(TinkNetworkError error) {
					// This error can safely be ignored since the timeout will trigger.
					Timber.e(error, "Error setting origin");
				}
			});
		}
	}

	private static class GetConfigurationObservable extends
		Observable<DeviceConfigurationResponse> {

		private final Context context;
		private final DeviceService deviceService;
		private final MarketProvider marketProvider;

		GetConfigurationObservable(Context context, DeviceService deviceService, MarketProvider marketProvider) {
			this.context = context;
			this.marketProvider = marketProvider;
			this.deviceService = deviceService;
		}

		@Override
		protected void subscribeActual(
			final Observer<? super DeviceConfigurationResponse> observer) {
			deviceService.getDeviceConfiguration(
				ClientDataStorage.sharedInstance(context).getUUID(),
				marketProvider.getMarket(),
				new SimpleMutationHandler<DeviceConfigurationResponse>() {
					@Override
					public void onNext(DeviceConfigurationResponse item) {
						observer.onNext(item);
						observer.onComplete();
					}

					@Override
					public void onError(TinkNetworkError error) {
						// This error can safely be ignored since the timeout will trigger.
						Timber.e(error, "Error getting configuration");
					}
				});
		}
	}

	// TODO: PFMDK: Do we need this?
//	private static class GetOriginFromStore extends Single<Optional<SetOriginRequest>> {
//
//		private final Context context;
//
//		GetOriginFromStore(Context context) {
//			this.context = context;
//		}
//
//		@Override
//		protected void subscribeActual(
//			SingleObserver<? super Optional<SetOriginRequest>> observer) {
//			Map<String, String> conversionData = store.getConversionData();
//			if (conversionData != null) {
//				observer.onSubscribe(new SimpleDisposable());
//				observer.onSuccess(Optional.of(new SetOriginRequestAppsFlyerMapAdapter(context, conversionData)));
//			}
//		}
//	}

	private static class GetConfigurationFromCacheOrDefault extends
		Single<DeviceConfigurationResponse> {

		private final Context context;
		private final ConfigurationCache configurationCache;
		private static final Map<String, String> countryCodeDisplayNameMap;
		private final MarketProvider marketProvider;

		static {
			countryCodeDisplayNameMap = new HashMap<>();
			for (Locale locale : Locale.getAvailableLocales()) {
				countryCodeDisplayNameMap.put(locale.getCountry(), locale.getDisplayCountry());
			}
		}

		GetConfigurationFromCacheOrDefault(Context context, ConfigurationCache configurationCache, MarketProvider marketProvider) {
			this.context = context;
			this.marketProvider = marketProvider;
			this.configurationCache = configurationCache;
		}


		@Override
		protected void subscribeActual(
			SingleObserver<? super DeviceConfigurationResponse> observer) {
			DeviceConfigurationResponse deviceConfigurationResponse = configurationCache
				.fetchConfiguration();

			observer.onSubscribe(new SimpleDisposable());
			if (deviceConfigurationResponse != null) {
				observer.onSuccess(deviceConfigurationResponse);
			} else {
				observer.onSuccess(getDefault());
			}
		}

		private DeviceConfigurationResponse getDefault() {
			return getDefault(context);
		}

		private DeviceConfigurationResponse getDefault(Context context) {
			String defaultMarketCode = marketProvider.getMarket();

			DeviceConfigurationMarket defaultMarket = new DeviceConfigurationMarket.Builder()
				.setMarket(defaultMarketCode)
				.setLabel(countryCodeDisplayNameMap.get(defaultMarketCode))
				.setLoginMethods(Arrays.asList(AuthenticationMethod.values()))
				.setRegisterMethods(Arrays.asList(AuthenticationMethod.values()))
				.setSuggested(true)
				.setTermsAndConditionsLink(context.getString(R.string.tink_onboarding_new_user_terms_link))
				.createDeviceConfigurationMarket();

			DeviceConfigurationResponse defaultResponse = new DeviceConfigurationResponse();
			defaultResponse.setMarkets(Collections.singletonList(defaultMarket));
			defaultResponse.setFeatureFlags(Collections.<String>emptyList());

			return defaultResponse;
		}
	}

	private static final class SimpleDisposable implements Disposable {

		private boolean disposed = false;

		@Override
		public void dispose() {
			disposed = true;
		}

		@Override
		public boolean isDisposed() {
			return disposed;
		}
	}

	// TODO: PFMDK: Do we need this?
	/*public static class SetOriginRequestAppsFlyerMapAdapter implements SetOriginRequest {

		private final Context context;
		private final ClientDataStorage clientDataStorage;
		private final Map<String, String> conversionData;
		private final FacebookOrigin facebookOrigin;
		private final AppsFlyerOrigin appsFlyerOrigin;

		public SetOriginRequestAppsFlyerMapAdapter(Context context, Map<String, String> conversionData) {
			this.context = context;
			clientDataStorage = ClientDataStorage.sharedInstance(context);
			this.conversionData = conversionData;

			facebookOrigin = new FacebookOriginAdapter();
			appsFlyerOrigin = new AppsFlyerOriginAdapter();
		}

		@Override
		public String getServiceName() {
			return "appsflyer";
		}

		@Override
		public String getExternalServiceId() {
			return AppsFlyerLib.getInstance().getAppsFlyerUID(context);
		}

		@Override
		public String getDeviceId() {
			return clientDataStorage.getUUID();
		}

		@Override
		public boolean isOrganic() {
			return Objects.equals(conversionData.get("af_status"), "Organic");
		}

		@Override
		public String getMediaSource() {
			return MoreObjects.firstNonNull(conversionData.get("media_source"), "");
		}

		@Override
		public String getCampaign() {
			return MoreObjects.firstNonNull(conversionData.get("campaign"), "");
		}

		@Override
		public String getAgency() {
			return MoreObjects.firstNonNull(conversionData.get("agency"), "");
		}

		@Override
		public DateTime getClickTime() {
			return retrieveDate("click_time");
		}

		@Nullable
		private DateTime retrieveDate(String click_time) {
			String dateString = conversionData.get(click_time);
			if (dateString == null) {
				return null;
			}

			DateTime dateTime = null;
			try {
				dateTime = DateTime.parse(dateString);
			} catch (IllegalArgumentException e) {
				Timber.w(e, "retrieveDate: Malformed date string.");
			}
			return dateTime;
		}

		@Override
		public DateTime getInstallTime() {
			return retrieveDate("install_time");
		}

		@Override
		public FacebookOrigin getFacebookOrigin() {
			return facebookOrigin;
		}

		@Override
		public AppsFlyerOrigin getAppsFlyerOrigin() {
			return appsFlyerOrigin;
		}

		private final class AppsFlyerOriginAdapter implements AppsFlyerOrigin {

			@Override
			public String getExtraParam(int index) {
				return MoreObjects.firstNonNull(
					conversionData.get(String.format(Locale.US, "af_sub%d", index)), "");
			}
		}

		private final class FacebookOriginAdapter implements FacebookOrigin {

			@Override
			public String getCampaignId() {
				return MoreObjects.firstNonNull(conversionData.get("campaign_id"), "");
			}

			@Override
			public String getAdGroupId() {
				return MoreObjects.firstNonNull(conversionData.get("adgroup_id"), "");
			}

			@Override
			public String getAdGroupName() {
				return MoreObjects.firstNonNull(conversionData.get("adgroup"), "");
			}

			@Override
			public String getAdSetId() {
				return MoreObjects.firstNonNull(conversionData.get("adset_id"), "");
			}

			@Override
			public String getAdSetName() {
				return MoreObjects.firstNonNull(conversionData.get("adset"), "");
			}

			@Override
			public String getAdId() {
				return MoreObjects.firstNonNull(conversionData.get("ad_id"), "");
			}
		}
	}*/
}
