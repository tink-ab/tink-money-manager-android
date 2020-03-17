package se.tink.converter.account.accountDetails;

import se.tink.converter.ModelConverter;
import se.tink.core.models.account.AccountDetails;
import com.tink.model.misc.ExactNumber;
import se.tink.modelConverter.AbstractConverter;

public class AccountDetailsToAccountDetailsDTOConverter extends
	AbstractConverter<AccountDetails, AccountDetails> {

	private ModelConverter modelConverter;

	public AccountDetailsToAccountDetailsDTOConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public AccountDetails convert(AccountDetails source) {
		AccountDetails destination = new AccountDetails();
		destination.setInterest(modelConverter.map(source.getInterest(), ExactNumber.class));
		destination
			.setNumMonthsBound(modelConverter.map(source.getNumMonthsBound(), Integer.class));
		destination.setType(getType(source.getType()));
		return destination;
	}

	@Override
	public Class<AccountDetails> getSourceClass() {
		return AccountDetails.class;
	}

	@Override
	public Class<AccountDetails> getDestinationClass() {
		return AccountDetails.class;
	}

	private AccountDetails.Type getType(AccountDetails.Type type) {
		switch (type) {
			case TYPE_BLANCO:
				return AccountDetails.Type.TYPE_BLANCO;
			case TYPE_LAND:
				return AccountDetails.Type.TYPE_LAND;
			case TYPE_MEMBERSHIP:
				return AccountDetails.Type.TYPE_MEMBERSHIP;
			case TYPE_MORTGAGE:
				return AccountDetails.Type.TYPE_MORTGAGE;
			case TYPE_OTHER:
				return AccountDetails.Type.TYPE_OTHER;
			case TYPE_STUDENT:
				return AccountDetails.Type.TYPE_STUDENT;
			case TYPE_VEHICLE:
				return AccountDetails.Type.TYPE_VEHICLE;
			case TYPE_UNKNOWN:
			default:
				return AccountDetails.Type.TYPE_UNKNOWN;
		}
	}
}
