package com.tink.pfmsdk

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize

sealed class OverviewFeature {
    @Parcelize
    class Statistics(val statisticTypes: List<StatisticType>) : OverviewFeature(), Parcelable

    object LatestTransactions : OverviewFeature()
}

enum class StatisticType {
    EXPENSES,
    INCOME
}

@Parcelize
data class OverviewFeatures(val features: List<OverviewFeature>) : Parcelable {

    companion object : Parceler<OverviewFeatures> {

        private fun readFromParcel(parcel: Parcel): OverviewFeatures {
            return OverviewFeatures(
                features = mutableListOf<OverviewFeature>().also {
                    parcel.readList(
                        it as List<OverviewFeature>,
                        OverviewFeature::class.java.classLoader
                    )
                }
            )
        }

        override fun OverviewFeatures.write(parcel: Parcel, flags: Int) {
            parcel.apply {
                writeList(features)
            }
        }

        override fun create(parcel: Parcel): OverviewFeatures {
            return readFromParcel(parcel)
        }

        val ALL =
            OverviewFeatures(
                listOf(
                    OverviewFeature.Statistics(
                        listOf(
                            StatisticType.EXPENSES,
                            StatisticType.INCOME
                        )
                    ),
                    OverviewFeature.LatestTransactions
                )
            )
    }
}