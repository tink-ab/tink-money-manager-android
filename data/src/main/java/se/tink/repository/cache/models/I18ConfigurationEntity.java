package se.tink.repository.cache.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import se.tink.repository.cache.database.TableNames;

@Entity(tableName = TableNames.I18CONFIGURATION)
public class I18ConfigurationEntity {

	@PrimaryKey
	private int id = 1;

	private String currencyCode;
	private String localeCode;
	private String marketCode;
	private String timezoneCode;

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public String getMarketCode() {
		return marketCode;
	}

	public void setMarketCode(String marketCode) {
		this.marketCode = marketCode;
	}

	public String getTimezoneCode() {
		return timezoneCode;
	}

	public void setTimezoneCode(String timezoneCode) {
		this.timezoneCode = timezoneCode;
	}

	public int getId() {
		return 1;
	}

	public void setId(int id) {
		this.id = id;
	}
}
