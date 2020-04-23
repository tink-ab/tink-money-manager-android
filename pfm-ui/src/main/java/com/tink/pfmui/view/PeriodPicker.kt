package com.tink.pfmui.view

import android.content.Context
import android.util.AttributeSet
import se.tink.core.models.misc.Period

internal class PeriodPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ItemPicker<Period>(context, attrs, defStyleAttr, defStyleRes)