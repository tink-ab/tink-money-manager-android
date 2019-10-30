package se.tink.converter.transaction;

import java.util.ArrayList;
import java.util.List;
import se.tink.converter.ModelConverter;
import se.tink.core.models.transaction.Counterpart;
import se.tink.core.models.transaction.Tag;
import se.tink.core.models.transaction.Transaction;
import se.tink.repository.cache.models.AmountEntity;
import se.tink.repository.cache.models.CounterpartEntity;
import se.tink.repository.cache.models.TagEntity;
import se.tink.repository.cache.models.TransactionWithAllRelations;

public class TransactionToTransactionWithAllRelationsConverter extends
	se.tink.modelConverter.AbstractConverter<Transaction, TransactionWithAllRelations> {

	private ModelConverter modelConverter;

	public TransactionToTransactionWithAllRelationsConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public TransactionWithAllRelations convert(Transaction source) {
		TransactionWithAllRelations transactionEntity = new TransactionWithAllRelations();
		transactionEntity.setAccountId(source.getAccountId());
		transactionEntity.setCategoryCode(source.getCategoryCode());
		transactionEntity.setId(source.getId());
		transactionEntity.setDescription(source.getDescription());
		transactionEntity.setSecondaryDescription(source.getSecondaryDescription());
		transactionEntity.setOriginalDescription(source.getOriginalDescription());
		transactionEntity.setNotes(source.getNotes());
		transactionEntity.setType(source.getType());
		transactionEntity.setOriginalTimestamp(source.getOriginalTimestamp().getMillis());
		transactionEntity.setTimestamp(source.getTimestamp().getMillis());

		if (source.getAmount() != null) {
			AmountEntity amount = modelConverter.map(source.getAmount(), AmountEntity.class);
			transactionEntity.setAmount(amount);
		}

		if (source.getDispensableAmount() != null) {
			AmountEntity amount = modelConverter
				.map(source.getDispensableAmount(), AmountEntity.class);
			transactionEntity.setDispensableAmount(amount);
		}

		List<TagEntity> tags = mapTags(source.getTags(), source.getId());
		transactionEntity.setTagEntities(tags);

		List<CounterpartEntity> counterpartEntities = mapCounterparts(source.getCounterparts(),
			source.getId());
		transactionEntity.setCounterpartEntities(counterpartEntities);

		return transactionEntity;
	}

	@Override
	public Class<Transaction> getSourceClass() {
		return Transaction.class;
	}

	@Override
	public Class<TransactionWithAllRelations> getDestinationClass() {
		return TransactionWithAllRelations.class;
	}

	private List<TagEntity> mapTags(List<Tag> tags, String transactionId) {

		List<TagEntity> tagEntities = new ArrayList<>();
		for (Tag tag : tags) {
			TagEntity tagEntity = new TagEntity();
			tagEntity.setName(tag.getName());
			tagEntity.setTransactionId(transactionId);
			tagEntities.add(tagEntity);
		}
		return tagEntities;
	}

	private List<CounterpartEntity> mapCounterparts(List<Counterpart> counterparts,
		String transactionId) {
		List<CounterpartEntity> counterpartEntities = modelConverter
			.map(counterparts, CounterpartEntity.class);
		for (CounterpartEntity entity : counterpartEntities) {
			entity.setTransactionId(transactionId);
		}
		return counterpartEntities;
	}
}
