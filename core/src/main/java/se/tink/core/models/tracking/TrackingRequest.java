package se.tink.core.models.tracking;

import java.util.List;

public class TrackingRequest {

	private List<TrackingEvent> events;
	private List<TrackingTiming> timings;
	private List<TrackingView> views;

	public List<TrackingView> getViews() {
		return views;
	}

	public void setViews(List<TrackingView> views) {
		this.views = views;
	}

	public List<TrackingTiming> getTimings() {
		return timings;
	}

	public void setTimings(List<TrackingTiming> timings) {
		this.timings = timings;
	}

	public List<TrackingEvent> getEvents() {
		return events;
	}

	public void setEvents(List<TrackingEvent> events) {
		this.events = events;
	}
}
