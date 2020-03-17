package se.tink.converter.transaction;

import java.util.List;
import org.joda.time.DateTime;
import se.tink.converter.ModelConverter;
import com.tink.model.misc.Amount;
import se.tink.core.models.transaction.Tag;
import se.tink.core.models.transaction.Transaction;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.models.TransactionWithAllRelations;

public class TransactionEntityWithAllRelationsToTransactionConverter extends
	AbstractConverter<TransactionWithAllRelations, Transaction> {

	private ModelConverter modelConverter;

	public TransactionEntityWithAllRelationsToTransactionConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public Transaction convert(TransactionWithAllRelations source) {

		Transaction destination = new Transaction();
		destination.setAccountId(source.getAccountId());
		destination.setCategoryCode(source.getCategoryCode());
		destination.setId(source.getId());
		destination.setDescription(source.getDescription());
		destination.setSecondaryDescription(source.getSecondaryDescription());
		destination.setOriginalDescription(source.getOriginalDescription());
		destination.setNotes(source.getNotes());
		destination.setType(source.getType());
		destination.setOriginalTimestamp(new DateTime(source.getOriginalTimestamp()));
		destination.setTimestamp(new DateTime(source.getTimestamp()));

		if (source.getAmount() != null) {
			Amount amount = modelConverter.map(source.getAmount(), Amount.class);
			destination.setAmount(amount);
		}

		if (source.getDispensableAmount() != null) {
			Amount amount = modelConverter.map(source.getDispensableAmount(), Amount.class);
			destination.setDispensableAmount(amount);
		}

		List<Tag> tags = modelConverter.map(source.getTagEntities(), Tag.class);

		destination.setTags(tags);

		return destination;

	}

	@Override
	public Class<TransactionWithAllRelations> getSourceClass() {
		return TransactionWithAllRelations.class;
	}

	@Override
	public Class<Transaction> getDestinationClass() {
		return Transaction.class;
	}

}
