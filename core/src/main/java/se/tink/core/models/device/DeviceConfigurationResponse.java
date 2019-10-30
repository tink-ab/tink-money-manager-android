package se.tink.core.models.device;

import java.io.Serializable;
import java.util.List;

public class DeviceConfigurationResponse implements Serializable {

	private List<String> featureFlags;
	private List<DeviceConfigurationMarket> markets;

	public List<String> getFeatureFlags() {
		return featureFlags;
	}

	public void setFeatureFlags(List<String> featureFlags) {
		this.featureFlags = featureFlags;
	}

	public List<DeviceConfigurationMarket> getMarkets() {
		return markets;
	}

	public void setMarkets(List<DeviceConfigurationMarket> markets) {
		this.markets = markets;
	}
}
