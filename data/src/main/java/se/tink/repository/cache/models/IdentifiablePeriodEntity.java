package se.tink.repository.cache.models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import se.tink.repository.cache.database.TableNames;

@Entity(tableName = TableNames.PERIODS)
public class IdentifiablePeriodEntity {

	@NonNull
	@PrimaryKey
	private String periodKey;

	@Embedded
	private PeriodEntity periodEntity;

	@NonNull
	public String getPeriodKey() {
		return periodKey;
	}

	public void setPeriodKey(@NonNull String periodKey) {
		this.periodKey = periodKey;
	}

	public PeriodEntity getPeriodEntity() {
		return periodEntity;
	}

	public void setPeriodEntity(PeriodEntity periodEntity) {
		this.periodEntity = periodEntity;
	}
}
