package se.tink.repository.cache.models.follow;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.annotation.NonNull;
import se.tink.repository.cache.database.TableNames;
import se.tink.repository.cache.models.ExactNumberEntity;
import se.tink.repository.cache.models.PeriodEntity;

@Entity(primaryKeys = {"followItemId", "periodyear", "periodmonth",
	"periodday"}, tableName = TableNames.FOLLOW_HISTORICAL_AMOUNTS)
public class FollowHistoricalAmountEntity {

	@NonNull
	@ForeignKey(entity = FollowItemEntity.class, parentColumns = "id", childColumns = "followItemId", onDelete = CASCADE)
	private String followItemId;

	@NonNull
	@Embedded(prefix = "period")
	private PeriodEntity period;

	@Embedded
	private ExactNumberEntity value;

	@NonNull
	public String getFollowItemId() {
		return followItemId;
	}

	public void setFollowItemId(@NonNull String followItemId) {
		this.followItemId = followItemId;
	}

	@NonNull
	public PeriodEntity getPeriod() {
		return period;
	}

	public void setPeriod(@NonNull PeriodEntity period) {
		this.period = period;
	}

	public ExactNumberEntity getValue() {
		return value;
	}

	public void setValue(ExactNumberEntity value) {
		this.value = value;
	}
}
