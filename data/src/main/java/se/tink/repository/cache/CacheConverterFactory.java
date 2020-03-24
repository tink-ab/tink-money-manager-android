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
import se.tink.modelConverter.AbstractConverter;


public class CacheConverterFactory {

	public static List<AbstractConverter<?, ?>> getCacheConverters(ModelConverter converter) {
		List<AbstractConverter<?, ?>> converters = new ArrayList<>();
		converters.add(new TagEntityToTagConverter());
		converters.add(new AmountEntityToAmountConverter());
		converters.add(new AmountToAmountEntityConverter());
		converters.add(new StatisticToStatisticEntityConverter(converter));
		converters.add(new StatisticEntityToStatisticConverter(converter));
		converters.add(new ExactNumberEntityToExactNumberConverter());
		converters.add(new ExactNumberToExactNumberEntityConverter());
		return converters;
	}
}
