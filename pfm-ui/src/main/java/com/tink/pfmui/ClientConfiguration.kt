package com.tink.pfmui

/**
 * Represents the client configuration data users need to provide while creating a new instance of [FinanceOverviewFragment].
 *
 * @param environment The [Environment] object for the endpoint to be used
 * @param sslCertificate The SSL certificate for the environment
 */
data class ClientConfiguration constructor(val environment: Environment, val sslCertificate: String)

/**
 * Represents the endpoint to be used to fetch the data.
 *
 * @param grpcUrl The grpc endpoint url to be used
 * @param port The port number
 */
sealed class Environment(val grpcUrl: String, val port: Int) {
    /**
     * Represents the environment that can be used if the data is fetched through Tink's production grpc endpoint.
     */
    object Production : Environment(grpcUrl = "main-grpc.production.oxford.tink.se", port = 443)

    /**
     * Represents a custom environment the user can set up for fetching the data.
     */
    class Custom(grpcApiUrl: String, portNumber: Int) :
        Environment(grpcUrl = grpcApiUrl, port = portNumber)
}
