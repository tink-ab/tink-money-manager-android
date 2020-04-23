package se.tink.repository.cache.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import se.tink.repository.cache.database.TableNames;

@Entity(tableName = TableNames.CATEGORIES)
public class CategoryEntity {

	@PrimaryKey
	@NonNull
	private String code;

	@ForeignKey(entity = CategoryEntity.class, parentColumns = "code", childColumns = "parentCode")
	private String parentCode;

	private String name;

	private int sortOrder;

	private String type;

	private boolean defaultChild;

	public boolean isDefaultChild() {
		return defaultChild;
	}

	public void setDefaultChild(boolean defaultChild) {
		this.defaultChild = defaultChild;
	}

	@NonNull
	public String getCode() {
		return code;
	}

	public void setCode(@NonNull String code) {
		this.code = code;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getParentCode() {
		return parentCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
