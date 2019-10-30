package se.tink.converter.account;

import se.tink.converter.ModelConverter;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.models.AccountSourceIdentifierEntity;

public class AccountSourceIdentifierEntityToStringConverter extends
	AbstractConverter<AccountSourceIdentifierEntity, String> {

	private final ModelConverter converter;

	public AccountSourceIdentifierEntityToStringConverter(ModelConverter converter) {
		this.converter = converter;
	}

	@Override
	public String convert(AccountSourceIdentifierEntity source) {
		return source.getIdentifier();
	}

	@Override
	public Class<AccountSourceIdentifierEntity> getSourceClass() {
		return AccountSourceIdentifierEntity.class;
	}

	@Override
	public Class<String> getDestinationClass() {
		return String.class;
	}
}
