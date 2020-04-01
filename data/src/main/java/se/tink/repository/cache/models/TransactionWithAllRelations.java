package se.tink.repository.cache.models;


import androidx.annotation.NonNull;
import androidx.room.Relation;
import java.util.ArrayList;
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

	@NonNull
	public List<CounterpartEntity> getCounterpartEntities() {
		if (counterpartEntities != null) {
			return counterpartEntities;
		} else {
			return new ArrayList<>();
		}
	}

	public void setCounterpartEntities(
		List<CounterpartEntity> counterpartEntities) {
		this.counterpartEntities = counterpartEntities;
	}
}
