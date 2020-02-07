package com.tink.pfmsdk.charts.models

import android.graphics.Typeface

internal class Labels {
    var texts: List<String>? = null
    var typeface1: Typeface? = null
        private set
    var textColor1 = 0
        private set
    var textSize1 = 0f
        private set
    var characterSpacing1 = 0f
        private set
    var typeface2: Typeface? = null
        private set
    var textColor2 = 0
        private set
    var textSize2 = 0f
        private set
    var characterSpacing2 = 0f
        private set
    var typeface3: Typeface? = null
        private set
    var textColor3 = 0
        private set
    var textSize3 = 0f
        private set
    var characterSpacing3 = 0f
        private set

    fun setText1Style(
        typeface1: Typeface?, textColor1: Int, textSize1: Float,
        characterSpacing1: Float
    ) {
        this.typeface1 = typeface1
        this.textColor1 = textColor1
        this.textSize1 = textSize1
        this.characterSpacing1 = characterSpacing1
    }

    fun setText2Style(
        typeface2: Typeface?, textColor2: Int, textSize2: Float,
        characterSpacing2: Float
    ) {
        this.typeface2 = typeface2
        this.textColor2 = textColor2
        this.textSize2 = textSize2
        this.characterSpacing2 = characterSpacing2
    }

    fun setText3Style(
        typeface3: Typeface?, textColor3: Int, textSize3: Float,
        characterSpacing3: Float
    ) {
        this.typeface3 = typeface3
        this.textColor3 = textColor3
        this.textSize3 = textSize3
        this.characterSpacing3 = characterSpacing3
    }
}