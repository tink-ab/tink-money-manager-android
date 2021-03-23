package com.tink.moneymanagerui.di

import com.tink.moneymanagerui.FinanceOverviewFragment
import dagger.Module
import dagger.Provides
import se.tink.android.di.application.ApplicationScoped
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScoped

@Module
internal class ContextModule {

    @Provides
    @ApplicationScoped
    fun applicationContext(fragment: FinanceOverviewFragment) = fragment.context!!.applicationContext

    @Provides
    @FragmentScoped
    fun fragmentContext(fragment: FinanceOverviewFragment) = fragment.context!!
}