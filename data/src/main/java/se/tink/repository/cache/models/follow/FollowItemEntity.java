package se.tink.repository.cache.models.follow;


import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import se.tink.repository.cache.database.TableNames;
import se.tink.repository.cache.models.ExactNumberEntity;
import se.tink.repository.cache.models.PeriodEntity;

@Entity(tableName = TableNames.FOLLOW_ITEMS)
public class FollowItemEntity {

	@NonNull
	@PrimaryKey
	private String id;
	private String name;

	private String savingsPeriod;

	@Embedded(prefix = "period")
	private PeriodEntity period;

	@Embedded(prefix = "targetAmount")
	private ExactNumberEntity targetAmount;

	private long createdDate;

	//TODO
//    private FollowData data;
//    private ExpensesFollowCriteria expensesCriteria;
//    private SearchFollowCriteria searchCriteria;
//    private SavingsFollowCriteria savingCriteria;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PeriodEntity getPeriod() {
		return period;
	}

	public void setPeriod(PeriodEntity period) {
		this.period = period;
	}

	public String getSavingsPeriod() {
		return savingsPeriod;
	}

	public void setSavingsPeriod(String savingsPeriod) {
		this.savingsPeriod = savingsPeriod;
	}

	public ExactNumberEntity getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(ExactNumberEntity targetAmount) {
		this.targetAmount = targetAmount;
	}

	public long getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}
}
