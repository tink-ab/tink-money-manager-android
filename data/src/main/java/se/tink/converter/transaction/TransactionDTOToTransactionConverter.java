package se.tink.converter.transaction;

import org.joda.time.DateTime;
import se.tink.converter.ModelConverter;
import se.tink.core.models.misc.Amount;
import se.tink.core.models.transaction.Tag;
import se.tink.core.models.transaction.Transaction;
import se.tink.core.models.transaction.TransactionDetails;
import se.tink.modelConverter.AbstractConverter;

public class TransactionDTOToTransactionConverter extends
	AbstractConverter<se.tink.grpc.v1.models.Transaction, Transaction> {

	private final ModelConverter converter;

	public TransactionDTOToTransactionConverter(ModelConverter modelConverter) {
		this.converter = modelConverter;
	}

	@Override
	public Transaction convert(se.tink.grpc.v1.models.Transaction source) {
		Transaction destination = new Transaction();
		destination.setAmount(converter.map(source.getAmount(), Amount.class));
		if (source.hasDispensableAmount()) {
			destination
				.setDispensableAmount(converter.map(source.getDispensableAmount(), Amount.class));
		}
		destination.setId(source.getId());
		destination.setAccountId(source.getAccountId());
		destination.setCategoryCode(source.getCategoryCode());
		destination.setTimestamp(converter.map(source.getDate(), DateTime.class));
		destination.setDescription(source.getDescription());
		destination.setSecondaryDescription(source.getSecondaryDescription());
		destination.setNotes(source.getNotes());
		destination.setOriginalTimestamp(converter.map(source.getOriginalDate(), DateTime.class));
		destination.setOriginalDescription(source.getOriginalDescription());
		destination.setInsertionTime(converter.map(source.getInserted(), DateTime.class));
		destination.setPending(source.getPending());
		destination.setUpcoming(source.getUpcoming());
		destination.setDetails(converter.map(source.getDetails(), TransactionDetails.class));
		if (source.getTagsList() != null) {
			//noinspection unchecked
			destination.setTags(converter.map(source.getTagsList(), Tag.class));
		}
		return destination;
	}


	@Override
	public Class<se.tink.grpc.v1.models.Transaction> getSourceClass() {
		return se.tink.grpc.v1.models.Transaction.class;
	}

	@Override
	public Class<Transaction> getDestinationClass() {
		return Transaction.class;
	}

}
