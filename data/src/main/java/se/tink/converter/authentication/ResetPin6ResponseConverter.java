package se.tink.converter.authentication;


import se.tink.converter.ModelConverter;
import se.tink.core.models.authentication.AuthenticationStatus;
import se.tink.core.models.authentication.ResetPin6Response;
import se.tink.modelConverter.AbstractConverter;

public class ResetPin6ResponseConverter extends
	AbstractConverter<se.tink.grpc.v1.rpc.ResetPin6Response, ResetPin6Response> {

	private ModelConverter modelConverter;

	public ResetPin6ResponseConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public ResetPin6Response convert(se.tink.grpc.v1.rpc.ResetPin6Response source) {
		ResetPin6Response destination = new ResetPin6Response();
		destination.setAuthenticationToken(source.getAuthenticationToken());
		if (source.getStatus() != null) {
			destination
				.setStatus(modelConverter.map(source.getStatus(), AuthenticationStatus.class));
		}
		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.rpc.ResetPin6Response> getSourceClass() {
		return se.tink.grpc.v1.rpc.ResetPin6Response.class;
	}

	@Override
	public Class<ResetPin6Response> getDestinationClass() {
		return ResetPin6Response.class;
	}
}
