package se.tink.converter.consents;

import se.tink.converter.ModelConverter;
import se.tink.core.models.consents.ConsentMessageLink;
import se.tink.modelConverter.AbstractConverter;

public class ConsentsMessageLinkDTOToConsentMessageLinkResponseConverter extends
	AbstractConverter<se.tink.grpc.v1.models.ConsentMessageLink, ConsentMessageLink> {

	private ModelConverter modelConverter;

	public ConsentsMessageLinkDTOToConsentMessageLinkResponseConverter(
		ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public ConsentMessageLink convert(se.tink.grpc.v1.models.ConsentMessageLink source) {
		ConsentMessageLink destination = new ConsentMessageLink();
		destination.setEnd(source.getEnd());
		destination.setStart(source.getStart());
		destination.setTo(source.getTo());
		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.models.ConsentMessageLink> getSourceClass() {
		return se.tink.grpc.v1.models.ConsentMessageLink.class;
	}

	@Override
	public Class<ConsentMessageLink> getDestinationClass() {
		return ConsentMessageLink.class;
	}

}
