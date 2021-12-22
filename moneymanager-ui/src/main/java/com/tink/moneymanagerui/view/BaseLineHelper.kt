package com.tink.moneymanagerui.view

import kotlin.math.ceil
import kotlin.math.floor

internal class BaseLineHelper(private val materialStep: Float) {
    fun putHeightOnGrid(height: Int): Int {
        val offsetFromGrid = height % materialStep
        return if (offsetFromGrid > 0) {
            (materialStep - floor(offsetFromGrid.toDouble())).toInt()
        } else 0
    }

    fun getCompoundTopPaddingToPutBaselineOnGrid(baseLine: Int): Int {
        val offsetFromGrid = baseLine % materialStep
        return if (offsetFromGrid > 0) {
            (materialStep - ceil(offsetFromGrid.toDouble())).toInt()
        } else 0
    }
}