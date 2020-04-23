package se.tink.android

import java.util.concurrent.Executor

interface AppExecutors {
    val networkExecutor: Executor
    val backgroundExecutor: Executor
    val mainThreadExecutor: Executor
}
