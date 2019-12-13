package com.tink.pfmsdk

data class ClientConfiguration constructor(val environment: Environment, val sslCertificate: String)

sealed class Environment(val grpcUrl: String, val port: Int) {

    object Production : Environment(grpcUrl = "main-grpc.production.oxford.tink.se", port = 443)

    class Custom(grpcApiUrl: String, portNumber: Int) :
        Environment(grpcUrl = grpcApiUrl, port = portNumber)
}
