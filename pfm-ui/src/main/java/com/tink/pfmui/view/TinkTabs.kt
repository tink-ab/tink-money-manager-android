package com.tink.pfmui.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import com.google.android.material.tabs.TabLayout
import com.tink.pfmui.R
import com.tink.pfmui.util.ColorsUtils
import com.tink.pfmui.util.DimensionUtils
import com.tink.pfmui.util.ScreenUtils
import se.tink.commons.extensions.getColorFromAttr

internal class TinkTabs @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TabLayout(context, attrs, defStyleAttr) {

    private val colorBackground = context.getColorFromAttr(R.attr.tink_colorPrimary)
    private val colorOnBackground = context.getColorFromAttr(R.attr.tink_colorOnPrimary)

    private fun normalTabColor(): Int = ColorsUtils.adjustAlpha(colorOnBackground, 0.4f)
    private fun selectedTabColor(): Int = colorOnBackground

    init {
        setBackgroundColor(colorBackground)
        setSelectedTabIndicatorColor(colorOnBackground)
        elevation = DimensionUtils.getPixelsFromDP(4f, context)
        setSelectedTabIndicatorHeight(ScreenUtils.dpToPixels(context, 3))
    }

    override fun addTab(
        tab: Tab,
        position: Int,
        setSelected: Boolean
    ) {
        val tinkTextView = TinkTextView(context)
        tinkTextView.setTheme(
            object: NanoTitle(context) {
                override var textColor = colorOnBackground
                override val spacing = DimensionUtils.getEmFromDp(2f)
                override fun toUpperCase() = true
            }
        )
        val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_selected),
                intArrayOf()
            ),
            intArrayOf(
                selectedTabColor(),
                normalTabColor()
            )
        )
        tinkTextView.setTextColor(colorStateList)
        tinkTextView.text = tab.text
        tinkTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        tab.customView = tinkTextView
        super.addTab(tab, position, setSelected)
    }

    fun updateNameOnFirstTab(name: String?) {
        val tinkTextView = getTabAt(0)!!.customView as TinkTextView?
        tinkTextView!!.text = name
        getTabAt(0)!!.customView = tinkTextView
    }

    fun hide() {
        postDelayed({
            val anim = AnimationUtils.loadAnimation(context, R.anim.tink_slide_in_from_top)
            startAnimation(anim)
        }, 0)
    }

    fun show() {
        postDelayed({
            val anim = AnimationUtils.loadAnimation(context, R.anim.tink_slide_in_to_top)
            startAnimation(anim)
        }, 0)
    }
}