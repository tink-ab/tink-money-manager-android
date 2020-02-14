package com.tink.pfmsdk.di;

import dagger.Module;
import dagger.Provides;
import io.grpc.Channel;
import java.security.Security;
import java.util.List;
import javax.inject.Singleton;
import org.conscrypt.Conscrypt;
import se.tink.converter.ConverterModule;
import se.tink.converter.ModelConverter;
import se.tink.core.models.account.Account;
import se.tink.core.models.credential.Credential;
import se.tink.core.models.transaction.Transaction;
import se.tink.core.models.user.UserConfiguration;
import se.tink.grpc.v1.services.AccountServiceGrpc;
import se.tink.grpc.v1.services.AuthenticationServiceGrpc;
import se.tink.grpc.v1.services.BudgetServiceGrpc;
import se.tink.grpc.v1.services.CategoryServiceGrpc;
import se.tink.grpc.v1.services.CredentialServiceGrpc;
import se.tink.grpc.v1.services.DeviceServiceGrpc;
import se.tink.grpc.v1.services.EmailAndPasswordAuthenticationServiceGrpc;
import se.tink.grpc.v1.services.InsightsServiceGrpc;
import se.tink.grpc.v1.services.LoanServiceGrpc;
import se.tink.grpc.v1.services.MobileBankIdAuthenticationServiceGrpc;
import se.tink.grpc.v1.services.StatisticServiceGrpc;
import se.tink.grpc.v1.services.StreamingServiceGrpc;
import se.tink.grpc.v1.services.TransactionServiceGrpc;
import se.tink.grpc.v1.services.UserServiceGrpc;
import se.tink.repository.ExceptionTracker;
import se.tink.repository.cache.Cache;
import se.tink.repository.cache.CategoryTreeCache;
import se.tink.repository.cache.LiveDataCache;
import se.tink.repository.cache.StasticCache;
import se.tink.repository.cache.WritableCacheRepository;
import se.tink.repository.manager.CategoryServiceCachedImpl;
import se.tink.repository.manager.CredentialServiceCachedImpl;
import se.tink.repository.manager.StatisticServiceCachedImpl;
import se.tink.repository.manager.TransactionServiceCachedImpl;
import se.tink.repository.service.AccountService;
import se.tink.repository.service.AccountServiceCachedImpl;
import se.tink.repository.service.BudgetService;
import se.tink.repository.service.BudgetServiceImpl;
import se.tink.repository.service.CategoryService;
import se.tink.repository.service.CredentialService;
import se.tink.repository.service.CredentialServiceImpl;
import se.tink.repository.service.DeviceService;
import se.tink.repository.service.DeviceServiceImpl;
import se.tink.repository.service.InsightService;
import se.tink.repository.service.InsightServiceImpl;
import se.tink.repository.service.StatisticService;
import se.tink.repository.service.StatisticServiceImpl;
import se.tink.repository.service.StreamingService;
import se.tink.repository.service.StreamingServiceImpl;
import se.tink.repository.service.TransactionService;
import se.tink.repository.service.TransactionServiceImpl;
import se.tink.repository.service.UserConfigurationService;
import se.tink.repository.service.UserConfigurationServiceCachedImpl;
import se.tink.repository.service.UserService;
import se.tink.repository.service.UserServiceImpl;

@Module(includes = {ConverterModule.class, CacheModule.class})
class ServiceModule {

	public ServiceModule() {
		Security.insertProviderAt(Conscrypt.newProvider("GmsCore_OpenSSL"), 1);
	}

	@Provides
	@Singleton
	StreamingServiceGrpc.StreamingServiceStub providesStreamingService(Channel channel) {
		return StreamingServiceGrpc.newStub(channel);
	}

	@Provides
	@Singleton
	AccountServiceGrpc.AccountServiceStub accountServiceApi(Channel channel) {
		return AccountServiceGrpc.newStub(channel);
	}

	@Provides
	@Singleton
	LoanServiceGrpc.LoanServiceStub loanServiceStub(Channel channel) {
		return LoanServiceGrpc.newStub(channel);
	}

	@Provides
	@Singleton
	TransactionServiceGrpc.TransactionServiceStub provideTransactionService(
		Channel channel
	) {
		return TransactionServiceGrpc.newStub(channel);
	}

	@Provides
	@Singleton
	UserServiceGrpc.UserServiceStub userServiceStub(Channel channel) {
		return UserServiceGrpc.newStub(channel);
	}

	@Provides
	@Singleton
	StatisticServiceGrpc.StatisticServiceStub statisticServiceStub(Channel channel) {
		return StatisticServiceGrpc.newStub(channel);
	}

	@Provides
	@Singleton
	AccountService provideAccountService(
		AccountServiceGrpc.AccountServiceStub accountServiceApi,
		StreamingService streamingServiceStub,
		ModelConverter converter,
		LiveDataCache<List<Account>> cache
	) {
		return new AccountServiceCachedImpl(accountServiceApi, streamingServiceStub, converter, cache);
	}

	@Provides
	@Singleton
	CredentialServiceGrpc.CredentialServiceStub credentialServiceApi(Channel channel) {
		return CredentialServiceGrpc.newStub(channel);
	}

	@Provides
	@Singleton
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
	@Singleton
	UserService provideUserService(
		ModelConverter converter,
		UserServiceGrpc.UserServiceStub userService,
		Channel channel
	) {
		return new UserServiceImpl(
			AuthenticationServiceGrpc.newStub(channel),
			MobileBankIdAuthenticationServiceGrpc.newStub(channel),
			EmailAndPasswordAuthenticationServiceGrpc.newStub(channel),
			userService,
			converter);
	}


	@Provides
	@Singleton
	CredentialService provideCredentialRepository(
		CredentialServiceGrpc.CredentialServiceStub api,
		StreamingService streamingServiceStub,
		WritableCacheRepository<Credential> cache,
		ModelConverter converter
	) {
		CredentialService innerService = new CredentialServiceImpl(streamingServiceStub, converter,
			api);
		return new CredentialServiceCachedImpl(innerService, streamingServiceStub, cache);
	}

	@Provides
	@Singleton
	UserConfigurationService userConfigurationService(
		StreamingService streamingService,
		UserService userService,
		ModelConverter modelConverter,
		Cache<UserConfiguration> cache
	) {
		return new UserConfigurationServiceCachedImpl(streamingService, userService, cache);
	}

	@Provides
	@Singleton
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
	@Singleton
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
	@Singleton
	CategoryService provideCategoryRepository(
		StreamingService stub,
		Channel channel,
		ModelConverter converter,
		CategoryTreeCache cache
	) {
    return new CategoryServiceCachedImpl(
    	stub, CategoryServiceGrpc.newStub(channel), converter, cache);
	}

	@Provides
	@Singleton
	DeviceService provideDeviceService(Channel channel, ModelConverter modelConverter) {
		return new DeviceServiceImpl(DeviceServiceGrpc.newStub(channel), modelConverter);
	}

	@Singleton
	@Provides
	public BudgetService budgetService(ModelConverter modelConverter, Channel channel) {
		return new BudgetServiceImpl(BudgetServiceGrpc.newStub(channel), modelConverter);
	}

	@Singleton
	@Provides
	InsightService insightService(ModelConverter modelConverter, Channel channel) {
		return new InsightServiceImpl(InsightsServiceGrpc.newStub(channel), modelConverter);
	}
}
