package se.tink.repository.cache.models;

import java.util.List;

public class AccountEntityAndSourceIdentifier {

	private AccountEntity entity;
	private List<AccountSourceIdentifierEntity> sourceIdentifierEntities;

	public AccountEntity getEntity() {
		return entity;
	}

	public void setEntity(AccountEntity entity) {
		this.entity = entity;
	}

	public void setSourceIdentifierEntities(
		List<AccountSourceIdentifierEntity> sourceIdentifierEntities) {
		this.sourceIdentifierEntities = sourceIdentifierEntities;
	}

	public List<AccountSourceIdentifierEntity> getSourceIdentifierEntities() {
		return sourceIdentifierEntities;
	}
}
