package se.tink.converter.account.accountDetails;


import se.tink.converter.ModelConverter;
import se.tink.core.models.account.AccountDetails;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.models.AccountDetailsEntity;

public class AccountDetailsToAccountDetailsEntityConverter extends
	AbstractConverter<AccountDetails, AccountDetailsEntity> {

	private ModelConverter modelConverter;

	public AccountDetailsToAccountDetailsEntityConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public AccountDetailsEntity convert(AccountDetails source) {
		AccountDetailsEntity destination = new AccountDetailsEntity();
		destination.setNumMonthsBound(source.getNumMonthsBound());

		long interestValue = source.getInterest().getUnscaledValue();
		long interestScale = source.getInterest().getScale();
		destination.setInterestValue(interestValue);
		destination.setInterestScale(interestScale);

		destination.setDetailsType(source.getType().name());

		return destination;
	}

	@Override
	public Class<AccountDetails> getSourceClass() {
		return AccountDetails.class;
	}

	@Override
	public Class<AccountDetailsEntity> getDestinationClass() {
		return AccountDetailsEntity.class;
	}
}
