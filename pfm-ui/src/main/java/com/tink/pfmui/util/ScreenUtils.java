package com.tink.pfmui.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

public class ScreenUtils {

	public static int dpToPixels(Context context, int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
			context.getResources().getDisplayMetrics());
	}

	public static int dpToPixels(Context context, float dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
			context.getResources().getDisplayMetrics());
	}

	public static int pixelsToDp(Context context, int pixels) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		return (int) Math.floor(pixels / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
	}

	public static Rect getSize(String text, Paint paint) {
		Rect bounds = new Rect();
		if (text != null && paint != null) {
			paint.getTextBounds(text, 0, text.length(), bounds);
		}
		return bounds;
	}

	public static int getDimensionPixelSize(int id) {
		return Resources.getSystem().getDimensionPixelSize(id);
	}

	public static DisplayMetrics getScreenMetrics(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		return metrics;
	}
}
