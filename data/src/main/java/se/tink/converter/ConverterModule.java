package se.tink.converter;

import com.tink.annotations.PfmScope;
import dagger.Module;
import dagger.Provides;

@Module
public class ConverterModule {

	@Provides
	@PfmScope
	public ModelConverter provideModelConverter() {
		return new ModelConverterImpl();
	}
}