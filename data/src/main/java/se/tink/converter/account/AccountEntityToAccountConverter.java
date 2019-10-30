package se.tink.converter.account;


import se.tink.converter.ModelConverter;
import se.tink.core.models.Images;
import se.tink.core.models.account.Account;
import se.tink.core.models.account.AccountDetails;
import se.tink.core.models.misc.Amount;
import se.tink.core.models.misc.ExactNumber;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.models.AccountEntity;

public class AccountEntityToAccountConverter extends AbstractConverter<AccountEntity, Account> {

	private ModelConverter modelConverter;

	public AccountEntityToAccountConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public Account convert(AccountEntity source) {
		Account destination = new Account();
		destination.setId(source.getId());
		destination.setAccountNumber(source.getAccountNumber());
		destination.setCredentialId(source.getCredentialId());
		destination.setName(source.getName());
		destination.setFavored(source.isFavored());
		destination.setTransactional(source.isTransactional());
		destination.setExcluded(source.isExcluded());
		destination.setClosed(source.isClosed());

		if (source.getType() != null) {
			destination.setType(Account.Type.valueOf(source.getType()));
		}

		if (source.getOwnership() != null) {
			ExactNumber ownership = modelConverter.map(source.getOwnership(), ExactNumber.class);
			destination.setOwnership(ownership);
		}

		if (source.getBalance() != null) {
			Amount amount = modelConverter.map(source.getBalance(), Amount.class);
			destination.setBalance(amount);
		}

		if (source.getAccountDetailsEntity() != null) {
			AccountDetails accountDetails = modelConverter
				.map(source.getAccountDetailsEntity(), AccountDetails.class);
			destination.setDetails(accountDetails);
		}

		if (source.getImagesEntity() != null) {
			Images imagesEntity = modelConverter.map(source.getImagesEntity(), Images.class);
			destination.setImages(imagesEntity);
		}

		return destination;
	}

	@Override
	public Class<AccountEntity> getSourceClass() {
		return AccountEntity.class;
	}

	@Override
	public Class<Account> getDestinationClass() {
		return Account.class;
	}
}
