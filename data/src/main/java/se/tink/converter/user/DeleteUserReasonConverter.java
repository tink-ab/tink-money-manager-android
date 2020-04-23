package se.tink.converter.user;

import com.google.protobuf.StringValue;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.user.DeleteAccountReason;
import se.tink.grpc.v1.rpc.DeleteUserRequest;
import se.tink.grpc.v1.rpc.DeleteUserRequest.Builder;
import se.tink.modelConverter.AbstractConverter;

public class DeleteUserReasonConverter extends
	AbstractConverter<DeleteAccountReason, DeleteUserRequest> {

	private final ModelConverterImpl modelConverter;

	public DeleteUserReasonConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public DeleteUserRequest convert(DeleteAccountReason source) {
		Builder destination = DeleteUserRequest.newBuilder();
		if (source.getComment() != null) {
			StringValue.Builder builder = StringValue.newBuilder();
			builder.setValue(source.getComment());
			destination.setComment(builder.build());
		}
		if (source.getReasons() != null) {
			for (String reason : source.getReasons()) {
				destination.addReasons(reason);
			}

		}
		return destination.build();
	}

	@Override
	public Class<DeleteAccountReason> getSourceClass() {
		return DeleteAccountReason.class;
	}

	@Override
	public Class<DeleteUserRequest> getDestinationClass() {
		return DeleteUserRequest.class;
	}
}
