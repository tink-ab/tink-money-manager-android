package com.tink.pfmui.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class SoftKeyboardUtils {

	static public void openSoftKeyboard(Context context, View view) {
		view.requestFocus();
		InputMethodManager inputMethodManager = (InputMethodManager) context
			.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}

	static public void toggleSoftKeyboard(Context context) {
		InputMethodManager inputMethodManager = (InputMethodManager) context
			.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager
			.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	static public void closeSoftKeyboard(Context context, View view) {
		InputMethodManager keyboard = (InputMethodManager) context
			.getSystemService(Context.INPUT_METHOD_SERVICE);
		keyboard.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	static public void closeSoftKeyboard(Activity activity) {
		if (activity == null) {
			return;
		}
		View currentFocus = activity.getCurrentFocus();
		if (currentFocus != null) {
			closeSoftKeyboard(activity, currentFocus);
		}
	}

}
