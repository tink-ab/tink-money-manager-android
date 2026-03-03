---
title: refreshDelayed
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.repository](../index.html)/[StatisticsRepository](index.html)/[refreshDelayed](refresh-delayed.html)



# refreshDelayed



[androidJvm]\
suspend fun [refreshDelayed](refresh-delayed.html)(delayMills: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) = 2000)



Refreshing imediately might cause problems since the statistics api take some time to update. This refreshes after a given delay to ensure that we fetch updated data.




