package se.tink.repository.cache.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;
import java.util.List;
import se.tink.repository.cache.models.TransactionEntity;
import se.tink.repository.cache.models.follow.FollowItemTransactionEntity;

@Dao
public abstract class FollowItemTransactionDao extends BaseDao<FollowItemTransactionEntity> {

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	public abstract void insertAll(List<FollowItemTransactionEntity> items);

	@Query(
		"SELECT * FROM " + TableNames.TRANSACTIONS + " INNER JOIN " + TableNames.FOLLOW_TRANSACTIONS
			+
			" ON transactions.id=follow_transactions.transactionId WHERE follow_transactions.followItemId=:itemId")
	@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH) //two columns are unused, but it's not reasonable to SELECT all the 10+ other columns instead
	public abstract List<TransactionEntity> getTransactionsForFollowItem(String itemId);

	@Query("DELETE FROM " + TableNames.FOLLOW_TRANSACTIONS)
	public abstract void clear();

	@Transaction
	public void clearAndInsertAll(List<FollowItemTransactionEntity> items) {
		clear();
		insertAll(items);
	}
}

