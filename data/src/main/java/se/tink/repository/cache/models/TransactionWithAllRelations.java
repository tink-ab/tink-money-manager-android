package se.tink.repository.cache.models;


import androidx.room.Relation;
import java.util.List;

public class TransactionWithAllRelations extends TransactionEntity {

	@Relation(parentColumn = "id", entityColumn = "transactionId")
	private List<TagEntity> tagEntities;

	@Relation(parentColumn = "id", entityColumn = "transactionId")
	private List<CounterpartEntity> counterpartEntities;

	public TransactionWithAllRelations() {
		super();
	}

	public List<TagEntity> getTagEntities() {
		return tagEntities;
	}

	public void setTagEntities(List<TagEntity> tagEntities) {
		this.tagEntities = tagEntities;
	}

	public List<CounterpartEntity> getCounterpartEntities() {
		return counterpartEntities;
	}

	public void setCounterpartEntities(
		List<CounterpartEntity> counterpartEntities) {
		this.counterpartEntities = counterpartEntities;
	}
}
