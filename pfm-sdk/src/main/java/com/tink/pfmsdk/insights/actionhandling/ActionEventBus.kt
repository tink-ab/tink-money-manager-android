package com.tink.pfmsdk.insights.actionhandling

import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import se.tink.core.models.insights.PerformedInsightAction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActionEventBus @Inject constructor() {
    private val publisherSubject = PublishSubject.create<PerformedInsightAction>()

    fun subscribe(onPerformedAction: (PerformedInsightAction) -> Unit): Disposable =
        publisherSubject.subscribe(onPerformedAction)

    fun postActionPerformed(action: PerformedInsightAction) = publisherSubject.onNext(action)
}