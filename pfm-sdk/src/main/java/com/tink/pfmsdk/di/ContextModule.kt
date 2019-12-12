package com.tink.pfmsdk.di

import com.tink.pfmsdk.FinanceOverviewFragment
import dagger.Module
import dagger.Provides
import se.tink.android.di.application.ApplicationScoped

@Module
class ContextModule {

    @Provides
    @ApplicationScoped
    fun context(fragment: FinanceOverviewFragment) = fragment.context!!.applicationContext
}
