package se.tink.converter.consents;


import se.tink.converter.ModelConverter;
import se.tink.core.models.consents.GiveConsentRequest;
import se.tink.grpc.v1.models.ConsentAction;
import se.tink.modelConverter.AbstractConverter;


public class GiveConsentRequestToGiveConsentRequestDTOConverter extends
	AbstractConverter<GiveConsentRequest, se.tink.grpc.v1.rpc.GiveConsentRequest> {

	private ModelConverter modelConverter;

	public GiveConsentRequestToGiveConsentRequestDTOConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public se.tink.grpc.v1.rpc.GiveConsentRequest convert(GiveConsentRequest source) {
		return se.tink.grpc.v1.rpc.GiveConsentRequest.newBuilder()
			.setAction(modelConverter.map(source.getAction(), ConsentAction.class))
			.setChecksum(source.getChecksum())
			.setKey(source.getKey())
			.setVersion(source.getVersion())
			.build();
	}

	@Override
	public Class<GiveConsentRequest> getSourceClass() {
		return GiveConsentRequest.class;
	}

	@Override
	public Class<se.tink.grpc.v1.rpc.GiveConsentRequest> getDestinationClass() {
		return se.tink.grpc.v1.rpc.GiveConsentRequest.class;
	}
}
