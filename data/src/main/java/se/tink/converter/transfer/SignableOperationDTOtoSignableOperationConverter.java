package se.tink.converter.transfer;

import org.joda.time.DateTime;
import se.tink.converter.EnumMappers;
import se.tink.converter.ModelConverter;
import se.tink.core.models.transfer.SignableOperation;
import se.tink.modelConverter.AbstractConverter;

public class SignableOperationDTOtoSignableOperationConverter extends
	AbstractConverter<se.tink.grpc.v1.models.SignableOperation, SignableOperation> {

	private ModelConverter modelConverter;

	public SignableOperationDTOtoSignableOperationConverter(ModelConverter modelConverter) {

		this.modelConverter = modelConverter;
	}

	@Override
	public SignableOperation convert(se.tink.grpc.v1.models.SignableOperation source) {
		SignableOperation signableOperation = new SignableOperation();
		signableOperation.setId(source.getId());
		signableOperation.setCreated(modelConverter.map(source.getCreated(), DateTime.class));
		signableOperation.setStatus(EnumMappers.GRPC_TO_MODEL_SIGNABLE_OPERATION_STATUS.get(source.getStatus()));
		signableOperation.setStatusMessage(source.getStatusMessage());
		signableOperation.setType(EnumMappers.GRPC_TO_MODEL_SIGNABLE_OPERATION_TYPE.get(source.getType()));
		signableOperation.setUnderlyingId(source.getUnderlyingId());
		signableOperation.setUpdated(modelConverter.map(source.getUpdated(), DateTime.class));
		signableOperation.setCredentialId(source.getCredentialId());
		return signableOperation;
	}

	@Override
	public Class<se.tink.grpc.v1.models.SignableOperation> getSourceClass() {
		return se.tink.grpc.v1.models.SignableOperation.class;
	}

	@Override
	public Class<SignableOperation> getDestinationClass() {
		return SignableOperation.class;
	}
}
