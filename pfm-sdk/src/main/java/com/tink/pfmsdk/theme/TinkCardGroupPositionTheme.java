package com.tink.pfmsdk.theme;

import com.tink.pfmsdk.R;
import com.tink.pfmsdk.view.GroupPositionUtils.Theme;

public class TinkCardGroupPositionTheme implements Theme {

	@Override
	public int getAlone() {
		return R.drawable.standalone_button_white_selector;
	}

	@Override
	public int getTop() {
		return R.drawable.group_row_selector_top_card;
	}

	@Override
	public int getBottom() {
		return R.drawable.group_row_selector_bottom_card;
	}

	@Override
	public int getMiddle() {
		return R.drawable.group_row_selector_middle_card;
	}

}
