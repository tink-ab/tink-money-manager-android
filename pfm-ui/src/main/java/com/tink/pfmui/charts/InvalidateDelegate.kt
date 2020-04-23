package com.tink.pfmui.charts

import android.view.View
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal class InvalidateDelegate<T>(initial: T) : ReadWriteProperty<View, T>{

    private var value: T = initial

    override fun getValue(thisRef: View, property: KProperty<*>): T {
        return value
    }

    override fun setValue(thisRef: View, property: KProperty<*>, value: T) {
        this.value = value
        thisRef.invalidate()
    }
}
