package com.tink.pfmsdk.di

import android.content.Context
import com.tink.pfmsdk.ClientDataStorage
import com.tink.pfmsdk.NetworkConfigSingleton
import com.tink.pfmsdk.buildConfig.NetworkConfiguration
import dagger.Module
import dagger.Provides
import io.grpc.Channel
import io.grpc.ClientInterceptor
import io.grpc.ClientInterceptors
import io.grpc.okhttp.OkHttpChannelBuilder
import se.tink.android.di.application.ApplicationScoped
import se.tink.repository.TLSHelper
import se.tink.repository.service.ChannelConnector
import se.tink.repository.service.HeaderClientInterceptor
import se.tink.tink_android_data.BuildConfig
import java.io.ByteArrayInputStream
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

//    @Provides
//    @Singleton
//    @Named("staging")
//    fun provideStagingNetworkConfig(): NetworkConfiguration {
//        return NetworkConfiguration(
//            serverAddress = "main-grpc.staging.oxford.tink.se",
//            port = 443,
//            clientKey = "914e09ef44c4444abb8cc6f40f10d608",
//            sslKey = "-----BEGIN CERTIFICATE-----\n" +
//                    "MIIDSjCCAjKgAwIBAgIQRK+wgNajJ7qJMDmGLvhAazANBgkqhkiG9w0BAQUFADA/\n" +
//                    "MSQwIgYDVQQKExtEaWdpdGFsIFNpZ25hdHVyZSBUcnVzdCBDby4xFzAVBgNVBAMT\n" +
//                    "DkRTVCBSb290IENBIFgzMB4XDTAwMDkzMDIxMTIxOVoXDTIxMDkzMDE0MDExNVow\n" +
//                    "PzEkMCIGA1UEChMbRGlnaXRhbCBTaWduYXR1cmUgVHJ1c3QgQ28uMRcwFQYDVQQD\n" +
//                    "Ew5EU1QgUm9vdCBDQSBYMzCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEB\n" +
//                    "AN+v6ZdQCINXtMxiZfaQguzH0yxrMMpb7NnDfcdAwRgUi+DoM3ZJKuM/IUmTrE4O\n" +
//                    "rz5Iy2Xu/NMhD2XSKtkyj4zl93ewEnu1lcCJo6m67XMuegwGMoOifooUMM0RoOEq\n" +
//                    "OLl5CjH9UL2AZd+3UWODyOKIYepLYYHsUmu5ouJLGiifSKOeDNoJjj4XLh7dIN9b\n" +
//                    "xiqKqy69cK3FCxolkHRyxXtqqzTWMIn/5WgTe1QLyNau7Fqckh49ZLOMxt+/yUFw\n" +
//                    "7BZy1SbsOFU5Q9D8/RhcQPGX69Wam40dutolucbY38EVAjqr2m7xPi71XAicPNaD\n" +
//                    "aeQQmxkqtilX4+U9m5/wAl0CAwEAAaNCMEAwDwYDVR0TAQH/BAUwAwEB/zAOBgNV\n" +
//                    "HQ8BAf8EBAMCAQYwHQYDVR0OBBYEFMSnsaR7LHH62+FLkHX/xBVghYkQMA0GCSqG\n" +
//                    "SIb3DQEBBQUAA4IBAQCjGiybFwBcqR7uKGY3Or+Dxz9LwwmglSBd49lZRNI+DT69\n" +
//                    "ikugdB/OEIKcdBodfpga3csTS7MgROSR6cz8faXbauX+5v3gTt23ADq1cEmv8uXr\n" +
//                    "AvHRAosZy5Q6XkjEGB5YGV8eAlrwDPGxrancWYaLbumR9YbK+rlmM6pZW87ipxZz\n" +
//                    "R8srzJmwN0jP41ZL9c8PDHIyh8bwRLtTcm1D9SZImlJnt1ir/md2cXjbDaJWFBM5\n" +
//                    "JDGFoqgCWjBH4d1QB7wCCZAA62RjYJsWvIjJEubSfZGL+T0yjWW06XyxV3bqxbYo\n" +
//                    "Ob8VZRzI9neWagqNdwvYkQsEjgfbKbYK7p2CNTUQ\n" +
//                    "-----END CERTIFICATE-----"
//        )
//    }
//
//    @Provides
//    @Singleton
//    @Named("production")
//    fun provideProductionNetworkConfig(): NetworkConfiguration {
//        return NetworkConfiguration(
//            serverAddress = "main-grpc.production.oxford.tink.se",
//            port = 443,
//            clientKey = "914e09ef44c4444abb8cc6f40f10d608",
//            sslKey = "-----BEGIN CERTIFICATE-----\n"
//                    + "MIIEkjCCA3qgAwIBAgIQCgFBQgAAAVOFc2oLheynCDANBgkqhkiG9w0BAQsFADA/\n"
//                    + "MSQwIgYDVQQKExtEaWdpdGFsIFNpZ25hdHVyZSBUcnVzdCBDby4xFzAVBgNVBAMT\n"
//                    + "DkRTVCBSb290IENBIFgzMB4XDTE2MDMxNzE2NDA0NloXDTIxMDMxNzE2NDA0Nlow\n"
//                    + "SjELMAkGA1UEBhMCVVMxFjAUBgNVBAoTDUxldCdzIEVuY3J5cHQxIzAhBgNVBAMT\n"
//                    + "GkxldCdzIEVuY3J5cHQgQXV0aG9yaXR5IFgzMIIBIjANBgkqhkiG9w0BAQEFAAOC\n"
//                    + "AQ8AMIIBCgKCAQEAnNMM8FrlLke3cl03g7NoYzDq1zUmGSXhvb418XCSL7e4S0EF\n"
//                    + "q6meNQhY7LEqxGiHC6PjdeTm86dicbp5gWAf15Gan/PQeGdxyGkOlZHP/uaZ6WA8\n"
//                    + "SMx+yk13EiSdRxta67nsHjcAHJyse6cF6s5K671B5TaYucv9bTyWaN8jKkKQDIZ0\n"
//                    + "Z8h/pZq4UmEUEz9l6YKHy9v6Dlb2honzhT+Xhq+w3Brvaw2VFn3EK6BlspkENnWA\n"
//                    + "a6xK8xuQSXgvopZPKiAlKQTGdMDQMc2PMTiVFrqoM7hD8bEfwzB/onkxEz0tNvjj\n"
//                    + "/PIzark5McWvxI0NHWQWM6r6hCm21AvA2H3DkwIDAQABo4IBfTCCAXkwEgYDVR0T\n"
//                    + "AQH/BAgwBgEB/wIBADAOBgNVHQ8BAf8EBAMCAYYwfwYIKwYBBQUHAQEEczBxMDIG\n"
//                    + "CCsGAQUFBzABhiZodHRwOi8vaXNyZy50cnVzdGlkLm9jc3AuaWRlbnRydXN0LmNv\n"
//                    + "bTA7BggrBgEFBQcwAoYvaHR0cDovL2FwcHMuaWRlbnRydXN0LmNvbS9yb290cy9k\n"
//                    + "c3Ryb290Y2F4My5wN2MwHwYDVR0jBBgwFoAUxKexpHsscfrb4UuQdf/EFWCFiRAw\n"
//                    + "VAYDVR0gBE0wSzAIBgZngQwBAgEwPwYLKwYBBAGC3xMBAQEwMDAuBggrBgEFBQcC\n"
//                    + "ARYiaHR0cDovL2Nwcy5yb290LXgxLmxldHNlbmNyeXB0Lm9yZzA8BgNVHR8ENTAz\n"
//                    + "MDGgL6AthitodHRwOi8vY3JsLmlkZW50cnVzdC5jb20vRFNUUk9PVENBWDNDUkwu\n"
//                    + "Y3JsMB0GA1UdDgQWBBSoSmpjBH3duubRObemRWXv86jsoTANBgkqhkiG9w0BAQsF\n"
//                    + "AAOCAQEA3TPXEfNjWDjdGBX7CVW+dla5cEilaUcne8IkCJLxWh9KEik3JHRRHGJo\n"
//                    + "uM2VcGfl96S8TihRzZvoroed6ti6WqEBmtzw3Wodatg+VyOeph4EYpr/1wXKtx8/\n"
//                    + "wApIvJSwtmVi4MFU5aMqrSDE6ea73Mj2tcMyo5jMd6jmeWUHK8so/joWUoHOUgwu\n"
//                    + "X4Po1QYz+3dszkDqMp4fklxBwXRsW10KXzPMTZ+sOPAveyxindmjkW8lGy+QsRlG\n"
//                    + "PfZ+G6Z6h7mjem0Y+iWlkYcV4PIWL1iwBi8saCbGS5jN2p8M+X+Q7UNKEkROb3N6\n"
//                    + "KOqkqm57TH2H3eDJAkSnh6/DNFu0Qg==\n"
//                    + "-----END CERTIFICATE-----"
//        )
//    }

    @Provides
    @Singleton
    fun provideNetworkConfig(): NetworkConfiguration =
        NetworkConfiguration(
            serverAddress = NetworkConfigSingleton.endpoint,
            clientKey = "",
            sslKey = NetworkConfigSingleton.certificate,
            port = NetworkConfigSingleton.port,
            useSsl = !NetworkConfigSingleton.certificate.isBlank()
        )

    @Provides
    @Singleton
    fun provideInterceptor(dataStorage: ClientDataStorage): HeaderClientInterceptor {
        return HeaderClientInterceptor(
            dataStorage.uuid,
            "" //TODO:PFMSDK: AppUtils.getUserAgent()
        )
    }

    @Provides
    @Singleton
    fun provideChannel(networkConfig: NetworkConfiguration, interceptor: HeaderClientInterceptor, @ApplicationScoped context: Context): Channel {
        val channel = OkHttpChannelBuilder.forAddress(networkConfig.serverAddress, networkConfig.port).apply {
            if (networkConfig.useSsl) {
                sslSocketFactory(TLSHelper.getSslSocketFactory(ByteArrayInputStream(networkConfig.sslKey.toByteArray())))
            } else {
                usePlaintext(true)
            }
//            userAgent(AppUtils.getUserAgent()) //TODO:PFMSDK
        }.build()


        ChannelConnector(context, channel).keepConnected()

        val interceptors = ArrayList<ClientInterceptor>().apply { add(interceptor) }
        return ClientInterceptors.intercept(channel, interceptors)
    }
}


