package se.tink.converter.consents;

import se.tink.converter.ModelConverter;
import se.tink.core.models.consents.Consent;
import se.tink.core.models.consents.ConsentDetailsResponse;
import se.tink.modelConverter.AbstractConverter;

public class ConsentsDetailsResponseConverter extends
	AbstractConverter<se.tink.grpc.v1.rpc.ConsentDetailsResponse, ConsentDetailsResponse> {

	private ModelConverter modelConverter;

	public ConsentsDetailsResponseConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public ConsentDetailsResponse convert(se.tink.grpc.v1.rpc.ConsentDetailsResponse source) {
		ConsentDetailsResponse destination = new ConsentDetailsResponse();
		if (source.getConsent() != null) {
			destination.setConsent(modelConverter.map(source.getConsent(), Consent.class));
		}
		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.rpc.ConsentDetailsResponse> getSourceClass() {
		return se.tink.grpc.v1.rpc.ConsentDetailsResponse.class;
	}

	@Override
	public Class<ConsentDetailsResponse> getDestinationClass() {
		return ConsentDetailsResponse.class;
	}

}
