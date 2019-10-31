package com.tink.pfmsdk.analytics;

import android.app.Activity;
import androidx.annotation.NonNull;
import java.util.Map;
import java.util.Set;

public class Analytics implements Tracker {

	private Set<Tracker> trackers;

	public Analytics(Set<Tracker> trackers) {
		this.trackers = trackers;
	}

	@Override
	public void registerUser(String identifier, String username) {
		for (Tracker tracker : trackers) {
			tracker.registerUser(identifier, username);
		}
	}

	@Override
	public void track(AnalyticsEvent event) {
		for (Tracker tracker : trackers) {
			tracker.track(event);
		}
	}

	@Override
	public void track(AnalyticsEvent event, Map<TrackerParameter, String> parameters) {
		for (Tracker tracker : trackers) {
			tracker.track(event, parameters);
		}
	}

	@Override
	public void track(AnalyticsScreen screen, @NonNull Activity activity) {
		for (Tracker tracker : trackers) {
			tracker.track(screen, activity);
		}
	}

	@Override
	public void track(AnalyticsScreen screen, Map<TrackerParameter, String> parameters,
		@NonNull Activity activity) {
		for (Tracker tracker : trackers) {
			tracker.track(screen, parameters, activity);
		}
	}


	@Override
	public void track(String screen) {
		for (Tracker tracker : trackers) {
			tracker.track(screen);
		}
	}
}
