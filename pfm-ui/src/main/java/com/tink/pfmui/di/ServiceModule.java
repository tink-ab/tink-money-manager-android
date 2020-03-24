package com.tink.pfmui.di;

import com.tink.annotations.PfmScope;
import com.tink.service.account.AccountService;
import com.tink.service.user.UserProfileService;
import dagger.Module;
import dagger.Provides;
import io.grpc.Channel;
import java.security.Security;
import java.util.List;
import org.conscrypt.Conscrypt;
import se.tink.converter.ConverterModule;
import se.tink.converter.ModelConverter;
import se.tink.core.models.transaction.Transaction;
import se.tink.core.models.user.UserConfiguration;
import se.tink.grpc.v1.services.DeviceServiceGrpc;
import se.tink.grpc.v1.services.StatisticServiceGrpc;
import se.tink.grpc.v1.services.StreamingServiceGrpc;
import se.tink.grpc.v1.services.TransactionServiceGrpc;
import se.tink.repository.ExceptionTracker;
import se.tink.repository.cache.Cache;
import se.tink.repository.cache.StasticCache;
import se.tink.repository.manager.StatisticServiceCachedImpl;
import se.tink.repository.manager.TransactionServiceCachedImpl;
import se.tink.repository.service.DeviceService;
import se.tink.repository.service.DeviceServiceImpl;
import se.tink.repository.service.StatisticService;
import se.tink.repository.service.StatisticServiceImpl;
import se.tink.repository.service.StreamingService;
import se.tink.repository.service.StreamingServiceImpl;
import se.tink.repository.service.TransactionService;
import se.tink.repository.service.TransactionServiceImpl;
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
	TransactionServiceGrpc.TransactionServiceStub provideTransactionService(
		Channel channel
	) {
		return TransactionServiceGrpc.newStub(channel);
	}

	@Provides
	@PfmScope
	StatisticServiceGrpc.StatisticServiceStub statisticServiceStub(Channel channel) {
		return StatisticServiceGrpc.newStub(channel);
	}

	@Provides
	@PfmScope
	TransactionService provideTransactionRepository(
		TransactionServiceGrpc.TransactionServiceStub transactionServiceApi,
		StreamingService streamingServiceStub,
		Cache<List<Transaction>> cache,
		ModelConverter converter,
		AccountService accountService
	) {
		return new TransactionServiceCachedImpl(
			transactionServiceApi,
			new TransactionServiceImpl(transactionServiceApi, streamingServiceStub, converter),
			streamingServiceStub,
			cache,
			converter,
			accountService
		);
	}


	@Provides
	@PfmScope
	UserConfigurationService userConfigurationService(
		StreamingService streamingService,
		UserProfileService userService,
		ModelConverter modelConverter,
		Cache<UserConfiguration> cache
	) {
		return new UserConfigurationServiceCachedImpl(streamingService, userService, cache);
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

	@Provides
	@PfmScope
	DeviceService provideDeviceService(Channel channel, ModelConverter modelConverter) {
		return new DeviceServiceImpl(DeviceServiceGrpc.newStub(channel), modelConverter);
	}
}
