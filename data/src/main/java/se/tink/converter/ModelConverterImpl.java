package se.tink.converter;

import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import se.tink.converter.date.DateTimeToDateConverter;
import se.tink.converter.date.DateTimeToTimestampConverter;
import se.tink.converter.date.DateToDateTimeConverter;
import se.tink.converter.date.DateToTimestampConverter;
import se.tink.converter.date.TimestampToDateTimeConverter;
import se.tink.converter.date.TimestampToLongConverter;
import se.tink.converter.device.AuthenticationMethodEnumResponsConverter;
import se.tink.converter.device.DeviceConfigurationMarketResponseConverter;
import se.tink.converter.device.DeviceConfigurationResponseConverter;
import se.tink.converter.misc.AmountDTOToAmountConverter;
import se.tink.converter.misc.AmountToAmountDTOConverter;
import se.tink.converter.misc.ExactNumberDTOToExactNumberConverter;
import se.tink.converter.misc.ExactNumberToExactNumberDTOConverter;
import se.tink.converter.misc.StringToStringValueConverter;
import se.tink.converter.misc.YearMonthDTOToYearMonthConverter;
import se.tink.converter.misc.YearWeekDTOToYearWeekConverter;
import se.tink.converter.request.TransactionToUpdateTransactionRequest;
import se.tink.converter.statistic.StatisticDTOToStatisticConverter;
import se.tink.converter.statistic.StatisticToStatisticDTOConverter;
import se.tink.converter.statistic.StatisticTreeDTOStatisticTreeConverter;
import se.tink.converter.streaming.StringToStreamingResponseTypeConverter;
import se.tink.converter.transaction.QueryTransactionResponseDTOToSearchResultMetadataConverter;
import se.tink.converter.transaction.SuggestTransactionConverter;
import se.tink.converter.transaction.SuggestTransactionsResponseConverter;
import se.tink.converter.transaction.TagDTOToTagConverter;
import se.tink.converter.transaction.TagToTagDTOConverter;
import se.tink.converter.transaction.TransactionClusterConverter;
import se.tink.converter.transaction.TransactionDTOToTransactionConverter;
import se.tink.converter.transaction.TransactionDetailsDTOToTransactionDetailsConverter;
import se.tink.converter.transaction.TransactionDetailsToTransactionDetailsDTOConverter;
import se.tink.converter.transaction.TransactionToTransactionDTOConverter;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.CacheConverterFactory;

public class ModelConverterImpl implements ModelConverter {

    private final Map<Integer, AbstractConverter> converterMap =
            new HashMap<Integer, AbstractConverter>();

    public ModelConverterImpl() {
        addConverters();
    }

    private <D> AbstractConverter getConverter(Class<?> aClass, Class<D> destinationType) {
        return converterMap.get(getHash(aClass, destinationType));
    }

    private <D> Integer getHash(Class<?> aClass, Class<D> destinationType) {
        int hashCode = 17;
        hashCode = hashCode * 31 + aClass.hashCode();
        hashCode = hashCode * 31 + destinationType.hashCode();
        return hashCode;
    }

    @Override
    public <KR, VR, KI, VI> Map<KR, VR> map(
            Map<KI, VI> source, Class<KR> destinationKeyType, Class<VR> destinationValueType) {
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

    private void addConverters() {
        setupStatisticConverters();
        setupTransactionConverters();
        setupSupportModelConverters();
        setupStreamingResponseConverter();
        setupRequestConverters();
        setupDeviceConfigurationConverters();
        setupCacheEntityConverters();
    }

    private void addConverter(AbstractConverter abstractConverter) {
        converterMap.put(
                getHash(
                        abstractConverter.getSourceClass(),
                        abstractConverter.getDestinationClass()),
                abstractConverter);
    }

    private void addConverters(List<AbstractConverter<?, ?>> converters) {
        for (AbstractConverter converter : converters) {
            addConverter(converter);
        }
    }

    private void setupStreamingResponseConverter() {
        addConverter(new StringToStreamingResponseTypeConverter(this));
    }

    private void setupTransactionConverters() {
        addConverter(new TransactionDTOToTransactionConverter(this));
        addConverter(new TransactionToTransactionDTOConverter(this));

        addConverter(new TransactionDetailsDTOToTransactionDetailsConverter(this));
        addConverter(new TransactionDetailsToTransactionDetailsDTOConverter(this));

        addConverter(new TagToTagDTOConverter(this));
        addConverter(new TagDTOToTagConverter(this));

        addConverter(new SuggestTransactionConverter(this));
        addConverter(new SuggestTransactionsResponseConverter(this));
        addConverter(new TransactionClusterConverter(this));

        addConverter(new QueryTransactionResponseDTOToSearchResultMetadataConverter(this));
    }

    private void setupStatisticConverters() {
        addConverter(new StatisticTreeDTOStatisticTreeConverter(this));
        addConverter(new StatisticToStatisticDTOConverter(this));
        addConverter(new StatisticDTOToStatisticConverter(this));
    }

    private void setupSupportModelConverters() {
        addConverter(new ExactNumberDTOToExactNumberConverter());
        addConverter(new ExactNumberToExactNumberDTOConverter(this));

        addConverter(new AmountDTOToAmountConverter());
        addConverter(new AmountToAmountDTOConverter(this));

        addConverter(new DateToDateTimeConverter());
        addConverter(new DateTimeToDateConverter());
        addConverter(new DateTimeToTimestampConverter());
        addConverter(new DateToTimestampConverter());

        addConverter(new TimestampToDateTimeConverter());
        addConverter(new TimestampToLongConverter(this));

        addConverter(new YearWeekDTOToYearWeekConverter());
        addConverter(new YearMonthDTOToYearMonthConverter());

        addConverter(new StringToStringValueConverter(this));
    }

    private void setupRequestConverters() {
        addConverter(new TransactionToUpdateTransactionRequest(this));
    }

    private void setupDeviceConfigurationConverters() {
        addConverter(new DeviceConfigurationMarketResponseConverter(this));
        addConverter(new DeviceConfigurationResponseConverter(this));

        addConverter(new AuthenticationMethodEnumResponsConverter(this));
    }

    private void setupCacheEntityConverters() {
        addConverters(CacheConverterFactory.getCacheConverters(this));
    }
}
