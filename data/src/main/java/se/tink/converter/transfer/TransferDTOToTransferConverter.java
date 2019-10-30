package se.tink.converter.transfer;

import org.joda.time.DateTime;
import se.tink.converter.ModelConverter;
import se.tink.core.models.misc.Amount;
import se.tink.core.models.transfer.Transfer;
import se.tink.modelConverter.AbstractConverter;

public class TransferDTOToTransferConverter extends
	AbstractConverter<se.tink.grpc.v1.models.Transfer, Transfer> {

	private final ModelConverter modelConverter;

	public TransferDTOToTransferConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public Transfer convert(se.tink.grpc.v1.models.Transfer source) {
		Transfer transfer = new Transfer();
		transfer.setSourceUri(source.getSourceUri());
		transfer.setDestinationUri(source.getDestinationUri());
		transfer.setCredentialId(source.getCredentialId());
		transfer.setAmount(modelConverter.map(source.getAmount(), Amount.class));
		if (source.getDestinationMessage() != null) {
			transfer.setDestinationMessage(source.getDestinationMessage());
		}
		if (source.getDueDate() != null) {
			transfer.setDueDate(modelConverter.map(source.getDueDate(), DateTime.class));
		}
		transfer.setId(source.getId());
		if (source.getSourceMessage() != null) {
			transfer.setSourceMessage(source.getSourceMessage());
		}
		transfer.setType(getType(source.getType()));

		return transfer;
	}

	@Override
	public Class<se.tink.grpc.v1.models.Transfer> getSourceClass() {
		return se.tink.grpc.v1.models.Transfer.class;
	}

	@Override
	public Class<Transfer> getDestinationClass() {
		return Transfer.class;
	}

	private Transfer.Type getType(se.tink.grpc.v1.models.Transfer.Type type) {
		switch (type) {
			case TYPE_BANK_TRANSFER:
				return Transfer.Type.TYPE_BANK_TRANSFER;
			case TYPE_EINVOICE:
				return Transfer.Type.TYPE_EINVOICE;
			case TYPE_PAYMENT:
				return Transfer.Type.TYPE_PAYMENT;
			case TYPE_UNKNOWN:
			default:
				return Transfer.Type.TYPE_UNKNOWN;
		}
	}
}
