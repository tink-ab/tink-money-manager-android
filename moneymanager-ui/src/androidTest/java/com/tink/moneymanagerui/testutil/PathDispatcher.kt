package com.tink.moneymanagerui.testutil

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class PathDispatcher(
    private val pathToResponseMap: MutableMap<String, MockResponse> = mutableMapOf(),
    private val pathToBodyFilterMap: MutableMap<String, (String) -> Boolean> = mutableMapOf()
): Dispatcher() {

    private val defaultResponse = MockResponse().setResponseCode(500).setBody("{}")

    fun addResponse(path: String, mockResponse: MockResponse) {
        pathToResponseMap[path] = mockResponse
    }

    fun addResponses(responses: Map<String, MockResponse>) {
        pathToResponseMap.putAll(responses)
    }

    fun addFilter(path: String, filter: (String) -> Boolean) {
        pathToBodyFilterMap[path] = filter
    }

    fun addFilters(filters: Map<String, (String) -> Boolean>) {
        pathToBodyFilterMap.putAll(filters)
    }

    fun clearResponses() {
        pathToResponseMap.clear()
        pathToBodyFilterMap.clear()
    }

    override fun dispatch(request: RecordedRequest): MockResponse {

        val hasResponse = pathToResponseMap[request.path] != null
        val hasFilter = pathToBodyFilterMap.containsKey(request.path)
        val passedFilter = pathToBodyFilterMap[request.path]?.let {
                filter -> filter(request.body.readUtf8())
        } ?: false


        return if (hasResponse && (hasFilter && passedFilter) || (hasResponse && !hasFilter)) {
            pathToResponseMap[request.path] ?: defaultResponse
        } else {
            defaultResponse
        }
    }

}