package se.tink.converter.consents;

import se.tink.converter.ModelConverter;
import se.tink.core.models.consents.ConsentMessage;
import se.tink.core.models.consents.ConsentMessageLink;
import se.tink.modelConverter.AbstractConverter;

public class ConsentsMessageDTOToConsentMessageResponseConverter extends
	AbstractConverter<se.tink.grpc.v1.models.ConsentMessage, ConsentMessage> {

	private ModelConverter modelConverter;

	public ConsentsMessageDTOToConsentMessageResponseConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public ConsentMessage convert(se.tink.grpc.v1.models.ConsentMessage source) {
		ConsentMessage destination = new ConsentMessage();
		if (source.getMessage() != null) {
			source.getMessage();
			destination.setMessage(source.getMessage());
		}
		if (source.getLinksList() != null) {
			destination.setLinks(modelConverter.map(
				source.getLinksList(), ConsentMessageLink.class));
		}
		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.models.ConsentMessage> getSourceClass() {
		return se.tink.grpc.v1.models.ConsentMessage.class;
	}

	@Override
	public Class<ConsentMessage> getDestinationClass() {
		return ConsentMessage.class;
	}

}
