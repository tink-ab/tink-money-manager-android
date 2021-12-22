package se.tink.commons.categories.enums;

public enum CategoryTransferType {

	TRANSFERS_EXCLUDE("exclude", "transfers:exclude"),
	TRANSFERS_SAVINGS("savings", "transfers:savings"),
	TRANSFERS_OTHER("other", "transfers:other");

	private final String type;
	private final String code;

	CategoryTransferType(String type, String code) {
		this.type = type;
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public String getCode() {
		return code;
	}

}