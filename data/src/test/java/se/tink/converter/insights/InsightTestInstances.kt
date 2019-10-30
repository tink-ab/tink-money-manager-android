package se.tink.converter.insights

import com.google.protobuf.Timestamp
import se.tink.converter.budgets.BudgetPeriodDTO
import se.tink.grpc.v1.models.CurrencyDenominatedAmount
import se.tink.grpc.v1.models.ExactNumber
import se.tink.grpc.v1.models.InsightAction
import se.tink.grpc.v1.models.InsightData

class InsightTestInstances {

    companion object {
        val fullTestDTO = InsightDTO.newBuilder().run {
            id = "489ru3940hjf043m"
            type = "BUDGET_OVERSPENT"
            title = "Cool budget streak"
            description = "you almost made it"
            createdTime = Timestamp.newBuilder().run {
                seconds = 1551790965
                nanos = 0
                build()
            }
            setData(
                InsightData
                    .newBuilder()
                    .setBudgetOverspent(
                        InsightData.Data.BudgetOverspent
                            .newBuilder()
                            .setBudgetId("budgetId-3")
                            .setBudgetPeriod(
                                BudgetPeriodDTO
                                    .newBuilder()
                                    .setBudgetAmount(
                                        CurrencyDenominatedAmount
                                            .newBuilder()
                                            .setCurrencyCode("SEK")
                                            .setValue(
                                                ExactNumber
                                                    .newBuilder()
                                                    .setUnscaledValue(10000)
                                                    .setScale(1)
                                            )
                                    )
                            )
                    )
            )
            addActions(
                InsightAction.newBuilder()
                    .setLabel("Action")
                    .setViewBudget(
                        InsightAction.Data.ViewBudget
                            .newBuilder()
                            .setBudgetId("budgetId-3")
                            .setBudgetPeriodStartTime(
                                Timestamp.newBuilder().setSeconds(1221).setNanos(23232)
                            )
                    )
                    .build()
            )
            build()
        }

    }

}
