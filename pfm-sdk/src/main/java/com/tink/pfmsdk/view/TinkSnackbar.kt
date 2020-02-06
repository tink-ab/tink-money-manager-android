package com.tink.pfmsdk.view

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.SnackbarContentLayout
import com.tink.pfmsdk.R
import com.tink.pfmsdk.util.DimensionUtils
import kotlin.math.roundToInt

internal class TinkSnackbar private constructor(
    private val wrappedSnackbar: Snackbar,
    private val theme: Theme
) {
    private val snackbarContentLayout: SnackbarContentLayout?
    private val context: Context = wrappedSnackbar.context
    private var loadingIndicator: ProgressBar? = null

    init {
        snackbarContentLayout = findRecursive(wrappedSnackbar.view)
        wrappedSnackbar.view.setBackgroundColor(theme.backgroundColor)
        val messageView =
            snackbarContentLayout!!.findViewById<TextView>(R.id.snackbar_text)
        val actionView =
            snackbarContentLayout.findViewById<TextView>(R.id.snackbar_action)
        styleText(messageView, theme.textTheme)
        styleText(actionView, theme.buttonTheme)
    }

    fun setLoading(loading: Boolean) {
        if (!loading) {
            if (loadingIndicator != null) {
                loadingIndicator!!.visibility = View.GONE
            }
        } else {
            if (loadingIndicator == null) {
                initLoader()
            }
            loadingIndicator!!.visibility = View.VISIBLE
        }
    }

    fun setAction(text: String?, listener: View.OnClickListener?) {
        wrappedSnackbar.setAction(text, listener)
    }

    fun setIcon(drawable: Drawable?) {
        val icon = ImageView(context)
        icon.setImageDrawable(drawable)
        val iconSize = Math.round(
            DimensionUtils.getPixelsFromDP(
                ICON_SIZE_DP,
                context
            )
        )
        val iconLayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams(iconSize, iconSize)
            )
        iconLayoutParams.gravity = Gravity.CENTER_VERTICAL
        icon.layoutParams = iconLayoutParams
        snackbarContentLayout!!.addView(icon)
    }

    fun show() {
        wrappedSnackbar.show()
    }

    private fun initLoader() {
        loadingIndicator = ProgressBar(context)
        val progressSize = DimensionUtils.getPixelsFromDP(
            LOADER_SIZE_DP,
            context
        ).roundToInt()
        val progressLayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams(progressSize, progressSize)
            )
        progressLayoutParams.gravity = Gravity.CENTER_VERTICAL
        loadingIndicator!!.layoutParams = progressLayoutParams
        loadingIndicator!!.indeterminateDrawable.setColorFilter(
            theme.loadingIndicatorColor,
            PorterDuff.Mode.SRC_IN
        )
        loadingIndicator!!.visibility = View.GONE
        snackbarContentLayout!!.addView(loadingIndicator)
    }

    fun dismiss() {
        wrappedSnackbar.dismiss()
    }

    interface Theme {
        val textTheme: TinkTextView.Theme
        val buttonTheme: TinkTextView.Theme
        val backgroundColor: Int
        val loadingIndicatorColor: Int

        companion object {
            const val MESSAGE_THEME = "message_theme"
            const val ERROR_THEME = "error_theme"
        }
    }

    companion object {
        private const val LOADER_SIZE_DP = 35f
        private const val ICON_SIZE_DP = 35f
        fun make(
            view: View,
            text: String?,
            duration: Int,
            theme: Theme
        ): TinkSnackbar {
            val snackbar = Snackbar.make(view, text!!, duration)
            // TODO: PFMSDK: How can he handle this with the app's bottom navigation view?
            /*val lp = snackbar.view.layoutParams
            boolean isBottomNavigationBar = view.findViewById(R.id.bottomNavigation) != null
			&& lp instanceof CoordinatorLayout.LayoutParams;
		    if (isBottomNavigationBar) {
                CoordinatorLayout.LayoutParams clp = (CoordinatorLayout.LayoutParams) lp;
                clp.setAnchorId(R.id.bottomNavigation);
                clp.anchorGravity = Gravity.TOP;
                clp.gravity = Gravity.TOP;
                snackbar.getView().setLayoutParams(clp);
		    }*/
            val instance = TinkSnackbar(snackbar, theme)
            val snackbarView: View? =
                findRecursive(snackbar.view)
            if (snackbarView != null) {
                snackbarView.minimumHeight =
                    view.resources.getDimension(R.dimen.snackbar_height).toInt()
            }
            return instance
        }

        private fun findRecursive(root: View): SnackbarContentLayout? {
            if (root is SnackbarContentLayout) {
                return root
            } else if (root is ViewGroup) {
                for (i in 0 until root.childCount) {
                    val child = root.getChildAt(i)
                    val foundInChildren =
                        findRecursive(child)
                    if (foundInChildren != null) {
                        return foundInChildren
                    }
                }
            }
            return null
        }

        private fun styleText(
            textView: TextView,
            theme: TinkTextView.Theme
        ) {
            textView.typeface = theme.font
            textView.setTextColor(theme.textColor)
        }
    }
}