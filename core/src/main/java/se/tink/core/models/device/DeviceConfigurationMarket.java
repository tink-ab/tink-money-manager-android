package se.tink.core.models.device;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DeviceConfigurationMarket implements Serializable {

	private String market;
	private String label;
	private String termsAndConditionsLink;
	private boolean suggested;
	private List<AuthenticationMethod> loginMethods;
	private List<AuthenticationMethod> registerMethods;
	private MarketStatus status;

	DeviceConfigurationMarket() {

	}

	private DeviceConfigurationMarket(String market, String label,
		String termsAndConditionsLink, boolean suggested,
		List<AuthenticationMethod> loginMethods,
		List<AuthenticationMethod> registerMethods,
		MarketStatus status) {
		this.market = market;
		this.label = label;
		this.termsAndConditionsLink = termsAndConditionsLink;
		this.suggested = suggested;
		this.loginMethods = loginMethods;
		this.registerMethods = registerMethods;
		this.status = status;
	}

	public MarketStatus getStatus() {
		return status;
	}

	public String getMarket() {
		return market;
	}

	public String getLabel() {
		return label;
	}

	public boolean isSuggested() {
		return suggested;
	}

	public String getTermsAndConditionsLink() {
		return termsAndConditionsLink;
	}

	public List<AuthenticationMethod> getLoginMethods() {
		return new ArrayList<>(loginMethods);
	}

	public List<AuthenticationMethod> getRegisterMethods() {
		return new ArrayList<>(registerMethods);
	}

	public static class Builder {

		private String market;
		private String label;
		private String termsAndConditionsLink;
		private boolean suggested;
		private List<AuthenticationMethod> loginMethods;
		private List<AuthenticationMethod> registerMethods;
		private MarketStatus status;

		public Builder setStatus(MarketStatus status) {
			this.status = status;
			return this;
		}

		public Builder setMarket(String market) {
			this.market = market;
			return this;
		}

		public Builder setLabel(String label) {
			this.label = label;
			return this;
		}

		public Builder setTermsAndConditionsLink(String termsAndConditionsLink) {
			this.termsAndConditionsLink = termsAndConditionsLink;
			return this;
		}

		public Builder setSuggested(boolean suggested) {
			this.suggested = suggested;
			return this;
		}

		public Builder setLoginMethods(List<AuthenticationMethod> loginMethods) {
			this.loginMethods = loginMethods;
			return this;
		}

		public Builder setRegisterMethods(List<AuthenticationMethod> registerMethods) {
			this.registerMethods = registerMethods;
			return this;
		}

		public DeviceConfigurationMarket createDeviceConfigurationMarket() {
			return new DeviceConfigurationMarket(
				market,
				label,
				termsAndConditionsLink,
				suggested,
				loginMethods,
				registerMethods,
				status);
		}
	}
}
