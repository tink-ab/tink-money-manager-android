package se.tink.converter.account;

import java.util.ArrayList;
import java.util.List;
import se.tink.converter.ModelConverter;
import se.tink.core.models.account.Account;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.models.AccountEntity;
import se.tink.repository.cache.models.AccountEntityAndSourceIdentifier;
import se.tink.repository.cache.models.AccountSourceIdentifierEntity;

public class AccountToAccountEntityAndAccountIdentifierEntityConverter extends
	AbstractConverter<Account, AccountEntityAndSourceIdentifier> {

	private final ModelConverter modelConverter;

	public AccountToAccountEntityAndAccountIdentifierEntityConverter(ModelConverter converter) {
		this.modelConverter = converter;
	}

	@Override
	public AccountEntityAndSourceIdentifier convert(Account source) {

		AccountEntity accountEntity = modelConverter.map(source, AccountEntity.class);

		List<AccountSourceIdentifierEntity> accountSourceIdentifierEntities = mapIdentifiers(
			source.getIdentifiers(), source.getId());
		AccountEntityAndSourceIdentifier accountEntityAndSourceIdentifier = new AccountEntityAndSourceIdentifier();
		accountEntityAndSourceIdentifier.setEntity(accountEntity);
		accountEntityAndSourceIdentifier
			.setSourceIdentifierEntities(accountSourceIdentifierEntities);

		return accountEntityAndSourceIdentifier;
	}

	@Override
	public Class<Account> getSourceClass() {
		return Account.class;
	}

	@Override
	public Class<AccountEntityAndSourceIdentifier> getDestinationClass() {
		return AccountEntityAndSourceIdentifier.class;
	}

	private List<AccountSourceIdentifierEntity> mapIdentifiers(List<String> accountIdentifiers,
		String accountId) {

		List<AccountSourceIdentifierEntity> accountSourceIdentifierEntities = new ArrayList<>();
		for (String accountIdentifier : accountIdentifiers) {
			AccountSourceIdentifierEntity accountSourceIdentifierEntity = new AccountSourceIdentifierEntity();
			accountSourceIdentifierEntity.setAccountId(accountId);
			accountSourceIdentifierEntity.setIdentifier(accountIdentifier);
			accountSourceIdentifierEntities.add(accountSourceIdentifierEntity);
		}
		return accountSourceIdentifierEntities;
	}

}
