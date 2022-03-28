package com.tink.moneymanagerui.testutil

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class PathDispatcher(
    private val pathToResponseMap: MutableMap<String, MockResponse> = mutableMapOf()
) : Dispatcher() {

    private val defaultResponse = MockResponse().setResponseCode(500).setBody("{}")

    fun addResponse(path: String, mockResponse: MockResponse) {
        pathToResponseMap[path] = mockResponse
    }

    fun addResponses(responses: Map<String, MockResponse>) {
        pathToResponseMap.putAll(responses)
    }

    fun clearResponses() {
        pathToResponseMap.clear()
    }

    override fun dispatch(request: RecordedRequest): MockResponse =
        pathToResponseMap[request.path] ?: defaultResponse
}
