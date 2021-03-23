package com.tink.moneymanagerui.insights.actionhandling

import com.tink.annotations.PfmScope
import com.tink.model.insights.PerformedInsightAction
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@PfmScope
class ActionEventBus @Inject constructor() {
    private val publisherSubject = PublishSubject.create<PerformedInsightAction>()

    fun subscribe(onPerformedAction: (PerformedInsightAction) -> Unit): Disposable =
        publisherSubject.subscribe(onPerformedAction)

    fun postActionPerformed(action: PerformedInsightAction) = publisherSubject.onNext(action)
}