package com.tink.pfmui.collections;

import androidx.annotation.NonNull;
import com.tink.model.user.UserProfile;
import se.tink.privacy.Clearable;
import se.tink.privacy.DataWipeManager;
import se.tink.repository.ObjectChangeObserver;
import se.tink.repository.service.UserConfigurationService;

@Deprecated
public class Currencies implements ObjectChangeObserver<UserProfile>, Clearable {

	private static Currencies instance;
	private static final String DEFAULT_CURRENCY_CODE = "EUR";

	private UserProfile userProfile;

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

	public UserProfile getUserProfile() {
		return userProfile;
	}

	@NonNull
	public String getDefaultCurrencyCode() {
		if (userProfile == null) {
			return DEFAULT_CURRENCY_CODE;
		}
		return userProfile.getCurrency();
	}

	@Override
	public void onCreate(UserProfile item) {
		userProfile = item;
	}

	@Override
	public void onRead(UserProfile item) {
		userProfile = item;
	}

	@Override
	public void onUpdate(UserProfile item) {
		userProfile = item;
	}

	@Override
	public void onDelete(UserProfile item) {
		userProfile = item;
	}

	@Override
	public void clear() {
		userProfile = null;
	}
}