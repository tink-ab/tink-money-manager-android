package com.tink.pfmsdk.analytics;

import android.app.Activity;
import androidx.annotation.NonNull;
import java.util.Map;

public interface Tracker {

	void track(AnalyticsEvent event);

	void track(AnalyticsEvent event, Map<TrackerParameter, String> parameters);

	void track(AnalyticsScreen screen, @NonNull Activity activity);

	void track(AnalyticsScreen screen, Map<TrackerParameter, String> parameters,
		@NonNull Activity activity);

	void track(String screen);
}
