package com.tink.pfmsdk;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.tink.pfmsdk.buildConfig.BuildConfiguration;
import com.tink.pfmsdk.buildConfig.BuildConfigurations;
import com.tink.pfmsdk.buildConfig.Feature;
import com.tink.pfmsdk.buildConfig.LoggingConfigurations;
import com.tink.pfmsdk.configuration.I18nConfiguration;
import com.tink.pfmsdk.security.DefaultRecoveryHandler;
import com.tink.pfmsdk.security.SecuredClientDataStorage;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import io.fabric.sdk.android.Fabric;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import javax.crypto.NoSuchPaddingException;
import javax.inject.Inject;
import se.tink.repository.cache.CacheModule;
import timber.log.Timber;


public class TinkApp extends Application implements HasAndroidInjector {
	private static TinkApp sharedInstance;

	@Inject
	DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

	@Inject
	I18nConfiguration i18nConfiguration;


	/*
		Injects all singleton services that has a cached implementation. This needs to be done before the streaming
		starts because a side effect of the initialization of each "cached service" is to setup the
		cache as a streaming listener. And if we get events before this happens the data will be lost
	 */
	@Inject
	ServiceCacheInitialization serviceCacheInitialization;

	@Override
	public void onCreate() {
		super.onCreate();

		sharedInstance = this;

		startFabricIfDesired();

		Fresco.initialize(this);

		// Init SecuredPreferenceStore, used for storing sensitive data
		try {
			SecuredClientDataStorage.init(getApplicationContext(), new DefaultRecoveryHandler());
		} catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException |
			UnrecoverableEntryException | InvalidAlgorithmParameterException | NoSuchPaddingException |
			InvalidKeyException | NoSuchProviderException e) {
			e.printStackTrace();
		}

		BuildConfiguration config = BuildConfigurations.INSTANCE.getInstance();
		// TODO: PFMSDK: Dagger setup
		/*AppComponent appComponent = DaggerAppComponent.builder()
			.cacheModule(new CacheModule(this, config.getFeatureConfigurations().hasFeature(Feature.DISK_CACHE)))
			.applicationModule(new ApplicationModule(this, this))
			.build();
		appComponent.inject(this);*/

		Timber.plant(config.getLoggingConfigurations().getTimberTree());

		i18nConfiguration.initialize();
	}

	private void startFabricIfDesired() {
		LoggingConfigurations loggingConfigurations = BuildConfigurations.INSTANCE.getInstance()
			.getLoggingConfigurations();

		if (loggingConfigurations.shouldStartFabric()) {
			Fabric.with(this, new Crashlytics());
		}
	}

	@Deprecated
	public static TinkApp sharedInstance() {
		return sharedInstance;
	}

	@Override
	public AndroidInjector<Object> androidInjector() {
		return dispatchingAndroidInjector;
	}
}
