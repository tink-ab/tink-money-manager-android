package com.tink.pfmsdk.mapper;


import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import se.tink.converter.ModelConverter;
import se.tink.converter.misc.ExactNumberDTOToExactNumberConverter;
import se.tink.converter.misc.ExactNumberToExactNumberDTOConverter;
import se.tink.converter.pushnotifications.RegisterPushNotificationTokenRequestToSendConverter;
import se.tink.converter.pushnotifications.ResponseToRegisterPushNotificationTokenResponseConverter;
import se.tink.modelConverter.AbstractConverter;

public class ModelConverterImplementation implements ModelConverter {

	private final Map<Integer, AbstractConverter> converterMap = new HashMap<Integer, AbstractConverter>();

	public ModelConverterImplementation() {
		addConverters();
	}

	public <KR, VR, KI, VI> Map<KR, VR> map(Map<KI, VI> source, Class<KR> destinationKeyType,
		Class<VR> destinationValueType) {
		Map<KR, VR> returnMap = Maps.newHashMap();
		Set<KI> keySet = source.keySet();
		for (KI key : keySet) {
			VI value = source.get(key);
			returnMap.put(map(key, destinationKeyType), map(value, destinationValueType));
		}
		return returnMap;
	}

	@Override
	public <S, D> List<D> map(Collection<S> source, Class<D> destinationType) {
		ArrayList<D> destinationList = new ArrayList<>();
		for (S sourceObject : source) {
			destinationList.add(map(sourceObject, destinationType));
		}
		return destinationList;
	}

	@Override
	public <S, D> D map(S source, Class<D> destinationType) {
		if (source.getClass().equals(destinationType)) {
			return (D) source;
		}
		AbstractConverter<S, D> converter = getConverter(source.getClass(), destinationType);
		return converter.convert(source);
	}


	private <D> AbstractConverter getConverter(Class<?> aClass, Class<D> destinationType) {
		return converterMap.get(getHash(aClass, destinationType));
	}

	private void addConverters() {
		addConverter(new ExactNumberToExactNumberDTOConverter(this));
		addConverter(new ExactNumberDTOToExactNumberConverter());
		addConverter(new RegisterPushNotificationTokenRequestToSendConverter(this));
		addConverter(new ResponseToRegisterPushNotificationTokenResponseConverter(this));

		setupChartConverters();
		setupCalendarConverters();
		setupStreamingResponseConverter();
	}

	private void setupChartConverters() {
		addConverter(new AmountToDoubleConverter());
		addConverter(new StatisticToPeriodBalanceConverter(this));
	}

	private void setupStreamingResponseConverter() {

	}

	private void setupCalendarConverters() {

	}

	private void addConverter(
		AbstractConverter abstractConverter) {
		converterMap.put(
			getHash(abstractConverter.getSourceClass(), abstractConverter.getDestinationClass()),
			abstractConverter);
	}

	private <D> Integer getHash(Class<?> aClass, Class<D> destinationType) {
		int hashCode = 17;
		hashCode = hashCode * 31 + aClass.hashCode();
		hashCode = hashCode * 31 + destinationType.hashCode();
		return hashCode;
	}
}
