package com.tink.pfmsdk.analytics;

import android.app.Activity;
import androidx.annotation.NonNull;
import java.util.Map;

public interface Tracker {

	void track(AnalyticsEvent event);

	void track(AnalyticsScreen screen);
}
