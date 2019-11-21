package com.tink.pfmsdk.di;

import dagger.Module;
import dagger.Provides;
import io.grpc.Channel;
import java.security.Security;
import java.util.List;
import java.util.Map;
import javax.inject.Singleton;
import org.conscrypt.Conscrypt;
import se.tink.converter.ConverterModule;
import se.tink.converter.ModelConverter;
import se.tink.core.models.account.Account;
import se.tink.core.models.credential.Credential;
import se.tink.core.models.misc.Period;
import se.tink.core.models.provider.Provider;
import se.tink.core.models.transaction.Transaction;
import se.tink.core.models.user.UserConfiguration;
import se.tink.grpc.v1.services.AccountServiceGrpc;
import se.tink.grpc.v1.services.AuthenticationServiceGrpc;
import se.tink.grpc.v1.services.BudgetServiceGrpc;
import se.tink.grpc.v1.services.CredentialServiceGrpc;
import se.tink.grpc.v1.services.DeviceServiceGrpc;
import se.tink.grpc.v1.services.EmailAndPasswordAuthenticationServiceGrpc;
import se.tink.grpc.v1.services.LoanServiceGrpc;
import se.tink.grpc.v1.services.MobileBankIdAuthenticationServiceGrpc;
import se.tink.grpc.v1.services.ProviderServiceGrpc;
import se.tink.grpc.v1.services.SettingsServiceGrpc;
import se.tink.grpc.v1.services.StatisticServiceGrpc;
import se.tink.grpc.v1.services.StreamingServiceGrpc;
import se.tink.grpc.v1.services.TrackingServiceGrpc;
import se.tink.grpc.v1.services.TransactionServiceGrpc;
import se.tink.grpc.v1.services.UserServiceGrpc;
import se.tink.grpc.v1.services.UserServiceGrpc.UserServiceStub;
import se.tink.repository.ExceptionTracker;
import se.tink.repository.ServiceCoordinator;
import se.tink.repository.cache.Cache;
import se.tink.repository.cache.CategoryTreeCache;
import se.tink.repository.cache.LiveDataCache;
import se.tink.repository.cache.StasticCache;
import se.tink.repository.cache.WritableCacheRepository;
import se.tink.repository.manager.CategoryServiceCachedImpl;
import se.tink.repository.manager.CredentialServiceCachedImpl;
import se.tink.repository.manager.ProviderServiceCachedImpl;
import se.tink.repository.manager.StatisticServiceCachedImpl;
import se.tink.repository.manager.TransactionServiceCachedImpl;
import se.tink.repository.service.AccountService;
import se.tink.repository.service.AccountServiceCachedImpl;
import se.tink.repository.service.BudgetService;
import se.tink.repository.service.BudgetServiceImpl;
import se.tink.repository.service.CategoryService;
import se.tink.repository.service.CredentialService;
import se.tink.repository.service.CredentialServiceImpl;
import se.tink.repository.service.PeriodService;
import se.tink.repository.service.PeriodServiceCachedImpl;
import se.tink.repository.service.ProviderService;
import se.tink.repository.service.ProviderServiceImpl;
import se.tink.repository.service.SettingsService;
import se.tink.repository.service.SettingsServiceImpl;
import se.tink.repository.service.StatisticService;
import se.tink.repository.service.StreamingService;
import se.tink.repository.service.StreamingServiceImpl;
import se.tink.repository.service.TrackingService;
import se.tink.repository.service.TrackingServiceImpl;
import se.tink.repository.service.TransactionService;
import se.tink.repository.service.TransactionServiceImpl;
import se.tink.repository.service.UserConfigurationService;
import se.tink.repository.service.UserConfigurationServiceCachedImpl;
import se.tink.repository.service.UserService;
import se.tink.repository.service.UserServiceImpl;

@Module(includes = {ConverterModule.class, CacheModule.class})
public class ServiceModule {

	public ServiceModule() {
		Security.insertProviderAt(Conscrypt.newProvider("GmsCore_OpenSSL"), 1);
	}

	@Provides
	@Singleton
	public StreamingServiceGrpc.StreamingServiceStub providesStreamingService(Channel channel) {
		return StreamingServiceGrpc.newStub(channel);
	}

	@Provides
	@Singleton
	public AccountServiceGrpc.AccountServiceStub accountServiceApi(Channel channel) {
		return AccountServiceGrpc.newStub(channel);
	}

	@Provides
	@Singleton
	public LoanServiceGrpc.LoanServiceStub loanServiceStub(Channel channel) {
		return LoanServiceGrpc.newStub(channel);
	}

