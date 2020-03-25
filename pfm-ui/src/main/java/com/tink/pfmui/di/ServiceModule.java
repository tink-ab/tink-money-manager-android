package com.tink.pfmui.di;

import com.tink.annotations.PfmScope;
import com.tink.service.user.UserProfileService;
import dagger.Module;
import dagger.Provides;
import io.grpc.Channel;
import java.security.Security;
import org.conscrypt.Conscrypt;
import se.tink.converter.ConverterModule;
import se.tink.converter.ModelConverter;
import se.tink.grpc.v1.services.StreamingServiceGrpc;
import se.tink.repository.ExceptionTracker;
import se.tink.repository.service.StreamingService;
import se.tink.repository.service.StreamingServiceImpl;
import se.tink.repository.service.UserConfigurationService;
import se.tink.repository.service.UserConfigurationServiceCachedImpl;

@Module(includes = {ConverterModule.class})
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
	UserConfigurationService userConfigurationService(
		UserProfileService userService
	) {
		return new UserConfigurationServiceCachedImpl(userService);
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
