package se.tink.converter.consents;


import java.util.List;
import se.tink.converter.ModelConverter;
import se.tink.core.models.consents.Consent;
import se.tink.grpc.v1.rpc.AvailableConsentsResponse;
import se.tink.modelConverter.AbstractConverter;

public class AvailableConsentsResponseDTOToAvailableConsentsConverter extends
	AbstractConverter<AvailableConsentsResponse, se.tink.core.models.consents.AvailableConsentsResponse> {

	private ModelConverter modelConverter;

	public AvailableConsentsResponseDTOToAvailableConsentsConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public se.tink.core.models.consents.AvailableConsentsResponse convert(
		AvailableConsentsResponse source) {
		se.tink.core.models.consents.AvailableConsentsResponse destination = new se.tink.core.models.consents.AvailableConsentsResponse();
		List<Consent> consents = modelConverter.map(source.getConsentsList(), Consent.class);
		destination.setConsents(consents);
		return destination;
	}

	@Override
	public Class<AvailableConsentsResponse> getSourceClass() {
		return AvailableConsentsResponse.class;
	}

	@Override
	public Class<se.tink.core.models.consents.AvailableConsentsResponse> getDestinationClass() {
		return se.tink.core.models.consents.AvailableConsentsResponse.class;
	}
}