	@Provides
	@Singleton
	public SettingsServiceGrpc.SettingsServiceStub settingsServiceStub(Channel channel) {
		return SettingsServiceGrpc.newStub(channel);
	}

	@Provides
	@Singleton
	public TransactionServiceGrpc.TransactionServiceStub provideTransactionService(
		Channel channel
	) {
		return TransactionServiceGrpc.newStub(channel);
	}

	@Provides
	@Singleton
	public UserServiceGrpc.UserServiceStub userServiceStub(Channel channel) {
		return UserServiceGrpc.newStub(channel);
	}

	@Provides
	@Singleton
	public StatisticServiceGrpc.StatisticServiceStub statisticServiceStub(Channel channel) {
		return StatisticServiceGrpc.newStub(channel);
	}

	@Provides
	@Singleton
	public AccountService provideAccountService(
		AccountServiceGrpc.AccountServiceStub accountServiceApi,
		StreamingService streamingServiceStub,
		ModelConverter converter,
		LiveDataCache<List<Account>> cache
	) {
		return new AccountServiceCachedImpl(accountServiceApi, streamingServiceStub, converter, cache);
	}

	@Provides
	@Singleton
	public CredentialServiceGrpc.CredentialServiceStub credentialServiceApi(Channel channel) {
		return CredentialServiceGrpc.newStub(channel);
	}

	@Provides
	@Singleton
	public ProviderServiceGrpc.ProviderServiceStub providerServiceApi(Channel channel) {
		return ProviderServiceGrpc.newStub(channel);
	}

	@Provides
	@Singleton
	public TransactionService provideTransactionRepository(
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
	public UserService provideUserService(
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
	public CredentialService provideCredentialRepository(
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
	public ProviderService provideProviderRepository(
		ProviderServiceGrpc.ProviderServiceStub api,
		StreamingService streamingServiceStub,
		WritableCacheRepository<Provider> cache, ModelConverter converter
	) {
		return new ProviderServiceCachedImpl(
			new ProviderServiceImpl(api, streamingServiceStub, converter), cache,
			streamingServiceStub
		);
	}

	@Provides
	@Singleton
	public SettingsService settingsService(
		SettingsServiceGrpc.SettingsServiceStub stub,
		UserServiceStub userService,
		ModelConverter converter
	) {
		return new SettingsServiceImpl(stub, userService, converter);
	}

	@Provides
	@Singleton
	public PeriodService periodService(
		Cache<Map<String, Period>> periodRepository,
		StreamingService streamingService,
		ModelConverter modelConverter
	) {
		return new PeriodServiceCachedImpl(periodRepository, streamingService, modelConverter);
	}

	@Provides
	@Singleton
	public UserConfigurationService userConfigurationService(
		StreamingService streamingService,
		ModelConverter modelConverter,
		Cache<UserConfiguration> cache
	) {
		return new UserConfigurationServiceCachedImpl(streamingService, cache);
	}

	@Provides
	@Singleton
	public StatisticService provideStatisticService(
		StreamingService streaming,
		ModelConverter converter,
		StatisticServiceGrpc.StatisticServiceStub serviceStub,
		StasticCache cache
	) {
		return new StatisticServiceCachedImpl(streaming, converter, serviceStub, cache);
	}

	@Provides
	@Singleton
	public StreamingService provideStreamingService(
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
	public CategoryService provideCategoryRepository(
		StreamingService stub,
		ModelConverter converter,
		CategoryTreeCache cache
	) {
		return new CategoryServiceCachedImpl(stub, converter, cache);
	}

	@Provides
	@Singleton
	public ServiceCoordinator provideServiceCoordinator(
		StreamingService streamingService,
		SettingsService settingsService
	) {
		return new ServiceCoordinator(streamingService, settingsService);
	}

	@Provides
	@Singleton
	public TrackingService trackingService(ModelConverter modelConverter, Channel channel) {
		return new TrackingServiceImpl(TrackingServiceGrpc.newStub(channel), modelConverter);
	}

	@Provides
	@Singleton
	public DeviceServiceGrpc.DeviceServiceStub deviceStubService(Channel channel) {
		return DeviceServiceGrpc.newStub(channel);
	}

	@Singleton
	@Provides
	public BudgetService budgetService(ModelConverter modelConverter, Channel channel) {
		return new BudgetServiceImpl(BudgetServiceGrpc.newStub(channel), modelConverter);
	}
}
