package com.tink.pfmui.di

import android.content.Context
import com.tink.pfmui.ClientDataStorage
import com.tink.pfmui.buildConfig.NetworkConfiguration
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
import java.io.ByteArrayInputStream
import javax.inject.Singleton

@Module
internal class NetworkModule {

    @Provides
    @Singleton
    fun provideInterceptor(dataStorage: ClientDataStorage): HeaderClientInterceptor {
        return HeaderClientInterceptor(dataStorage.uuid)
    }

    @Provides
    @Singleton
    fun provideChannel(networkConfig: NetworkConfiguration, interceptor: HeaderClientInterceptor, @ApplicationScoped context: Context): Channel {
        val channel = OkHttpChannelBuilder.forAddress(networkConfig.serverAddress, networkConfig.port).apply {
            if (networkConfig.useSsl) {
                sslSocketFactory(TLSHelper.getSslSocketFactory(ByteArrayInputStream(networkConfig.sslKey.toByteArray())))
            } else {
                usePlaintext()
            }
//            userAgent(AppUtils.getUserAgent()) //TODO:PFMSDK
        }.build()


        ChannelConnector(context, channel).keepConnected()

        val interceptors = ArrayList<ClientInterceptor>().apply { add(interceptor) }
        return ClientInterceptors.intercept(channel, interceptors)
    }
}


