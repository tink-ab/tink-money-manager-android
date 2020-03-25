package com.tink.pfmui.di;

import com.tink.annotations.PfmScope;
import com.tink.service.user.UserProfileService;
import dagger.Module;
import dagger.Provides;
import java.security.Security;
import org.conscrypt.Conscrypt;
import se.tink.android.repository.service.UserConfigurationService;
import se.tink.android.repository.service.UserConfigurationServiceCachedImpl;

@Module
class ServiceModule {

	public ServiceModule() {
		Security.insertProviderAt(Conscrypt.newProvider("GmsCore_OpenSSL"), 1);
	}

	@Provides
	@PfmScope
	UserConfigurationService userConfigurationService(
		UserProfileService userService
	) {
		return new UserConfigurationServiceCachedImpl(userService);
	}
}
