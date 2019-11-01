package com.tink.pfmsdk.view;

import android.view.View;
import androidx.annotation.DrawableRes;

public class GroupPositionUtils {

	public static void applyGroupPosition(GroupPosition position, View view, Theme theme) {
		@DrawableRes int drawableResource;
		switch (position) {
			case Top:
				drawableResource = theme.getTop();
				break;
			case Middle:
				drawableResource = theme.getMiddle();
				break;
			case Bottom:
				drawableResource = theme.getBottom();
				break;
			case Alone:
			default:
				drawableResource = theme.getAlone();
				break;
		}
		view.setBackgroundResource(drawableResource);
		// TODO: Remove during cleanup if not required
//		if (view instanceof RowWithDivider) {
//			((RowWithDivider) view).hideDivider();
//		}
	}

	public interface Theme {

		@DrawableRes
		int getAlone();

		@DrawableRes
		int getTop();

		@DrawableRes
		int getBottom();

		@DrawableRes
		int getMiddle();
	}
}
