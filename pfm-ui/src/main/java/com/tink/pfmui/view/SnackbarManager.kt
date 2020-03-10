package com.tink.pfmui.view

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import android.content.Context
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import android.view.View
import com.tink.pfmui.R
import com.tink.pfmui.theme.TinkDefaultSnackbarTheme
import com.tink.pfmui.theme.TinkErrorSnackbarTheme
import se.tink.repository.TinkNetworkError
import se.tink.repository.TinkNetworkError.ErrorCode
import javax.inject.Inject
import javax.inject.Singleton


private interface SnackbarHandler {
    fun display(message: String, duration: Int, theme: TinkSnackbar.Theme)
    fun displayLoader(message: String, theme: TinkSnackbar.Theme): TinkSnackbar
}

@Singleton
internal class SnackbarManager @Inject constructor() {
    private val handlers = mutableListOf<SnackbarHandler>()

    fun displayError(error: TinkNetworkError?, context: Context?) {
        context?.let { displayError(error, it) }
    }

    fun displayError(error: TinkNetworkError?, context: Context,
                     theme: TinkSnackbar.Theme = TinkErrorSnackbarTheme(context)
    ) {
        val message = when {
            error?.statusCode?.value() == 408 -> context.getString(R.string.snackbar_utils_error_status_code_408)
            error != null && error.errorCode != ErrorCode.UNHANDLED_ERROR ->
                error.backendMessage ?: context.getString(R.string.snackbar_utils_error_default)
            else -> context.getString(R.string.snackbar_utils_error_default)
        }
        displayError(message, theme)
    }

    fun displayError(@StringRes message: Int, context: Context?) {
        context?.let { displayError(message, it) }
    }

    fun displayError(@StringRes message: Int, context: Context,
                     theme: TinkSnackbar.Theme = TinkErrorSnackbarTheme(context)) =
            displayError(context.getString(message), theme)

    fun displayError(message: String, context: Context?) {
        context?.let { displayError(message, it) }
    }

    fun displayError(message: String, context: Context,
                     theme: TinkSnackbar.Theme = TinkErrorSnackbarTheme(context)) =
            display(message, Snackbar.LENGTH_LONG, theme)

    fun displayError(message: String, theme: TinkSnackbar.Theme) =
            display(message, Snackbar.LENGTH_LONG, theme)

    fun displayMessage(message: String, duration: Int, context: Context?) {
        context?.let { displayMessage(message, duration, it) }
    }

    fun displayMessage(message: String, duration: Int, context: Context,
                       theme: TinkSnackbar.Theme = TinkDefaultSnackbarTheme(context)) =
            display(message, duration, theme)

    fun displayMessage(message: String, duration: Int, theme: TinkSnackbar.Theme) =
            display(message, duration, theme)

    private fun display(message: String, duration: Int, theme: TinkSnackbar.Theme) {
        handlers.lastOrNull()?.display(message, duration, theme)
    }

    @JvmOverloads
    fun displayLoader(@StringRes message: Int, context: Context, theme: TinkSnackbar.Theme = TinkDefaultSnackbarTheme(context)): TinkSnackbar? =
            handlers.lastOrNull()?.displayLoader(context.getString(message), theme)

    private fun register(handler: SnackbarHandler) = handlers.add(handler)
    private fun unregister(handler: SnackbarHandler) = handlers.remove(handler)

    fun register(view: View, lifecycle: Lifecycle) {
        lifecycle.addObserver(object : DefaultLifecycleObserver,
            SnackbarHandler {

            override fun onStart(owner: LifecycleOwner) {
                register(this)
            }

            override fun onStop(owner: LifecycleOwner) {
                unregister(this)
            }

            override fun display(message: String, duration: Int, theme: TinkSnackbar.Theme) =
                    TinkSnackbar.make(view, message, duration, theme).show()

            override fun displayLoader(message: String, theme: TinkSnackbar.Theme): TinkSnackbar =
                    TinkSnackbar.make(
                        view,
                        message,
                        Snackbar.LENGTH_INDEFINITE,
                        theme
                    ).apply {
                        setLoading(true)
                        show()
                    }
        })
    }
}