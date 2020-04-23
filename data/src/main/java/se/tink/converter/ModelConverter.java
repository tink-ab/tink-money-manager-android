package se.tink.converter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ModelConverter {



	<KR, VR, KI, VI> Map<KR, VR> map(Map<KI, VI> source, Class<KR> destinationKeyType,
		Class<VR> destinationValueType);

	<S, D> List<D> map(Collection<S> source, Class<D> destinationType);

	<S, D> D map(S source, Class<D> destinationType);
}