package com.tink.moneymanagerui.extensions

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun EditText.openKeyboard(): Boolean {
    val keyboard = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return keyboard.showSoftInput(this, 0)
}

fun EditText.closeKeyboard(): Boolean {
    val keyboard = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return keyboard.hideSoftInputFromWindow(windowToken, 0)
}

fun EditText.textChangedObserver(): LiveData<String> {
    return MutableLiveData<String>().apply {
        value = text?.toString().orEmpty()
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                value = editable?.toString().orEmpty()
            }

            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }
}
