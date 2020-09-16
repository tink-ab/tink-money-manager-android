package com.tink.pfmui.view

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.widget.ActionMenuView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import com.tink.pfmui.R
import com.tink.pfmui.util.DimensionUtils
import com.tink.pfmui.view.TinkToolbar.Theme.ToolbarTextTheme

// Copied from Base.TextAppearance.MaterialComponents.Button
private const val BUTTON_LETTERSPACING = 0.0892857143f

internal class TinkToolbar : Toolbar {
    private var theme: Theme? = null

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    fun setCustomView(view: View?) {
        var customViewContainer =
            findViewById<ViewGroup>(R.id.tink_toolbar_custom_view_container)
        if (customViewContainer == null) {
            customViewContainer = FrameLayout(context)
            customViewContainer.setId(R.id.tink_toolbar_custom_view_container)
            customViewContainer.setLayoutParams(
                FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
            addView(customViewContainer)
        }
        customViewContainer.removeAllViews()
        customViewContainer.addView(view)
    }

    fun clearCustomView() {
        val customViewContainer =
            findViewById<ViewGroup>(R.id.tink_toolbar_custom_view_container)
        if (customViewContainer != null) {
            customViewContainer.removeAllViews()
            removeView(customViewContainer)
        }
    }

    override fun setNavigationIcon(icon: Drawable?) {
        var icon = icon
        if (icon != null && theme != null) {
            icon = icon.mutate()
            icon.setColorFilter(theme!!.actionButtonTheme.textColor, PorterDuff.Mode.SRC_ATOP)
        }
        super.setNavigationIcon(icon)
    }

    override fun inflateMenu(resId: Int) {
        super.inflateMenu(resId)
        // Re-apply styling after inflating a new menu.
        theme?.actionButtonTheme?.let { findAndStyleMenu(it) }
    }

    fun setTheme(theme: Theme) {
        this.theme = theme
        setTitleTextAppearance(context, R.style.mega)
        val actionButtonTheme = theme.actionButtonTheme
        navigationIcon = navigationIcon
        setBackgroundColor(theme.backgroundColor)
        setTitleTextColor(theme.titleColor)
        elevation = DimensionUtils.getPixelsFromDP(theme.elevationDP, context)
        val overflowIcon = overflowIcon
        overflowIcon!!.setColorFilter(
            actionButtonTheme.textColor,
            PorterDuff.Mode.SRC_ATOP
        )
        setOverflowIcon(overflowIcon)
        for (i in 0 until menu.size()) {
            val item = menu.getItem(i)
            val itemIcon = item.icon
            if (itemIcon != null) {
                itemIcon.setColorFilter(
                    actionButtonTheme.textColor,
                    PorterDuff.Mode.SRC_ATOP
                )
                item.icon = itemIcon
            }
        }
        findAndStyleMenu(actionButtonTheme)
    }

    private fun findAndStyleMenu(actionButtonTheme: ToolbarTextTheme) {
        var menu: ActionMenuView? = null
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child is ActionMenuView) {
                menu = child
            }
        }
        if (menu != null) {
            val finalMenu: ActionMenuView = menu
            ViewCompat.postOnAnimation(
                this
            ) { styleMenu(finalMenu, actionButtonTheme) }
        }
    }

    private fun styleMenu(
        menu: ActionMenuView,
        theme: ToolbarTextTheme
    ) { //style action items
        for (i in 0 until menu.childCount) {
            val child = menu.getChildAt(i)
            if (child is ActionMenuItemView) {
                styleTextView(child, theme)
            }
        }
    }

    private fun styleTextView(
        textView: TextView,
        theme: ToolbarTextTheme
    ) {
        textView.setTextColor(theme.textColor)
        textView.typeface = theme.font
        textView.letterSpacing = BUTTON_LETTERSPACING
        if (theme.shouldChangeTextSize()) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, theme.textSizeInPx)
        }
    }

    interface Theme {
        val backgroundColor: Int
        @get:AttrRes
        val titleColor: Int

        val actionButtonTheme: ToolbarTextTheme
        val elevationDP: Float

        interface ToolbarTextTheme : TinkTextView.Theme {
            fun shouldChangeTextSize(): Boolean
            val textSizeInPx: Float
            val letterSpacing: Float
        }
    }

    companion object {
        const val TAG = "TinkToolbar"
    }
}