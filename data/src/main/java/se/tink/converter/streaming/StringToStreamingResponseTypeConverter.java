package se.tink.converter.streaming;

import se.tink.converter.ModelConverter;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.StreamingResponseType;
import se.tink.grpc.v1.rpc.StreamingResponse;
import se.tink.modelConverter.AbstractConverter;

public class StringToStreamingResponseTypeConverter extends
	AbstractConverter<StreamingResponse.Type, StreamingResponseType> {

	private ModelConverter modelConverter;

	public StringToStreamingResponseTypeConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public StreamingResponseType convert(StreamingResponse.Type source) {
		switch (source) {
			case CREATE:
				return StreamingResponseType.CREATE;
			case DELETE:
				return StreamingResponseType.DELETE;
			case READ:
				return StreamingResponseType.READ;
			case UPDATE:
				return StreamingResponseType.UPDATE;

		}
		return null;
	}

	@Override
	public Class<StreamingResponse.Type> getSourceClass() {
		return StreamingResponse.Type.class;
	}

	@Override
	public Class<StreamingResponseType> getDestinationClass() {
		return StreamingResponseType.class;
	}
}
