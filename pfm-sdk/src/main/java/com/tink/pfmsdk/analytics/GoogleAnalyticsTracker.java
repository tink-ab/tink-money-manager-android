package com.tink.pfmsdk.analytics;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.tink.pfmsdk.BuildConfig;

public class GoogleAnalyticsTracker extends SimpleTracker {

	private static final String GA_VERSION_NAME = BuildConfig.VERSION_NAME + "-Android";

	private com.google.android.gms.analytics.Tracker gaTracker;

	public GoogleAnalyticsTracker(Context context, String googleAnalyticsKey) {
		GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(context);
		gaTracker = googleAnalytics.newTracker(googleAnalyticsKey);
		gaTracker.setAppVersion(GA_VERSION_NAME);
	}

	@Override
	public void track(AnalyticsEvent event) {
		gaTracker.send(new HitBuilders.EventBuilder()
			.setAction(event.getAction())
			.setCategory(event.getCategory())
			.setLabel(event.getLabel())
			.build());
	}

	@Override
	public void track(AnalyticsScreen screen, @NonNull Activity activity) {
		track(screen.getName());
	}

	@Override
	public void track(String screen) {
		gaTracker.setScreenName(screen);
		gaTracker.send(new HitBuilders.ScreenViewBuilder().build());
	}

}
