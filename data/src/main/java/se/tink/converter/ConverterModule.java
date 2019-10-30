package se.tink.converter;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class ConverterModule {

	@Provides
	@Singleton
	public ModelConverter provideModelConverter() {
		return new ModelConverterImpl();
	}
}