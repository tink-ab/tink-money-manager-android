package com.tink.moneymanagerui.testutil

import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

val requestsTakenMap = mutableMapOf<MockWebServer, Int>()

fun MockWebServer.getLatestRequest(): RecordedRequest? {
    val requests = mutableListOf<RecordedRequest>()

    val requestsTaken = requestsTakenMap.getOrDefault(this, 0)
    val requestsToTake = requestCount - requestsTaken
    (1..requestsToTake).forEach { _ ->
        requests.add(takeRequest())
    }
    requestsTakenMap[this] = requestsTakenMap.getOrDefault(this, 0) + requestsToTake
    return requests.lastOrNull()
}
