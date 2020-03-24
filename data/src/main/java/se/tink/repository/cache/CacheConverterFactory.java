package se.tink.repository.cache;


import java.util.ArrayList;
import java.util.List;
import se.tink.converter.ModelConverter;
import se.tink.converter.misc.AmountEntityToAmountConverter;
import se.tink.converter.misc.AmountToAmountEntityConverter;
import se.tink.converter.misc.ExactNumberEntityToExactNumberConverter;
import se.tink.converter.misc.ExactNumberToExactNumberEntityConverter;
import se.tink.converter.statistic.StatisticEntityToStatisticConverter;
import se.tink.converter.statistic.StatisticToStatisticEntityConverter;
import se.tink.converter.transaction.TagEntityToTagConverter;
import se.tink.converter.transaction.TransactionEntityWithAllRelationsToTransactionConverter;
import se.tink.converter.transaction.TransactionToTransactionWithAllRelationsConverter;
import se.tink.converter.user.FlagEntityToStringConverter;
import se.tink.converter.user.I18ConfigurationEntityToUserConfigurationI18ConfigurationConverter;
import se.tink.converter.user.StringToFlagEntityConverter;
import se.tink.converter.user.UserConfigurationI18ConfigurationToI18ConfigurationEntityConverter;
import se.tink.converter.user.UserConfigurationI18nConfigurationEntityToI18nConfigurationConverter;
import se.tink.modelConverter.AbstractConverter;


public class CacheConverterFactory {

	public static List<AbstractConverter<?, ?>> getCacheConverters(ModelConverter converter) {
		List<AbstractConverter<?, ?>> converters = new ArrayList<>();
		converters.add(new TransactionToTransactionWithAllRelationsConverter(converter));
		converters.add(new TransactionEntityWithAllRelationsToTransactionConverter(converter));
		converters.add(new TagEntityToTagConverter());
		converters.add(new I18ConfigurationEntityToUserConfigurationI18ConfigurationConverter());
		converters.add(new UserConfigurationI18ConfigurationToI18ConfigurationEntityConverter());
		converters.add(new StringToFlagEntityConverter());
		converters.add(new FlagEntityToStringConverter());
		converters.add(new AmountEntityToAmountConverter());
		converters.add(new AmountToAmountEntityConverter());
		converters.add(new StatisticToStatisticEntityConverter(converter));
		converters.add(new StatisticEntityToStatisticConverter(converter));
		converters.add(new ExactNumberEntityToExactNumberConverter());
		converters.add(new ExactNumberToExactNumberEntityConverter());
		converters.add(new UserConfigurationI18nConfigurationEntityToI18nConfigurationConverter());

		return converters;
	}
}
