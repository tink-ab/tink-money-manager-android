package se.tink.converter.account;

import se.tink.converter.ModelConverter;
import se.tink.core.models.Images;
import se.tink.core.models.account.Account;
import com.tink.model.misc.Amount;
import com.tink.model.misc.ExactNumber;
import se.tink.modelConverter.AbstractConverter;

public class AccountToAccountDTOConverter extends
	AbstractConverter<se.tink.grpc.v1.models.Account, Account> {

	private final ModelConverter converter;

	public AccountToAccountDTOConverter(ModelConverter modelConverter) {
		converter = modelConverter;
	}

	@Override
	public Account convert(se.tink.grpc.v1.models.Account source) {
		Account destination = new Account();
		destination.setBalance(converter.map(source.getBalance(), Amount.class));
		destination.setAccountNumber(source.getAccountNumber());
		destination.setCredentialId(source.getCredentialId());
		destination.setExcluded(source.getExcluded());
		destination.setClosed(source.getClosed());
		destination.setFavored(source.getFavored());
		destination.setId(source.getId());
		destination.setName(source.getName());
		destination.setOwnership(converter.map(source.getOwnership(), ExactNumber.class));

		destination.setType(getType(source.getType()));
		destination.setIdentifiers(source.getIdentifiersList());

		destination.setTransactional(source.getTransactional());
		if (source.getImages() != null) {
			destination.setImages(converter.map(source.getImages(), Images.class));
		}

		return destination;
	}

	@Override
	public Class<se.tink.grpc.v1.models.Account> getSourceClass() {
		return se.tink.grpc.v1.models.Account.class;
	}

	@Override
	public Class<Account> getDestinationClass() {
		return Account.class;
	}

	private static Account.Type getType(se.tink.grpc.v1.models.Account.Type type) {
		switch (type) {
			case TYPE_CHECKING:
				return Account.Type.TYPE_CHECKING;
			case TYPE_CREDIT_CARD:
				return Account.Type.TYPE_CREDIT_CARD;
			case TYPE_DUMMY:
				return Account.Type.TYPE_DUMMY;
			case TYPE_EXTERNAL:
				return Account.Type.TYPE_EXTERNAL;
			case TYPE_INVESTMENT:
				return Account.Type.TYPE_INVESTMENT;
			case TYPE_LOAN:
				return Account.Type.TYPE_LOAN;
			case TYPE_MORTGAGE:
				return Account.Type.TYPE_MORTGAGE;
			case TYPE_OTHER:
				return Account.Type.TYPE_OTHER;
			case TYPE_PENSION:
				return Account.Type.TYPE_PENSION;
			case TYPE_SAVINGS:
				return Account.Type.TYPE_SAVINGS;
			default:
			case TYPE_UNKNOWN:
				return Account.Type.TYPE_UNKNOWN;
		}
	}
}
