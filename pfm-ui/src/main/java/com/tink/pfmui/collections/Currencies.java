package com.tink.pfmui.collections;

import androidx.annotation.NonNull;
import se.tink.core.models.user.UserConfiguration;
import se.tink.privacy.Clearable;
import se.tink.privacy.DataWipeManager;
import se.tink.repository.ObjectChangeObserver;
import se.tink.repository.service.UserConfigurationService;

@Deprecated
public class Currencies implements ObjectChangeObserver<UserConfiguration>, Clearable {

	private static Currencies instance;
	private static final String DEFAULT_CURRENCY_CODE = "EUR";

	private UserConfiguration userConfiguration;

	public static Currencies getSharedInstance() {
		if (instance == null) {
			instance = new Currencies();
			DataWipeManager.sharedInstance().register(instance);
		}
		return instance;
	}

	public void attatchListener(UserConfigurationService service) {
		service.subscribe(this);
	}

	public void removeListener(UserConfigurationService service) {
		service.unsubscribe(this);
	}

	public UserConfiguration getUserConfiguration() {
		return userConfiguration;
	}

	@NonNull
	public String getDefaultCurrencyCode() {
		if (userConfiguration == null || userConfiguration.getI18nConfiguration() == null) {
			return DEFAULT_CURRENCY_CODE;
		}
		return userConfiguration.getI18nConfiguration().getCurrencyCode();
	}

	@Override
	public void onCreate(UserConfiguration item) {
		userConfiguration = item;
	}

	@Override
	public void onRead(UserConfiguration item) {
		userConfiguration = item;
	}

	@Override
	public void onUpdate(UserConfiguration item) {
		userConfiguration = item;
	}

	@Override
	public void onDelete(UserConfiguration item) {
		userConfiguration = item;
	}

	@Override
	public void clear() {
		userConfiguration = new UserConfiguration();
	}
}