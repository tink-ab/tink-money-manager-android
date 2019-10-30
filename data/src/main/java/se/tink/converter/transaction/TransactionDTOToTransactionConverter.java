package se.tink.converter.transaction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.joda.time.DateTime;
import se.tink.converter.ModelConverter;
import se.tink.core.models.misc.Amount;
import se.tink.core.models.transaction.Counterpart;
import se.tink.core.models.transaction.Tag;
import se.tink.core.models.transaction.Transaction;
import se.tink.core.models.transaction.TransactionDetails;
import se.tink.grpc.v1.models.Transaction.Part;
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
		List<Counterpart> counterparts = new ArrayList<>();
		for (CounterpartWithPart counterpartWithPart : getCounterpartsList(source)) {
			counterparts.add(mapCounterpartFrom(counterpartWithPart));
		}
		destination.setCounterparts(counterparts);
		return destination;
	}

	private static List<CounterpartWithPart> getCounterpartsList(
		se.tink.grpc.v1.models.Transaction transaction) {
		List<Part> parts = transaction.getPartsList();
		List<CounterpartWithPart> counterparts = new LinkedList<>();

		if (parts != null) {
			for (Part part : parts) {
				if (part.hasCounterpart()) {
					CounterpartWithPart counterpartWithPart =
						new CounterpartWithPart(part.getCounterpart(), part);

					counterparts.add(counterpartWithPart);
				}
			}
		}

		return counterparts;
	}

	private Counterpart mapCounterpartFrom(
		CounterpartWithPart from) {
		final Amount partAmount = converter.map(from.getPart().getAmount(), Amount.class);
		final Amount counterpartAmount = new Amount(
			partAmount.getValue().negate(), partAmount.getCurrencyCode());

		final Amount transactionAmount = converter
			.map(from.getCounterpart().getTransactionAmount(), Amount.class);

		final se.tink.grpc.v1.models.Transaction.Counterpart counterpart = from.getCounterpart();

		return new Counterpart(
			counterpart.getId(),
			from.getPart().getId(),
			counterpart.getTransactionId(),
			counterpartAmount,
			transactionAmount,
			counterpart.getTransactionDescription()
		);
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
