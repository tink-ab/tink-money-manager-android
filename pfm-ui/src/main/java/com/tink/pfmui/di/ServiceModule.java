package com.tink.pfmui.di;

import com.tink.annotations.PfmScope;
import com.tink.service.transaction.TransactionService;
import com.tink.service.user.UserProfileService;
import dagger.Module;
import dagger.Provides;
import io.grpc.Channel;
import java.security.Security;
import org.conscrypt.Conscrypt;
import se.tink.converter.ConverterModule;
import se.tink.converter.ModelConverter;
import se.tink.grpc.v1.services.StatisticServiceGrpc;
import se.tink.grpc.v1.services.StreamingServiceGrpc;
import se.tink.repository.ExceptionTracker;
import se.tink.repository.cache.StasticCache;
import se.tink.repository.manager.StatisticServiceCachedImpl;
import se.tink.repository.service.StatisticService;
import se.tink.repository.service.StatisticServiceImpl;
import se.tink.repository.service.StreamingService;
import se.tink.repository.service.StreamingServiceImpl;
import se.tink.repository.service.UserConfigurationService;
import se.tink.repository.service.UserConfigurationServiceCachedImpl;

@Module(includes = {ConverterModule.class, CacheModule.class})
class ServiceModule {

	public ServiceModule() {
		Security.insertProviderAt(Conscrypt.newProvider("GmsCore_OpenSSL"), 1);
	}

	@Provides
	@PfmScope
	StreamingServiceGrpc.StreamingServiceStub providesStreamingService(Channel channel) {
		return StreamingServiceGrpc.newStub(channel);
	}

	@Provides
	@PfmScope
	StatisticServiceGrpc.StatisticServiceStub statisticServiceStub(Channel channel) {
		return StatisticServiceGrpc.newStub(channel);
	}

	@Provides
	@PfmScope
	UserConfigurationService userConfigurationService(
		UserProfileService userService,
		ModelConverter modelConverter
	) {
		return new UserConfigurationServiceCachedImpl(userService);
	}

	@Provides
	@PfmScope
	StatisticService provideStatisticService(
		StreamingService streaming,
		ModelConverter converter,
		StatisticServiceGrpc.StatisticServiceStub serviceStub,
		TransactionService transactionService,
		StasticCache cache
	) {
    return new StatisticServiceCachedImpl(
        new StatisticServiceImpl(streaming, converter, serviceStub, transactionService), cache);
	}

	@Provides
	@PfmScope
	StreamingService provideStreamingService(
		StreamingServiceGrpc.StreamingServiceStub stub,
		ModelConverter converter,
		ExceptionTracker tracker
	) {
		StreamingServiceImpl impl = new StreamingServiceImpl(stub, converter);
		impl.setExceptionTracker(tracker);
		return impl;

	}
}
