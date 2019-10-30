package se.tink.commons.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.res.use
import kotlinx.android.synthetic.main.period_picker.view.*
import se.tink.commons.R
import se.tink.commons.extensions.inflate

class PeriodPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {
    var onPreviousClicked: (() -> Unit)? = null
    var onNextClicked: (() -> Unit)? = null

    init {
        inflate(R.layout.period_picker, true)

        iconLeft.setOnClickListener { onPreviousClicked?.invoke() }
        iconRight.setOnClickListener { onNextClicked?.invoke() }

        context.obtainStyledAttributes(attrs, R.styleable.PeriodPicker, defStyleAttr, defStyleRes)
            .use {
                setShowButtons(it.getBoolean(R.styleable.PeriodPicker_showButtons, true))
                setNextButtonEnabled(
                    it.getBoolean(
                        R.styleable.PeriodPicker_nextButtonEnabled,
                        true
                    )
                )
                setPreviousButtonEnabled(
                    it.getBoolean(R.styleable.PeriodPicker_previousButtonEnabled, true)
                )

                setText(it.getText(R.styleable.PeriodPicker_android_text))
            }
    }

    fun setShowButtons(visible: Boolean) {
        val visibility = if (visible) View.VISIBLE else View.GONE
        iconLeft.visibility = visibility
        iconRight.visibility = visibility
    }

    fun setText(text: CharSequence?) {
        text?.let {
            title.text = it.toString().capitalize()
        }
    }

    fun setPreviousButtonEnabled(enabled: Boolean) {
        iconLeft.isEnabled = enabled
    }

    fun setNextButtonEnabled(enabled: Boolean) {
        iconRight.isEnabled = enabled
    }
}