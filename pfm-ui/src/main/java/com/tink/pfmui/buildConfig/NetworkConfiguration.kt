package com.tink.pfmui.buildConfig

data class NetworkConfiguration(val serverAddress: String,
                                val clientKey: String,
                                val sslKey: String,
                                val port: Int,
                                val useSsl: Boolean = true)
