package se.tink.converter.account;


import se.tink.converter.ModelConverter;
import se.tink.core.models.account.Account;
import se.tink.grpc.v1.models.CurrencyDenominatedAmount;
import se.tink.grpc.v1.models.ExactNumber;
import se.tink.grpc.v1.models.Images;
import se.tink.modelConverter.AbstractConverter;

public class AccountDTOToAccountConverter extends
	AbstractConverter<Account, se.tink.grpc.v1.models.Account> {

	private final ModelConverter converter;

	public AccountDTOToAccountConverter(ModelConverter modelConverter) {
		this.converter = modelConverter;
	}

	@Override
	public se.tink.grpc.v1.models.Account convert(Account source) {
		se.tink.grpc.v1.models.Account.Builder destination = se.tink.grpc.v1.models.Account
			.newBuilder();
		destination.setBalance(converter.map(source.getBalance(), CurrencyDenominatedAmount.class));
		destination.setAccountNumber(source.getAccountNumber());
		destination.setCredentialId(source.getCredentialId());
		destination.setExcluded(source.isExcluded());
		destination.setFavored(source.isFavored());
		destination.setId(source.getId());
		destination.setName(source.getName());
		destination.setOwnership(converter.map(source.getOwnership(), ExactNumber.class));
		destination.setType(getType(source.getType()));
		destination.setTransactional(source.isTransactional());
		destination.setClosed(source.isClosed());
		destination.setImages(converter.map(source.getImages(), Images.class));

		return destination.build();
	}

	@Override
	public Class<Account> getSourceClass() {
		return Account.class;
	}

	@Override
	public Class<se.tink.grpc.v1.models.Account> getDestinationClass() {
		return se.tink.grpc.v1.models.Account.class;
	}

	private static se.tink.grpc.v1.models.Account.Type getType(Account.Type type) {
		switch (type) {
			case TYPE_CHECKING:
				return se.tink.grpc.v1.models.Account.Type.TYPE_CHECKING;
			case TYPE_CREDIT_CARD:
				return se.tink.grpc.v1.models.Account.Type.TYPE_CREDIT_CARD;
			case TYPE_DUMMY:
				return se.tink.grpc.v1.models.Account.Type.TYPE_DUMMY;
			case TYPE_EXTERNAL:
				return se.tink.grpc.v1.models.Account.Type.TYPE_EXTERNAL;
			case TYPE_INVESTMENT:
				return se.tink.grpc.v1.models.Account.Type.TYPE_INVESTMENT;
			case TYPE_LOAN:
				return se.tink.grpc.v1.models.Account.Type.TYPE_LOAN;
			case TYPE_MORTGAGE:
				return se.tink.grpc.v1.models.Account.Type.TYPE_MORTGAGE;
			case TYPE_OTHER:
				return se.tink.grpc.v1.models.Account.Type.TYPE_OTHER;
			case TYPE_PENSION:
				return se.tink.grpc.v1.models.Account.Type.TYPE_PENSION;
			case TYPE_SAVINGS:
				return se.tink.grpc.v1.models.Account.Type.TYPE_SAVINGS;
			default:
			case TYPE_UNKNOWN:
				return se.tink.grpc.v1.models.Account.Type.TYPE_UNKNOWN;
		}
	}
}