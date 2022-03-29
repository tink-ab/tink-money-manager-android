package com.tink.moneymanagerui.configuration

/**
 * Interface definition for a callback to be invoked when a back button has been pressed.
 */
fun interface OnBackPressedListener {
    /**
     * Called when back button has been pressed, in toolbar or Android OS back button.
     *
     */
    fun onBackPressed()
}
