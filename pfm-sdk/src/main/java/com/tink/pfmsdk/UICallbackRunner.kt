package com.tink.pfmsdk

import android.content.Context
import android.os.Handler
import androidx.annotation.MainThread
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.util.LinkedList

@MainThread
internal class UICallbackRunner(private val lifecycle: Lifecycle, context: Context) : LifecycleObserver {
    private val uiDependantCallbacks = LinkedList<Runnable>()
    private val mainLooper = context.mainLooper
    private val mainThreadHandler = Handler(mainLooper)

    init {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun runUiDependantCallbacks() {
        while (!uiDependantCallbacks.isEmpty()) {
            uiDependantCallbacks.removeLast().run()
        }
    }

    fun runUiDependant(runnable: Runnable?) {
        if (Thread.currentThread() == mainLooper.thread) {
            processCallback(runnable)
        } else {
            mainThreadHandler.post {
                processCallback(runnable)
            }
        }
    }

    private fun processCallback(runnable: Runnable?) {
        runnable?.let {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                it.run()
            } else {
                uiDependantCallbacks.addFirst(it)
            }
        }
    }
}