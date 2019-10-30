package se.tink.converter.transfer;

import com.google.protobuf.Timestamp;
import se.tink.converter.ModelConverter;
import se.tink.core.models.transfer.Transfer;
import se.tink.modelConverter.AbstractConverter;

public class TransferToTransferDTOConverter extends
	AbstractConverter<Transfer, se.tink.grpc.v1.models.Transfer> {

	private ModelConverter modelConverter;

	public TransferToTransferDTOConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public se.tink.grpc.v1.models.Transfer convert(Transfer source) {
		se.tink.grpc.v1.models.Transfer.Builder transfer = se.tink.grpc.v1.models.Transfer
			.newBuilder();
		transfer.setSourceUri(source.getSourceUri());
		transfer.setDestinationUri(source.getDestinationUri());
		transfer.setCredentialId(source.getCredentialId());
		transfer.setAmount(modelConverter
			.map(source.getAmount(), se.tink.grpc.v1.models.CurrencyDenominatedAmount.class));
		if (source.getDestinationMessage() != null) {
			transfer.setDestinationMessage(source.getDestinationMessage());
		}
		if (source.getDueDate() != null) {
			transfer.setDueDate(modelConverter.map(source.getDueDate(), Timestamp.class));
		}
		transfer.setId(source.getId());
		if (source.getSourceMessage() != null) {
			transfer.setSourceMessage(source.getSourceMessage());
		}
		transfer.setType(getType(source.getType()));

		return transfer.build();
	}

	@Override
	public Class<Transfer> getSourceClass() {
		return Transfer.class;
	}

	@Override
	public Class<se.tink.grpc.v1.models.Transfer> getDestinationClass() {
		return se.tink.grpc.v1.models.Transfer.class;
	}

	private se.tink.grpc.v1.models.Transfer.Type getType(
		se.tink.core.models.transfer.Transfer.Type type) {
		switch (type) {
			case TYPE_BANK_TRANSFER:
				return se.tink.grpc.v1.models.Transfer.Type.TYPE_BANK_TRANSFER;
			case TYPE_EINVOICE:
				return se.tink.grpc.v1.models.Transfer.Type.TYPE_EINVOICE;
			case TYPE_PAYMENT:
				return se.tink.grpc.v1.models.Transfer.Type.TYPE_PAYMENT;
			case TYPE_UNKNOWN:
			default:
				return se.tink.grpc.v1.models.Transfer.Type.TYPE_UNKNOWN;
		}
	}

}
