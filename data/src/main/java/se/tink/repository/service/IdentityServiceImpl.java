package se.tink.repository.service;

import java.util.List;
import se.tink.converter.ModelConverter;
import se.tink.core.models.identity.IdentityEvent;
import se.tink.core.models.identity.IdentityEventAnswer;
import se.tink.core.models.identity.IdentityEventSummary;
import se.tink.core.models.identity.IdentityState;
import se.tink.grpc.v1.rpc.AnswerIdentityEventRequest;
import se.tink.grpc.v1.rpc.AnswerIdentityEventResponse;
import se.tink.grpc.v1.rpc.GetIdentityEventRequest;
import se.tink.grpc.v1.rpc.IdentityEventListRequest;
import se.tink.grpc.v1.rpc.IdentityEventListResponse;
import se.tink.grpc.v1.rpc.IdentityEventResponse;
import se.tink.grpc.v1.rpc.IdentityStateRequest;
import se.tink.grpc.v1.rpc.IdentityStateResponse;
import se.tink.grpc.v1.rpc.SeenIdentityEventRequest;
import se.tink.grpc.v1.rpc.SeenIdentityEventResponse;
import se.tink.grpc.v1.services.IdentityServiceGrpc.IdentityServiceStub;
import se.tink.repository.MutationHandler;
import se.tink.repository.SimpleStreamObserver;

public class IdentityServiceImpl implements IdentityService {


	private final IdentityServiceStub service;
	private final ModelConverter modelConverter;

	public IdentityServiceImpl(IdentityServiceStub identityServiceBlockingStub,
		ModelConverter modelConverter) {
		this.service = identityServiceBlockingStub;
		this.modelConverter = modelConverter;
	}

	@Override
	public void getIdentityEvent(String id, MutationHandler<IdentityEvent> mutationHandler) {
		GetIdentityEventRequest.Builder request = GetIdentityEventRequest.newBuilder();
		request.setIdentityEventId(id);
		service.getIdentityEvent(request.build(),
			new SimpleStreamObserver<IdentityEventResponse>(mutationHandler) {
				@Override
				public void onNext(IdentityEventResponse value) {
					IdentityEvent identityEvent = modelConverter.map(value.getEvent(), IdentityEvent.class);
					mutationHandler.onNext(identityEvent);
				}
			});
	}

	@Override
	public void getIdentityEventSummaryList(
		MutationHandler<List<IdentityEventSummary>> mutationHandler) {
		service.getIdentityEventSummaryList(IdentityEventListRequest.getDefaultInstance(),
			new SimpleStreamObserver<IdentityEventListResponse>(mutationHandler) {
				@Override
				public void onNext(IdentityEventListResponse value) {
					mutationHandler.onNext(modelConverter.map(value.getEventsList(), IdentityEventSummary.class));
				}
			});
	}

	@Override
	public void seenIdentityEvents(List<String> seenIds,
		MutationHandler<List<IdentityEventSummary>> mutationHandler) {
		SeenIdentityEventRequest.Builder request = SeenIdentityEventRequest.newBuilder();
		request.addAllIdentityEventIds(seenIds);
		service.seenIdentityEvents(request.build(),
			new SimpleStreamObserver<SeenIdentityEventResponse>(mutationHandler) {
				@Override
				public void onNext(SeenIdentityEventResponse value) {
					mutationHandler.onNext(modelConverter.map(value.getEventsList(), IdentityEventSummary.class));
					value.getEventsList();
				}
			});
	}

	@Override
	public void answerIdentityEvent(String eventid, IdentityEventAnswer event,
		MutationHandler<IdentityEvent> mutationHandler) {
		AnswerIdentityEventRequest.Builder request = AnswerIdentityEventRequest.newBuilder();
		request.setAnswer(modelConverter.map(event, se.tink.grpc.v1.models.IdentityEventAnswer.class));
		request.setIdentityEventId(eventid);
		service.answerIdentityEvent(request.build(),
			new SimpleStreamObserver<AnswerIdentityEventResponse>(mutationHandler) {
				@Override
				public void onNext(AnswerIdentityEventResponse value) {
					IdentityEvent identityEvent = modelConverter.map(value.getEvent(), IdentityEvent.class);
					mutationHandler.onNext(identityEvent);
				}
			});
	}

	@Override
	public void getIdentityState(MutationHandler<IdentityState> mutationHandler) {
		service.getIdentityState(IdentityStateRequest.getDefaultInstance(),
			new SimpleStreamObserver<IdentityStateResponse>(mutationHandler) {
				@Override
				public void onNext(IdentityStateResponse value) {
					IdentityState state = modelConverter.map(value.getState(), IdentityState.class);
					mutationHandler.onNext(state);
				}
			});
	}
}
