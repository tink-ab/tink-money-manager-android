package se.tink.converter.transfer;

import com.google.protobuf.StringValue;
import com.google.protobuf.Timestamp;
import se.tink.converter.ModelConverter;
import se.tink.core.models.transfer.Transfer;
import se.tink.grpc.v1.rpc.UpdateTransferRequest;
import se.tink.modelConverter.AbstractConverter;

public class UpdateTransferRequestConverter extends
	AbstractConverter<Transfer, UpdateTransferRequest> {

	private ModelConverter modelConverter;

	public UpdateTransferRequestConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public se.tink.grpc.v1.rpc.UpdateTransferRequest convert(Transfer source) {
		se.tink.grpc.v1.rpc.UpdateTransferRequest.Builder transfer = se.tink.grpc.v1.rpc.UpdateTransferRequest
			.newBuilder();

		transfer.setTransferId(source.getId());
		transfer.setSource(StringValue.newBuilder().setValue(source.getSourceUri()));
		transfer.setDestination(StringValue.newBuilder().setValue(source.getDestinationUri()));
		transfer.setAmount(modelConverter
			.map(source.getAmount(), se.tink.grpc.v1.models.CurrencyDenominatedAmount.class));
		if (source.getDestinationMessage() != null) {
			transfer.setDestinationMessage(StringValue.newBuilder().setValue(source.getDestinationMessage()));
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
	public Class<UpdateTransferRequest> getDestinationClass() {
		return UpdateTransferRequest.class;
	}
}
