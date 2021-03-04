package com.tink.moneymanagerui

import android.content.Context
import android.os.Handler
import se.tink.android.AppExecutors
import se.tink.android.di.application.ApplicationScoped
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

internal class AppExecutorsDefaultImpl @Inject constructor(
    @ApplicationScoped context: Context
) : AppExecutors {
    private val mainThreadHandler = Handler(context.mainLooper)

    override val networkExecutor: Executor = Executors.newSingleThreadExecutor()

    override val backgroundExecutor: Executor = Executors.newCachedThreadPool()

    override val mainThreadExecutor = Executor { mainThreadHandler.post(it) }
}