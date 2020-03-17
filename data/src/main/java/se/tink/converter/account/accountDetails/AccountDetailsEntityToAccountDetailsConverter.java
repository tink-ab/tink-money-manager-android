package se.tink.converter.account.accountDetails;


import se.tink.converter.ModelConverter;
import se.tink.core.models.account.AccountDetails;
import com.tink.model.misc.ExactNumber;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.models.AccountDetailsEntity;

public class AccountDetailsEntityToAccountDetailsConverter extends
	AbstractConverter<AccountDetailsEntity, AccountDetails> {

	private ModelConverter modelConverter;

	public AccountDetailsEntityToAccountDetailsConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public AccountDetails convert(AccountDetailsEntity source) {
		AccountDetails destination = new AccountDetails();
		destination.setNumMonthsBound(source.getNumMonthsBound());

		ExactNumber exactNumber = new ExactNumber(source.getInterestValue(),
			source.getInterestScale());
		destination.setInterest(exactNumber);

		destination.setType(AccountDetails.Type.valueOf(source.getDetailsType()));

		return destination;
	}

	@Override
	public Class<AccountDetailsEntity> getSourceClass() {
		return AccountDetailsEntity.class;
	}

	@Override
	public Class<AccountDetails> getDestinationClass() {
		return AccountDetails.class;
	}
}
