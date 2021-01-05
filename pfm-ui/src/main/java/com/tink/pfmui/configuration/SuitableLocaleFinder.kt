package com.tink.pfmui.configuration

import android.content.Context
import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import androidx.core.os.LocaleListCompat
import com.tink.pfmui.di.FragmentScoped
import java.util.Locale
import javax.inject.Inject

internal class SuitableLocaleFinder @Inject constructor(
    @FragmentScoped private val context: Context
) {

    private val resources: Resources = context.resources

    private val localesOnDevice: LocaleListCompat
        get() = ConfigurationCompat.getLocales(resources.configuration)

    fun findLocale(): Locale =
        if (!localesOnDevice.isEmpty) {
            localesOnDevice[0]
        } else {
            Locale.getDefault()
        }
}