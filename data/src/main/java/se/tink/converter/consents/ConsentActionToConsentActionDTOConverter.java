package se.tink.converter.consents;


import se.tink.core.models.consents.ConsentAction;
import se.tink.modelConverter.AbstractConverter;

public class ConsentActionToConsentActionDTOConverter extends
	AbstractConverter<ConsentAction, se.tink.grpc.v1.models.ConsentAction> {

	@Override
	public se.tink.grpc.v1.models.ConsentAction convert(ConsentAction source) {
		switch (source) {
			case CONSENT_ACTION_UNKNOWN:
				return se.tink.grpc.v1.models.ConsentAction.CONSENT_ACTION_UNKNOWN;
			case CONSENT_ACTION_ACCEPTED:
				return se.tink.grpc.v1.models.ConsentAction.CONSENT_ACTION_ACCEPTED;
			case CONSENT_ACTION_DECLINED:
				return se.tink.grpc.v1.models.ConsentAction.CONSENT_ACTION_DECLINED;
		}
		return se.tink.grpc.v1.models.ConsentAction.UNRECOGNIZED;
	}

	@Override
	public Class<ConsentAction> getSourceClass() {
		return ConsentAction.class;
	}

	@Override
	public Class<se.tink.grpc.v1.models.ConsentAction> getDestinationClass() {
		return se.tink.grpc.v1.models.ConsentAction.class;
	}
}
