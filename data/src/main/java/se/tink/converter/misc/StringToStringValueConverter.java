package se.tink.converter.misc;

import com.google.protobuf.StringValue;
import com.google.protobuf.StringValue.Builder;
import se.tink.converter.ModelConverter;
import se.tink.modelConverter.AbstractConverter;

public class StringToStringValueConverter extends AbstractConverter<String, StringValue> {

	private final ModelConverter converter;

	public StringToStringValueConverter(ModelConverter converter) {
		this.converter = converter;
	}

	@Override
	public StringValue convert(String source) {
		Builder destination = StringValue.newBuilder();
		destination.setValue(source);
		return destination.build();
	}

	@Override
	public Class<String> getSourceClass() {
		return String.class;
	}

	@Override
	public Class<StringValue> getDestinationClass() {
		return StringValue.class;
	}

}