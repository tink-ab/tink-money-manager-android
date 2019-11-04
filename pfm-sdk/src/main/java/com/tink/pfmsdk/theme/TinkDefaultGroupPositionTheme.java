package com.tink.pfmsdk.theme;


import com.tink.pfmsdk.R;
import com.tink.pfmsdk.view.GroupPositionUtils;

public class TinkDefaultGroupPositionTheme implements GroupPositionUtils.Theme {

	@Override
	public int getAlone() {
		return R.drawable.standalone_button_white_selector_dotted;
	}

	@Override
	public int getTop() {
		return R.drawable.group_row_selector_top_corners_no_dividers;
	}

	@Override
	public int getBottom() {
		return R.drawable.group_row_selector_bottom_dotted;
	}

	@Override
	public int getMiddle() {
		return R.drawable.group_row_middle_selector_no_dividers;
	}
}
