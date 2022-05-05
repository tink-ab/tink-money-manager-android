---
title: com.tink.moneymanagerui.extensions
---
//[moneymanager-ui](../../index.html)/[com.tink.moneymanagerui.extensions](index.html)



# Package com.tink.moneymanagerui.extensions



## Functions


| Name | Summary |
|---|---|
| [clearTime](clear-time.html) | [androidJvm]<br>fun DateTime.[clearTime](clear-time.html)(): DateTime |
| [closeKeyboard](close-keyboard.html) | [androidJvm]<br>fun [EditText](https://developer.android.com/reference/kotlin/android/widget/EditText.html).[closeKeyboard](close-keyboard.html)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [formattedPeriod](formatted-period.html) | [androidJvm]<br>fun [Budget.Periodicity.OneOff](../com.tink.model.budget/-budget/-periodicity/-one-off/index.html).[formattedPeriod](formatted-period.html)(dateUtils: [DateUtils](../se.tink.utils/-date-utils/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>fun [Budget.Periodicity.Recurring](../com.tink.model.budget/-budget/-periodicity/-recurring/index.html).[formattedPeriod](formatted-period.html)(budgetPeriod: [BudgetPeriod](../com.tink.model.budget/index.html#406477269%2FClasslikes%2F1000845458), dateUtils: [DateUtils](../se.tink.utils/-date-utils/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getAbbreviatedMonthName](get-abbreviated-month-name.html) | [androidJvm]<br>fun DateTime.[getAbbreviatedMonthName](get-abbreviated-month-name.html)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getHalfwayPoint](get-halfway-point.html) | [androidJvm]<br>fun [Budget.Period](../com.tink.model.budget/-budget/-period/index.html).[getHalfwayPoint](get-halfway-point.html)(): DateTime<br>fun [Period](../com.tink.model.time/-period/index.html).[getHalfwayPoint](get-halfway-point.html)(): DateTime |
| [getHalfwayUntil](get-halfway-until.html) | [androidJvm]<br>fun DateTime.[getHalfwayUntil](get-halfway-until.html)(end: DateTime): DateTime |
| [getInstant](get-instant.html) | [androidJvm]<br>fun DateTime.[getInstant](get-instant.html)(): Instant |
| [mapState](map-state.html) | [androidJvm]<br>inline fun &lt;[P1](map-state.html), [P2](map-state.html), [R](map-state.html)&gt; [mapState](map-state.html)(p1: [ResponseState](../com.tink.service.network/-response-state/index.html)&lt;[P1](map-state.html)&gt;?, p2: [ResponseState](../com.tink.service.network/-response-state/index.html)&lt;[P2](map-state.html)&gt;?, function: ([P1](map-state.html), [P2](map-state.html)) -&gt; [R](map-state.html)): [ResponseState](../com.tink.service.network/-response-state/index.html)&lt;[R](map-state.html)&gt;<br>inline fun &lt;[P1](map-state.html), [P2](map-state.html), [P3](map-state.html), [R](map-state.html)&gt; [mapState](map-state.html)(p1: [ResponseState](../com.tink.service.network/-response-state/index.html)&lt;[P1](map-state.html)&gt;?, p2: [ResponseState](../com.tink.service.network/-response-state/index.html)&lt;[P2](map-state.html)&gt;?, p3: [ResponseState](../com.tink.service.network/-response-state/index.html)&lt;[P3](map-state.html)&gt;?, function: ([P1](map-state.html), [P2](map-state.html), [P3](map-state.html)) -&gt; [R](map-state.html)): [ResponseState](../com.tink.service.network/-response-state/index.html)&lt;[R](map-state.html)&gt;<br>inline fun &lt;[P1](map-state.html), [P2](map-state.html), [P3](map-state.html), [P4](map-state.html), [R](map-state.html)&gt; [mapState](map-state.html)(p1: [ResponseState](../com.tink.service.network/-response-state/index.html)&lt;[P1](map-state.html)&gt;?, p2: [ResponseState](../com.tink.service.network/-response-state/index.html)&lt;[P2](map-state.html)&gt;?, p3: [ResponseState](../com.tink.service.network/-response-state/index.html)&lt;[P3](map-state.html)&gt;?, p4: [ResponseState](../com.tink.service.network/-response-state/index.html)&lt;[P4](map-state.html)&gt;?, function: ([P1](map-state.html), [P2](map-state.html), [P3](map-state.html), [P4](map-state.html)) -&gt; [R](map-state.html)): [ResponseState](../com.tink.service.network/-response-state/index.html)&lt;[R](map-state.html)&gt; |
| [minusMonths](minus-months.html) | [androidJvm]<br>fun Instant.[minusMonths](minus-months.html)(months: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): Instant |
| [openKeyboard](open-keyboard.html) | [androidJvm]<br>fun [EditText](https://developer.android.com/reference/kotlin/android/widget/EditText.html).[openKeyboard](open-keyboard.html)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [textChangedObserver](text-changed-observer.html) | [androidJvm]<br>fun [EditText](https://developer.android.com/reference/kotlin/android/widget/EditText.html).[textChangedObserver](text-changed-observer.html)(): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [toHistoricIntervalLabel](to-historic-interval-label.html) | [androidJvm]<br>fun [Budget.Period](../com.tink.model.budget/-budget/-period/index.html).[toHistoricIntervalLabel](to-historic-interval-label.html)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), dateUtils: [DateUtils](../se.tink.utils/-date-utils/index.html), periodicity: [Budget.Periodicity.Recurring](../com.tink.model.budget/-budget/-periodicity/-recurring/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [toPeriodChartLabel](to-period-chart-label.html) | [androidJvm]<br>fun [Budget.Period](../com.tink.model.budget/-budget/-period/index.html).[toPeriodChartLabel](to-period-chart-label.html)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), dateUtils: [DateUtils](../se.tink.utils/-date-utils/index.html), periodicity: [Budget.Periodicity.Recurring](../com.tink.model.budget/-budget/-periodicity/-recurring/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [toStartOfLocalDate](to-start-of-local-date.html) | [androidJvm]<br>fun DateTime.[toStartOfLocalDate](to-start-of-local-date.html)(): LocalDate |
| [withUtcTimeRetainZone](with-utc-time-retain-zone.html) | [androidJvm]<br>fun DateTime.[withUtcTimeRetainZone](with-utc-time-retain-zone.html)(): DateTime<br>Switches the time to what it would be in UTC timezone, but keeps our timezone. |


## Properties


| Name | Summary |
|---|---|
| [totalMonths](total-months.html) | [androidJvm]<br>val Period.[totalMonths](total-months.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

