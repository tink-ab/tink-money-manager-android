package se.tink.converter.transfer;

import java.util.List;
import se.tink.converter.ModelConverter;
import se.tink.core.models.transfer.TransferDestination;
import se.tink.core.models.transfer.TransferDestinationPerAccount;
import se.tink.modelConverter.AbstractConverter;


public class TransferDestinationPerAccountDTOtoTransferDestinationPerAccount extends
	AbstractConverter<se.tink.grpc.v1.models.TransferDestinationPerAccount, TransferDestinationPerAccount> {

	private ModelConverter modelConverter;

	public TransferDestinationPerAccountDTOtoTransferDestinationPerAccount(
		ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}


	@Override
	public TransferDestinationPerAccount convert(
		se.tink.grpc.v1.models.TransferDestinationPerAccount source) {
		TransferDestinationPerAccount transferDestinationPerAccount = new TransferDestinationPerAccount();
		transferDestinationPerAccount.setAccountId(source.getAccountId());
		List<TransferDestination> map = modelConverter
			.map(source.getDestinationsList(), TransferDestination.class);
		transferDestinationPerAccount.setDestinations(map);
		return transferDestinationPerAccount;
	}

	@Override
	public Class<se.tink.grpc.v1.models.TransferDestinationPerAccount> getSourceClass() {
		return se.tink.grpc.v1.models.TransferDestinationPerAccount.class;
	}

	@Override
	public Class<TransferDestinationPerAccount> getDestinationClass() {
		return TransferDestinationPerAccount.class;
	}
}
