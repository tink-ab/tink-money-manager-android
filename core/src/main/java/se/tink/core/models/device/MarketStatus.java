package se.tink.core.models.device;

public enum MarketStatus {

	MARKET_STATUS_UNKNOWN(0),
	MARKET_STATUS_BETA(1),
	MARKET_STATUS_DISABLED(2),
	MARKET_STATUS_ENABLED(3);

	private int key;

	MarketStatus(int key) {
		this.key = key;
	}

	public static MarketStatus getMarketStatusByKey(int key) {
		for (MarketStatus result : MarketStatus.values()) {
			if (result.getKey() == key) {
				return result;
			}
		}
		return null;
	}

	public int getKey() {
		return key;
	}

}
