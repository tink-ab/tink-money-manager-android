package com.tink.moneymanagerui.budgets.creation.search

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.extensions.closeKeyboard
import com.tink.moneymanagerui.extensions.openKeyboard

internal class SearchHeader : RelativeLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var onClearListener: (() -> Unit)? = null
    var onTextChangedListener: ((CharSequence) -> Unit)? = null
    var onEditorActionListener: ((CharSequence) -> Boolean)? = null
    private var editText: EditText

    var text: String
        get() = editText.text.toString()
        set(text) {
            if (text == editText.text.toString()) {
                return
            }
            editText.setText(text)
            editText.setSelection(text.length)
        }
    
    init {
        val searchContainer = LayoutInflater.from(context).inflate(
            R.layout.tink_search_header_input_frame,
            this,
            true
        ) as RelativeLayout
        editText = searchContainer.findViewById(R.id.search_input)
        val closeIcon: ImageView = searchContainer.findViewById(R.id.close_icon)

        closeIcon.setOnClickListener {
            onClearListener?.invoke()
            editText.setText("")
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                onTextChangedListener?.invoke(s)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        editText.setOnEditorActionListener { textView, _, _ ->
            onEditorActionListener?.invoke(textView.text) ?: false
        }
    }

    fun focus() {
        editText.requestFocus()
        editText.openKeyboard()
    }

    override fun clearFocus() {
        editText.clearFocus()
        editText.closeKeyboard()
    }

    fun setImeOptions(imeOptions: Int) {
        editText.imeOptions = imeOptions
    }
}
