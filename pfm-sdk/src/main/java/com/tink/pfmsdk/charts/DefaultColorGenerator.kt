package com.tink.pfmsdk.charts

import android.graphics.Color
import androidx.annotation.ColorInt

internal object DefaultColorGenerator : ColorGenerator {

    @ColorInt
    override fun color(@ColorInt baseColor: Int, idx: Int) =
        getColorForAnimationToBackground(baseColor, 0xFFFFFF, idx * 0.11875)

    private fun getColorForAnimationToBackground(
        sectorColor: Int, backgroundColor: Int,
        alphaFactor: Double
    ): Int {
        val sectorColorRed = getRedColor(sectorColor)
        val sectorColorGreen = getGreenColor(sectorColor)
        val sectorColorBlue = getBlueColor(sectorColor)

        val backgroundColorRed = getRedColor(backgroundColor)
        val backgroundColorGreen = getGreenColor(backgroundColor)
        val backgroundColorBlue = getBlueColor(backgroundColor)

        val deltaRed = sectorColorRed - backgroundColorRed
        val deltaGreen = sectorColorGreen - backgroundColorGreen
        val deltaBlue = sectorColorBlue - backgroundColorBlue

        val red = sectorColorRed - (deltaRed * alphaFactor).toInt()
        val green = sectorColorGreen - (deltaGreen * alphaFactor).toInt()
        val blue = sectorColorBlue - (deltaBlue * alphaFactor).toInt()

        return Color.argb(0xFF, red, green, blue)
    }

    private fun getRedColor(color: Int): Int {
        val redMask = 0xFF0000
        return color and redMask shr 16
    }

    private fun getGreenColor(color: Int): Int {
        val greenMask = 0x00FF00
        return color and greenMask shr 8
    }

    private fun getBlueColor(color: Int): Int {
        val blueMask = 0x0000FF
        return color and blueMask
    }

}