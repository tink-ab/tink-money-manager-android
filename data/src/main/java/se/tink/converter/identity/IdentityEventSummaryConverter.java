package se.tink.converter.identity;

import org.joda.time.DateTime;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.identity.IdentityEventSummary;
import se.tink.modelConverter.AbstractConverter;

public class IdentityEventSummaryConverter extends
	AbstractConverter<se.tink.grpc.v1.models.IdentityEventSummary, IdentityEventSummary> {

	private final ModelConverterImpl modelConverter;

	public IdentityEventSummaryConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public IdentityEventSummary convert(se.tink.grpc.v1.models.IdentityEventSummary source) {
		IdentityEventSummary identityEventSummary = new IdentityEventSummary();
		identityEventSummary.setDate(modelConverter.map(source.getDate(), DateTime.class));
		identityEventSummary.setId(source.getId());
		identityEventSummary.setDescription(source.getDescription());
		identityEventSummary.setSeen(source.getSeen());

		return identityEventSummary;
	}

	@Override
	public Class<se.tink.grpc.v1.models.IdentityEventSummary> getSourceClass() {
		return se.tink.grpc.v1.models.IdentityEventSummary.class;
	}

	@Override
	public Class<IdentityEventSummary> getDestinationClass() {
		return IdentityEventSummary.class;
	}
}