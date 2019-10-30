package se.tink.converter.consents;

import se.tink.converter.ModelConverter;
import se.tink.core.models.consents.Consent;
import se.tink.core.models.consents.ConsentMessage;
import se.tink.modelConverter.AbstractConverter;

public class ConsentsDTOToConcentConverter extends
	AbstractConverter<se.tink.grpc.v1.models.Consent, Consent> {

	private ModelConverter modelConverter;

	public ConsentsDTOToConcentConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public Consent convert(se.tink.grpc.v1.models.Consent source) {
		Consent destination = new Consent();
		if (source.getKey() != null) {
			destination.setKey(source.getKey());
		}
		if (source.getTitle() != null) {
			destination.setTitle(source.getTitle());
		}
		if (source.getVersion() != null) {
			destination.setVersion(source.getVersion());
		}
		if (source.getBody() != null) {
			destination.setBody(source.getBody());
		}
		if (source.getChecksum() != null) {
			destination.setChecksum(source.getChecksum());
		}
		if (source.getMessagesList() != null) {
			destination.setMessageList(modelConverter.map(
				source.getMessagesList(), ConsentMessage.class));
		}
		if (source.getAttachmentsMap() != null) {
			destination.setAttachmentsMap(
				modelConverter.map(source.getAttachmentsMap(), String.class, String.class));
		}
		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.models.Consent> getSourceClass() {
		return se.tink.grpc.v1.models.Consent.class;
	}

	@Override
	public Class<Consent> getDestinationClass() {
		return Consent.class;
	}

}
