package com.tink.moneymanagerui.view

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.widget.ActionMenuView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.util.DimensionUtils
import com.tink.moneymanagerui.util.FontUtils
import se.tink.commons.extensions.getColorFromAttr

// Copied from Base.TextAppearance.MaterialComponents.Button
private const val BUTTON_LETTERSPACING = 0.0892857143f

internal class TinkToolbar : Toolbar {

    private val colorBackground = context.getColorFromAttr(R.attr.tink_colorPrimary)
    private val colorOnBackground = context.getColorFromAttr(R.attr.tink_colorOnPrimary)
    private val typeface = FontUtils.getTypeface(FontUtils.BOLD_FONT, context)
    private val elevationInDp = 4.0f

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    init {
        applyCustomStyle()
    }

    override fun setNavigationIcon(icon: Drawable?) {
        var navIcon = icon
        if (navIcon != null) {
            navIcon = navIcon.mutate()
            navIcon.setTint(colorOnBackground)
            navIcon.setTintMode(PorterDuff.Mode.SRC_ATOP)
        }
        super.setNavigationIcon(navIcon)
    }

    override fun inflateMenu(resId: Int) {
        super.inflateMenu(resId)
        // Re-apply styling after inflating a new menu.
        findAndStyleMenu()
    }

    private fun applyCustomStyle() {
        setTitleTextAppearance(context, R.style.tink_mega)
        navigationIcon = navigationIcon
        setBackgroundColor(colorBackground)
        setTitleTextColor(colorOnBackground)
        elevation = DimensionUtils.getPixelsFromDP(elevationInDp, context)
        val overflowIcon = overflowIcon
        overflowIcon!!.setColorFilter(
            colorOnBackground,
            PorterDuff.Mode.SRC_ATOP
        )
        setOverflowIcon(overflowIcon)
        for (i in 0 until menu.size()) {
            val item = menu.getItem(i)
            val itemIcon = item.icon
            if (itemIcon != null) {
                itemIcon.setColorFilter(
                    colorOnBackground,
                    PorterDuff.Mode.SRC_ATOP
                )
                item.icon = itemIcon
            }
        }
        findAndStyleMenu()
    }

    private fun findAndStyleMenu() {
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
            ) { styleMenu(finalMenu) }
        }
    }

    private fun styleMenu(menu: ActionMenuView) {
        // style action items
        for (i in 0 until menu.childCount) {
            val child = menu.getChildAt(i)
            if (child is ActionMenuItemView) {
                styleTextView(child)
            }
        }
    }

    private fun styleTextView(
        textView: TextView
    ) {
        textView.setTextColor(colorOnBackground)
        textView.typeface = typeface
        textView.letterSpacing = BUTTON_LETTERSPACING
    }

    fun setCustomView(view: View?) {
        var customViewContainer = findViewById<ViewGroup>(R.id.tink_toolbar_custom_view_container)
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
        val customViewContainer = findViewById<ViewGroup>(R.id.tink_toolbar_custom_view_container)
        if (customViewContainer != null) {
            customViewContainer.removeAllViews()
            removeView(customViewContainer)
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
}
