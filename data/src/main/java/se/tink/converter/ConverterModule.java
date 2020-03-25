package se.tink.converter;

import com.tink.annotations.PfmScope;
import dagger.Module;
import dagger.Provides;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Module
public class ConverterModule {

	@Provides
	@PfmScope
	public ModelConverter provideModelConverter() {
		return new ModelConverter() {
			@Override
			public <KR, VR, KI, VI> Map<KR, VR> map(Map<KI, VI> source,
				Class<KR> destinationKeyType, Class<VR> destinationValueType) {
				return null;
			}

			@Override
			public <S, D> List<D> map(Collection<S> source, Class<D> destinationType) {
				return null;
			}

			@Override
			public <S, D> D map(S source, Class<D> destinationType) {
				return null;
			}
		};
	}
}