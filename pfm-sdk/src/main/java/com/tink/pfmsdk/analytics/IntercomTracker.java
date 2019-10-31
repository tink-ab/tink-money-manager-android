package com.tink.pfmsdk.analytics;

import android.app.Application;
import io.intercom.android.sdk.Intercom;
import io.intercom.android.sdk.identity.Registration;

public class IntercomTracker extends SimpleTracker {

	public IntercomTracker(Application application, String appId, String apiKey) {
		Intercom.initialize(application, apiKey, appId);
	}

	@Override
	public void registerUser(String identifier, String username) {
		Registration registration = Registration.create()
			.withUserId(identifier)
			.withEmail(username);
		Intercom.client().registerIdentifiedUser(registration);
	}

}
