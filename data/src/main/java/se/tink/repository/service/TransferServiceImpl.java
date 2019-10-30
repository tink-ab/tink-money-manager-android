package se.tink.repository.service;

import java.util.List;
import javax.inject.Inject;
import se.tink.converter.ModelConverter;
import se.tink.core.models.transfer.Clearing;
import se.tink.core.models.transfer.GiroLookupEntity;
import se.tink.core.models.transfer.SignableOperation;
import se.tink.core.models.transfer.Transfer;
import se.tink.core.models.transfer.TransferDestination;
import se.tink.core.models.transfer.TransferDestinationPerAccount;
import se.tink.grpc.v1.rpc.ClearingLookupRequest;
import se.tink.grpc.v1.rpc.ClearingLookupResponse;
import se.tink.grpc.v1.rpc.CreateTransferDestinationRequest;
import se.tink.grpc.v1.rpc.CreateTransferDestinationResponse;
import se.tink.grpc.v1.rpc.CreateTransferRequest;
import se.tink.grpc.v1.rpc.CreateTransferResponse;
import se.tink.grpc.v1.rpc.GetTransferDestinationsRequest;
import se.tink.grpc.v1.rpc.GetTransferDestinationsResponse;
import se.tink.grpc.v1.rpc.GiroLookupRequest;
import se.tink.grpc.v1.rpc.GiroLookupResponse;
import se.tink.grpc.v1.rpc.TransferGetRequest;
import se.tink.grpc.v1.rpc.TransferGetResponse;
import se.tink.grpc.v1.rpc.TransferListRequest;
import se.tink.grpc.v1.rpc.TransferListResponse;
import se.tink.grpc.v1.rpc.UpdateTransferRequest;
import se.tink.grpc.v1.rpc.UpdateTransferResponse;
import se.tink.grpc.v1.services.TransferServiceGrpc;
import se.tink.repository.MutationHandler;
import se.tink.repository.SimpleStreamObserver;

public class TransferServiceImpl implements TransferService {

private final TransferServiceGrpc.TransferServiceStub service;
	private final ModelConverter converter;

    @Inject
    public TransferServiceImpl(TransferServiceGrpc.TransferServiceStub stub, ModelConverter converter) {
        service = stub;
        this.converter = converter;
    }


	@Override
	public void listTransfers(String type, final MutationHandler<List<Transfer>> handler) {
        TransferListRequest.Builder builder = TransferListRequest.newBuilder();
		builder.setType(se.tink.grpc.v1.models.Transfer.Type.valueOf(type));
        TransferListRequest request = builder.build();
        service.listTransfers(request, new SimpleStreamObserver<TransferListResponse>(handler) {
            @Override
            public void onNext(TransferListResponse value) {
                handler.onNext(converter.map(value.getTransfersList(), Transfer.class));
            }
        });
	}

	@Override
	public void getTransfer(String id, final MutationHandler<Transfer> handler) {
        TransferGetRequest.Builder builder = TransferGetRequest.newBuilder();
        builder.setTransferId(id);
        TransferGetRequest request = builder.build();
        service.getTransfer(request, new SimpleStreamObserver<TransferGetResponse>(handler) {
            @Override
            public void onNext(TransferGetResponse value) {
                handler.onNext(converter.map(value.getTransfer(), Transfer.class));
            }
        });
	}

	@Override
	public void createTransfer(Transfer transfer,
		final MutationHandler<SignableOperation> handler) {
        CreateTransferRequest request = converter.map(transfer, CreateTransferRequest.class);
        service.createTransfer(request, new SimpleStreamObserver<CreateTransferResponse>(handler) {
            @Override
            public void onNext(CreateTransferResponse value) {
                handler.onNext(converter.map(value.getSignableOperation(), SignableOperation.class));
            }
        });
	}

	@Override
	public void updateTransfer(Transfer transfer,
		final MutationHandler<SignableOperation> handler) {
        UpdateTransferRequest request = converter.map(transfer, UpdateTransferRequest.class);
        service.updateTransfer(request, new SimpleStreamObserver<UpdateTransferResponse>(handler) {
            @Override
            public void onNext(UpdateTransferResponse value) {
                handler.onNext(converter.map(value.getSignableOperation(), SignableOperation.class));
            }
        });
	}

	@Override
	public void lookupGiro(String giroNumber,
		final MutationHandler<List<GiroLookupEntity>> handler) {
        GiroLookupRequest.Builder builder = GiroLookupRequest.newBuilder();
        builder.setGiroNumber(giroNumber);
        GiroLookupRequest request = builder.build();
        service.lookupGiro(request, new SimpleStreamObserver<GiroLookupResponse>(handler) {
            @Override
            public void onNext(GiroLookupResponse value) {
                handler.onNext(converter.map(value.getGiroEntitiesList(), GiroLookupEntity.class));
            }
        });
	}

	@Override
	public void lookupClearing(String clearingNumber, final MutationHandler<Clearing> handler) {
        ClearingLookupRequest.Builder builder = ClearingLookupRequest.newBuilder();
        builder.setClearingNumber(clearingNumber);
        service.lookupClearing(builder.build(), new SimpleStreamObserver<ClearingLookupResponse>(handler) {
            @Override
            public void onNext(ClearingLookupResponse value) {
                handler.onNext(converter.map(value, Clearing.class));
            }
        });
	}

	@Override
	public void createTransferDestination(TransferDestination transferDestination,
		final MutationHandler<TransferDestination> handler) {
        CreateTransferDestinationRequest.Builder builder = CreateTransferDestinationRequest.newBuilder();
        builder.setUri(transferDestination.getUri());
        builder.setName(transferDestination.getName());
        service.createTransferDestination(builder.build(), new SimpleStreamObserver<CreateTransferDestinationResponse>(handler) {
            @Override
            public void onNext(CreateTransferDestinationResponse value) {
                handler.onNext(converter.map(value.getDestination(), TransferDestination.class));
            }
        });
	}

	@Override
	public void getTransferDestinations(final MutationHandler<List<TransferDestinationPerAccount>> handler) {
		GetTransferDestinationsRequest.Builder builder = GetTransferDestinationsRequest.newBuilder();
		service.getTransferDestinations(builder.build(),
			new SimpleStreamObserver<GetTransferDestinationsResponse>(handler) {
				@Override
				public void onNext(GetTransferDestinationsResponse value) {
					List<TransferDestinationPerAccount> destinationsPerAccount = converter.map(value.getDestinationsPerAccountList(),
						TransferDestinationPerAccount.class);
					handler.onNext(destinationsPerAccount);
				}
			});
	}


}
