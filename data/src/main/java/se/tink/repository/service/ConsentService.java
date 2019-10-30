package se.tink.repository.service;

import se.tink.core.models.consents.AvailableConsentsResponse;
import se.tink.core.models.consents.ConsentDetailsResponse;
import se.tink.core.models.consents.GiveConsentRequest;
import se.tink.core.models.consents.GiveConsentResponse;
import se.tink.core.models.consents.ListConsentsResponse;
import se.tink.core.models.consents.UserConsentDetailsResponse;
import se.tink.core.models.consents.UserConsentsListResponse;
import se.tink.repository.MutationHandler;

public interface ConsentService extends TinkService {

	String TERMS_AND_CONDITIONS = "TERMS_AND_CONDITIONS";
	String PRIVACY_POLICY = "PRIVACY_POLICY";
	String GENERAL_TERMS = "GENERAL_TERMS";

	void listConsents(MutationHandler<ListConsentsResponse> handler);

	void getConsentDetails(String key, MutationHandler<ConsentDetailsResponse> handler);

	void getAvailableConsents(MutationHandler<AvailableConsentsResponse> handler);

	void listUserConsents(MutationHandler<UserConsentsListResponse> handler);

	void giveConsents(GiveConsentRequest consentRequest,
		MutationHandler<GiveConsentResponse> handler);

	void getUserConsentDetails(MutationHandler<UserConsentDetailsResponse> handler);

}
