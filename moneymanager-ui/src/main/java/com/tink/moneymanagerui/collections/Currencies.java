package com.tink.moneymanagerui.collections;

import com.tink.model.user.UserProfile;
import com.tink.service.observer.ChangeObserver;

import androidx.annotation.NonNull;
import se.tink.android.privacy.Clearable;

@Deprecated
public class Currencies implements ChangeObserver<UserProfile>, Clearable {

	private static Currencies instance;
	private static final String DEFAULT_CURRENCY_CODE = "EUR";

	private UserProfile userProfile;

	public static Currencies getSharedInstance() {
		if (instance == null) {
			instance = new Currencies();
		}
		return instance;
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