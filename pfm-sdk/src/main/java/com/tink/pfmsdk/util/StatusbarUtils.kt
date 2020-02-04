package com.tink.pfmsdk.util

import android.animation.ObjectAnimator
import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager

internal object StatusbarUtils {

    @JvmStatic
    fun updateStatusBar(activity: Activity, colorResource: Int, lightStatusBar: Boolean) {
        val window = activity.window ?: throw IllegalStateException("Cannot obtain window")

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        ObjectAnimator.ofArgb(window, "statusBarColor", colorResource).apply {
            duration = 1000
            start()
        }
        //TODO: change dark <--> light in the middle of the animation
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var uiVisibility = window.decorView.systemUiVisibility

            uiVisibility = if (lightStatusBar) {
                uiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                uiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
            window.decorView.systemUiVisibility = uiVisibility
        }
    }
}
