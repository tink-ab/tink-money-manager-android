package se.tink.repository.cache.models;


import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import se.tink.repository.cache.database.TableNames;

@Entity(tableName = TableNames.STATISTICS)
public class StatisticEntity {

	@PrimaryKey
	@NonNull
	private String databaseId;

	private String mapIdentifier;

	@Embedded
	private AmountEntity amount;

	@ForeignKey(childColumns = "databaseId", parentColumns = "parentDatabaseId", entity = StatisticEntity.class, onDelete = CASCADE)
	private String parentDatabaseId;

	@Embedded(prefix = "period")
	private PeriodEntity period;

	@NonNull
	public String getMapIdentifier() {
		return mapIdentifier;
	}

	public void setMapIdentifier(@NonNull String mapIdentifier) {
		this.mapIdentifier = mapIdentifier;
	}

	public AmountEntity getAmount() {
		return amount;
	}

	public void setAmount(AmountEntity amount) {
		this.amount = amount;
	}

	public String getParentDatabaseId() {
		return parentDatabaseId;
	}

	public void setParentDatabaseId(String parentDatabaseId) {
		this.parentDatabaseId = parentDatabaseId;
	}

	public PeriodEntity getPeriod() {
		return period;
	}

	public void setPeriod(PeriodEntity periodEntity) {
		this.period = periodEntity;
	}

	public String getDatabaseId() {
		return databaseId;
	}

	public void setDatabaseId(String databaseId) {
		this.databaseId = databaseId;
	}
}
