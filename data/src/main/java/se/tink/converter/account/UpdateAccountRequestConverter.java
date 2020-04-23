package se.tink.converter.account;

import com.google.protobuf.BoolValue;
import com.google.protobuf.StringValue;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.account.Account;
import se.tink.core.models.account.Account.Type;
import se.tink.grpc.v1.models.ExactNumber;
import se.tink.grpc.v1.rpc.UpdateAccountRequest;
import se.tink.modelConverter.AbstractConverter;

public class UpdateAccountRequestConverter extends
	AbstractConverter<Account, UpdateAccountRequest> {

	private final ModelConverterImpl converter;

	public UpdateAccountRequestConverter(ModelConverterImpl modelConverter) {
		this.converter = modelConverter;
	}

	@Override
	public UpdateAccountRequest convert(Account account) {
		UpdateAccountRequest.Builder requestDto = UpdateAccountRequest.newBuilder();
		requestDto.setAccountId(account.getId());
		requestDto.setExcluded(BoolValue.newBuilder().setValue(account.isExcluded()).build());
		requestDto.setFavored(BoolValue.newBuilder().setValue(account.isFavored()).build());
		requestDto.setName(StringValue.newBuilder().setValue(account.getName()).build());
		requestDto.setOwnership(converter.map(account.getOwnership(), ExactNumber.class));
		requestDto.setType(getType(account.getType()));

		return requestDto.build();
	}

	@Override
	public Class<Account> getSourceClass() {
		return Account.class;
	}

	@Override
	public Class<UpdateAccountRequest> getDestinationClass() {
		return UpdateAccountRequest.class;
	}

	private se.tink.grpc.v1.models.Account.Type getType(Type type) {
		switch (type) {
			case TYPE_LOAN:
				return se.tink.grpc.v1.models.Account.Type.TYPE_LOAN;
			case TYPE_DUMMY:
				return se.tink.grpc.v1.models.Account.Type.TYPE_DUMMY;
			case TYPE_OTHER:
				return se.tink.grpc.v1.models.Account.Type.TYPE_OTHER;
			case TYPE_CHECKING:
				return se.tink.grpc.v1.models.Account.Type.TYPE_CHECKING;
			case TYPE_PENSION:
				return se.tink.grpc.v1.models.Account.Type.TYPE_PENSION;
			case TYPE_SAVINGS:
				return se.tink.grpc.v1.models.Account.Type.TYPE_SAVINGS;
			case TYPE_CREDIT_CARD:
				return se.tink.grpc.v1.models.Account.Type.TYPE_CREDIT_CARD;
			case TYPE_EXTERNAL:
				return se.tink.grpc.v1.models.Account.Type.TYPE_EXTERNAL;
			case TYPE_MORTGAGE:
				return se.tink.grpc.v1.models.Account.Type.TYPE_MORTGAGE;
			case TYPE_INVESTMENT:
				return se.tink.grpc.v1.models.Account.Type.TYPE_INVESTMENT;
			case TYPE_UNKNOWN:
			default:
				return se.tink.grpc.v1.models.Account.Type.TYPE_UNKNOWN;
		}
	}

}
