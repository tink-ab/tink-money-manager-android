package se.tink.converter.settings;

import com.google.protobuf.Int32Value;
import se.tink.converter.EnumMappers;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.user.PeriodSetting;
import se.tink.grpc.v1.rpc.UpdatePeriodSettingsRequest;
import se.tink.grpc.v1.rpc.UpdatePeriodSettingsRequest.Builder;
import se.tink.modelConverter.AbstractConverter;

public class PeriodSettingsConverterUpdateRequest extends
	AbstractConverter<PeriodSetting, UpdatePeriodSettingsRequest> {

	private final ModelConverterImpl modelConverter;

	public PeriodSettingsConverterUpdateRequest(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public UpdatePeriodSettingsRequest convert(PeriodSetting source) {
		Builder destination = UpdatePeriodSettingsRequest.newBuilder();
		Int32Value.Builder int32Vale = Int32Value.newBuilder();
		int32Vale.setValue(source.getMonthlyAdjustedDay());
		destination.setMonthlyAdjustedDay(int32Vale.build());
		destination.setPeriodDateBreakType(
			EnumMappers.PERIOD_SETTINGS_MAP.inverse().get(source.getPeriodDateBreakType()));
		return destination.build();
	}

	@Override
	public Class<PeriodSetting> getSourceClass() {
		return PeriodSetting.class;
	}

	@Override
	public Class<UpdatePeriodSettingsRequest> getDestinationClass() {
		return UpdatePeriodSettingsRequest.class;
	}

}
