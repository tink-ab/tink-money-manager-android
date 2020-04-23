package se.tink.converter.settings;

import se.tink.converter.EnumMappers;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.user.PeriodSetting;
import se.tink.grpc.v1.models.PeriodSettings;
import se.tink.modelConverter.AbstractConverter;

public class PeriodSettingsConverter extends AbstractConverter<PeriodSettings, PeriodSetting> {

	public PeriodSettingsConverter(ModelConverterImpl modelConverter) {
	}

	@Override
	public PeriodSetting convert(PeriodSettings source) {
		PeriodSetting target = new PeriodSetting();
		target.setMonthlyAdjustedDay(source.getMonthlyAdjustedDay());
		target.setPeriodDateBreakType(
			EnumMappers.PERIOD_SETTINGS_MAP.get(source.getPeriodDateBreakType()));
		return target;
	}

	@Override
	public Class<PeriodSettings> getSourceClass() {
		return PeriodSettings.class;
	}

	@Override
	public Class<PeriodSetting> getDestinationClass() {
		return PeriodSetting.class;
	}
}
