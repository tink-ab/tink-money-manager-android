package se.tink.repository.cache.models.follow;


import androidx.room.Embedded;
import androidx.room.Relation;
import java.util.List;

public class FollowItemWithRelations {

	@Embedded
	private FollowItemEntity followItemEntity;

	@Relation(parentColumn = "id", entityColumn = "followItemId", entity = FollowExpenseCategoryCodeEntity.class, projection = "categoryCode")
	private List<String> expenseCriteriaCodes;

	@Relation(parentColumn = "id", entityColumn = "followItemId", entity = FollowSavingAccountIdEntity.class, projection = "accountId")
	private List<String> savingsAccountIds;

	@Relation(parentColumn = "id", entityColumn = "followItemId")
	private List<FollowHistoricalAmountEntity> historicalAmounts;

	@Relation(parentColumn = "id", entityColumn = "followItemId")
	private List<FollowItemTransactionEntity> transactionEntities;


	public FollowItemEntity getFollowItemEntity() {
		return followItemEntity;
	}

	public void setFollowItemEntity(FollowItemEntity followItemEntity) {
		this.followItemEntity = followItemEntity;
	}

	public List<String> getExpenseCriteriaCodes() {
		return expenseCriteriaCodes;
	}

	public void setExpenseCriteriaCodes(List<String> expenseCriteriaCodes) {
		this.expenseCriteriaCodes = expenseCriteriaCodes;
	}

	public List<String> getSavingsAccountIds() {
		return savingsAccountIds;
	}

	public void setSavingsAccountIds(List<String> savingsAccountIds) {
		this.savingsAccountIds = savingsAccountIds;
	}

	public List<FollowHistoricalAmountEntity> getHistoricalAmounts() {
		return historicalAmounts;
	}

	public void setHistoricalAmounts(List<FollowHistoricalAmountEntity> historicalAmounts) {
		this.historicalAmounts = historicalAmounts;
	}

	public List<FollowItemTransactionEntity> getTransactionEntities() {
		return transactionEntities;
	}

	public void setTransactionEntities(List<FollowItemTransactionEntity> transactionEntities) {
		this.transactionEntities = transactionEntities;
	}
}
