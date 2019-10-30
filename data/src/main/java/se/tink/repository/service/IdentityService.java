package se.tink.repository.service;

import java.util.List;
import se.tink.core.models.identity.IdentityEvent;
import se.tink.core.models.identity.IdentityEventAnswer;
import se.tink.core.models.identity.IdentityEventSummary;
import se.tink.core.models.identity.IdentityState;
import se.tink.repository.MutationHandler;

public interface IdentityService {

	void getIdentityEvent(String id, MutationHandler<IdentityEvent> mutationHandler);

	void getIdentityEventSummaryList(MutationHandler<List<IdentityEventSummary>> mutationHandler);

	void seenIdentityEvents(List<String> seenIds,
		MutationHandler<List<IdentityEventSummary>> mutationHandler);

	void answerIdentityEvent(String eventid, IdentityEventAnswer event,
		MutationHandler<IdentityEvent> mutationHandler);

	void getIdentityState(MutationHandler<IdentityState> mutationHandler);
}
