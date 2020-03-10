package com.tink.pfmui.di

import com.tink.pfmui.FinanceOverviewFragment
import dagger.Module
import dagger.Provides
import se.tink.android.di.application.ApplicationScoped

@Module
internal class ContextModule {

    @Provides
    @ApplicationScoped
    fun context(fragment: FinanceOverviewFragment) = fragment.context!!.applicationContext
}
