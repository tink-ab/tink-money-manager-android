package com.tink.moneymanagerui.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun EditText.openKeyboard(): Boolean {
    val keyboard = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return keyboard.showSoftInput(this, 0)
}

fun EditText.closeKeyboard(): Boolean {
    val keyboard = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return keyboard.hideSoftInputFromWindow(windowToken, 0)
}