package com.tink.pfmsdk.overview.charts;

public enum TabsEnum {

	CURRENT_MONTH_PAGE(0),
	SIX_MONTH_PAGE(1),
	TWELVE_MONTH_PAGE(2);

	private int index;

	TabsEnum(int index) {
		this.index = index;
	}

	public static TabsEnum getTabsEnumByIndex(int index) {
		for (TabsEnum result : TabsEnum.values()) {
			if (result.getIndex() == index) {
				return result;
			}
		}
		return null;
	}

	public int getIndex() {
		return index;
	}

}
