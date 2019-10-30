package se.tink.converter.transfer;

import com.google.protobuf.Timestamp;
import se.tink.converter.ModelConverter;
import se.tink.core.models.transfer.Transfer;
import se.tink.grpc.v1.rpc.CreateTransferRequest;
import se.tink.modelConverter.AbstractConverter;

public class CreateTransferRequestConverter extends
	AbstractConverter<Transfer, CreateTransferRequest> {

	private ModelConverter modelConverter;

	public CreateTransferRequestConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public se.tink.grpc.v1.rpc.CreateTransferRequest convert(Transfer source) {
		se.tink.grpc.v1.rpc.CreateTransferRequest.Builder transfer = se.tink.grpc.v1.rpc.CreateTransferRequest
			.newBuilder();
		transfer.setSource(source.getSourceUri());
		transfer.setDestination(source.getDestinationUri());
		transfer.setAmount(modelConverter
			.map(source.getAmount(), se.tink.grpc.v1.models.CurrencyDenominatedAmount.class));
		if (source.getDestinationMessage() != null) {
			transfer.setDestinationMessage(source.getDestinationMessage());
		}
		if (source.getDueDate() != null) {
			transfer.setDueDate(modelConverter.map(source.getDueDate(), Timestamp.class));
		}
		return transfer.build();
	}

	@Override
	public Class<Transfer> getSourceClass() {
		return Transfer.class;
	}

	@Override
	public Class<CreateTransferRequest> getDestinationClass() {
		return CreateTransferRequest.class;
	}
}
