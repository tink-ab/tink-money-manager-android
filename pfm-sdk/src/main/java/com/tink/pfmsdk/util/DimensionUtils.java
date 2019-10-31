package com.tink.pfmsdk.util;

import android.content.Context;
import android.content.res.Resources;

public class DimensionUtils {

	public static float getPixelsFromDP(float dp, Context context) {
		return (float) Math.ceil(dp * context.getResources().getDisplayMetrics().density);
	}

	public static float getEmFromDp(float dp) {
		return 0.0624f * dp;
	}

	/**
	 * Calculates furthest point to screen based on X and Y coordinates in screen
	 *
	 * @param screenWidth width of the screen/view
	 * @param screenHeight height of the screen/view
	 * @param xCoordinate x position inside screen/view
	 * @param yCoordinate y position inside screen/view
	 * @return returns the furthest point form coordinates to screen/view as a Double
	 */
	public static double getFurthestPointOnScreen(final int screenWidth, final int screenHeight,
		final int xCoordinate, final int yCoordinate) {
		int y = screenHeight / 2 < yCoordinate ? yCoordinate : screenHeight - yCoordinate;
		int x = screenWidth / 2 < xCoordinate ? xCoordinate : screenWidth - xCoordinate;
		return Math.hypot(x, y);
	}

	public static float getScreenWidth(Resources resources) {
		return resources.getDisplayMetrics().widthPixels;
	}

}
