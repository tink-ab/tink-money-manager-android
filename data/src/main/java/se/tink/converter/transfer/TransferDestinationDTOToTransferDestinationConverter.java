package se.tink.converter.transfer;


import se.tink.converter.EnumMappers;
import se.tink.converter.ModelConverter;
import se.tink.core.models.Images;
import se.tink.core.models.misc.Amount;
import se.tink.core.models.transfer.TransferDestination;
import se.tink.modelConverter.AbstractConverter;

public class TransferDestinationDTOToTransferDestinationConverter extends
    AbstractConverter<se.tink.grpc.v1.models.TransferDestination, TransferDestination> {

    private ModelConverter modelConverter;

    public TransferDestinationDTOToTransferDestinationConverter(ModelConverter modelConverter) {
        this.modelConverter = modelConverter;
    }

    @Override
    public TransferDestination convert(se.tink.grpc.v1.models.TransferDestination source) {
        TransferDestination destination = new TransferDestination();
        destination.setBalance(modelConverter.map(source.getBalance(), Amount.class));
        destination.setDisplayAccountNumber(source.getDisplayAccountNumber());
        destination.setDisplayBankName(source.getDisplayBankName());
        destination.setName(source.getName());
        destination.setType(EnumMappers.GRPC_TO_MODEL_TRANSFER_DESTINATION_TYPE.get(source.getType()));
        destination.setUri(source.getUri());
        destination.setMatchesMultiple(source.getMatchesMultiple());

        if (source.getImages() != null) {
            Images images = modelConverter.map(source.getImages(), Images.class);
            destination.setImages(images);
        }

        return destination;
    }

    @Override
    public Class<se.tink.grpc.v1.models.TransferDestination> getSourceClass() {
        return se.tink.grpc.v1.models.TransferDestination.class;
    }

    @Override
    public Class<TransferDestination> getDestinationClass() {
        return TransferDestination.class;
    }
}
