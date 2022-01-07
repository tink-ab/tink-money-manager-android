package com.tink.moneymanagerui.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object SoftKeyboardUtils {
    fun closeSoftKeyboard(context: Context, view: View) {
        val keyboard = context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.hideSoftInputFromWindow(view.windowToken, 0)
    }

    @JvmStatic
	fun closeSoftKeyboard(activity: Activity?) {
        val currentFocus = activity?.currentFocus
        if (currentFocus != null) {
            closeSoftKeyboard(activity, currentFocus)
        }
    }
}