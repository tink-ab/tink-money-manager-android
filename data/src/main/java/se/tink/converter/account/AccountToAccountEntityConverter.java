package se.tink.converter.account;


import se.tink.converter.ModelConverter;
import se.tink.core.models.account.Account;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.models.AccountDetailsEntity;
import se.tink.repository.cache.models.AccountEntity;
import se.tink.repository.cache.models.AmountEntity;
import se.tink.repository.cache.models.ExactNumberEntity;
import se.tink.repository.cache.models.ImagesEntity;

public class AccountToAccountEntityConverter extends AbstractConverter<Account, AccountEntity> {

	private ModelConverter modelConverter;

	public AccountToAccountEntityConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public AccountEntity convert(Account source) {
		AccountEntity destination = new AccountEntity();
		destination.setId(source.getId());
		destination.setAccountNumber(source.getAccountNumber());
		destination.setCredentialId(source.getCredentialId());
		destination.setName(source.getName());
		destination.setFavored(source.isFavored());
		destination.setTransactional(source.isTransactional());
		destination.setExcluded(source.isExcluded());
		destination.setClosed(source.isClosed());

		if (source.getType() != null) {
			destination.setType(source.getType().name());
		}

		if (source.getOwnership() != null) {
			ExactNumberEntity exactNumberEntity = modelConverter
				.map(source.getOwnership(), ExactNumberEntity.class);
			destination.setOwnership(exactNumberEntity);
		}

		if (source.getBalance() != null) {
			AmountEntity amountEntity = modelConverter.map(source.getBalance(), AmountEntity.class);
			destination.setBalance(amountEntity);
		}

		if (source.getDetails() != null) {
			AccountDetailsEntity accountDetailsEntity = modelConverter
				.map(source.getDetails(), AccountDetailsEntity.class);
			destination.setAccountDetailsEntity(accountDetailsEntity);
		}

		if (source.getImages() != null) {
			ImagesEntity imagesEntity = modelConverter.map(source.getImages(), ImagesEntity.class);
			destination.setImagesEntity(imagesEntity);
		}

		return destination;
	}

	@Override
	public Class<Account> getSourceClass() {
		return Account.class;
	}

	@Override
	public Class<AccountEntity> getDestinationClass() {
		return AccountEntity.class;
	}
}
