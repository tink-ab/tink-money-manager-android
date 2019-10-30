package se.tink.core.models.origin;

import org.joda.time.DateTime;

public interface SetOriginRequest {

	String getServiceName();

	String getExternalServiceId();

	String getDeviceId();

	boolean isOrganic();

	String getMediaSource();

	String getCampaign();

	String getAgency();

	DateTime getClickTime();

	DateTime getInstallTime();

	FacebookOrigin getFacebookOrigin();

	AppsFlyerOrigin getAppsFlyerOrigin();

	interface FacebookOrigin {

		String getCampaignId();

		String getAdGroupId();

		String getAdGroupName();

		String getAdSetId();

		String getAdSetName();

		String getAdId();
	}

	interface AppsFlyerOrigin {

		String getExtraParam(int index);
	}
}
