package se.tink.repository.service;

import java.util.List;
import se.tink.core.models.transfer.Clearing;
import se.tink.core.models.transfer.GiroLookupEntity;
import se.tink.core.models.transfer.SignableOperation;
import se.tink.core.models.transfer.Transfer;
import se.tink.core.models.transfer.TransferDestination;
import se.tink.core.models.transfer.TransferDestinationPerAccount;
import se.tink.repository.MutationHandler;


public interface TransferService {

	void listTransfers(String type, MutationHandler<List<Transfer>> handler);

	void getTransfer(String id, MutationHandler<Transfer> handler);

	void createTransfer(Transfer transfer, MutationHandler<SignableOperation> handler);

	void updateTransfer(Transfer transfer, MutationHandler<SignableOperation> handler);

	void lookupGiro(String giroNumber, MutationHandler<List<GiroLookupEntity>> handler);

	void lookupClearing(String clearingNumber, MutationHandler<Clearing> handler);

	void createTransferDestination(TransferDestination transferDestination,
		MutationHandler<TransferDestination> handler);

	void getTransferDestinations(MutationHandler<List<TransferDestinationPerAccount>> handler);
}
