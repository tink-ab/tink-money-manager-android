package com.tink.pfmsdk.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.tink.pfmsdk.R
import com.tink.pfmsdk.util.DimensionUtils
import com.tink.pfmsdk.util.ScreenUtils

internal class TinkTabs @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TabLayout(context, attrs, defStyleAttr) {

    private var theme: Theme? = null

    fun setTheme(theme: Theme) {
        this.theme = theme
        setBackgroundColor(theme.backgroundColor)
        setSelectedTabIndicatorColor(theme.markerColor)
        val context = context
        elevation = DimensionUtils.getPixelsFromDP(theme.elevation.toFloat(), context)
        setSelectedTabIndicatorHeight(ScreenUtils.dpToPixels(context, 3))
    }

    override fun addTab(
        tab: Tab,
        position: Int,
        setSelected: Boolean
    ) {
        val tinkTextView = TinkTextView(context)
        tinkTextView.setTheme(theme!!.tabsTitle)
        tinkTextView.setTextColor(
            ContextCompat.getColorStateList(
                tinkTextView.context,
                R.color.tink_tab_label_color
            )
        )
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
            val anim = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_top)
            startAnimation(anim)
        }, 0)
    }

    fun show() {
        postDelayed({
            val anim = AnimationUtils.loadAnimation(context, R.anim.slide_in_to_top)
            startAnimation(anim)
        }, 0)
    }

    interface Theme {
        val backgroundColor: Int
        val markerColor: Int
        val elevation: Int
        val tabsTitle: TinkTextView.Theme
    }
}