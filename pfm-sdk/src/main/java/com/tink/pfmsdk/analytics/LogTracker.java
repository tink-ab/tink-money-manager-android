package com.tink.pfmsdk.analytics;

import android.app.Activity;
import androidx.annotation.NonNull;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

public class LogTracker extends SimpleTracker {

	private static String TAG = "LogTracker";

	@Override
	public void track(AnalyticsEvent event) {
		Timber.tag(TAG).d("Track event - Category: %s, Action: %s, Label: %s",
				event.getCategory(),
				event.getAction(),
				event.getLabel());
	}

	@Override
	public void track(@Nullable AnalyticsEvent event, @Nullable Map<TrackerParameter, String> parameters) {
		Timber.tag(TAG).d("Track event - Category: %s, Action: %s, Label: %s, Parameters: %s",
			event.getCategory(),
			event.getAction(),
			event.getLabel(),
			getParametersText(parameters)
		);
	}

	private String getParametersText(@Nullable Map<TrackerParameter, String> parameters) {
		if (parameters != null && !parameters.isEmpty()) {
			StringBuilder builder = new StringBuilder();
			for (Map.Entry<TrackerParameter, String> entry : parameters.entrySet()) {
				builder
					.append("[")
					.append(entry.getKey().getParameter())
					.append(": ")
					.append(entry.getValue())
					.append("]");
			}
			return builder.toString();
		}
		return "[]";
	}

	@Override
	public void track(AnalyticsScreen screen, @NonNull Activity activity) {
		track(screen.getName());
	}

	@Override
	public void track(String screen) {
		Timber.tag(TAG).d("Track screen - Screen name: %s", screen);
	}

	@Override
	public void track(@Nullable AnalyticsScreen screen,
		@Nullable Map<TrackerParameter, String> parameters, @NotNull Activity activity) {
		Timber.tag(TAG).d("Track screen - Screen name: %s, Parameters: %s",
			screen,
			getParametersText(parameters)
		);
	}
}
