package se.tink.repository.cache.models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import se.tink.repository.cache.database.TableNames;

@Entity(tableName = TableNames.ACCOUNTS)
public class AccountEntity {

	@NonNull
	@PrimaryKey
	private String id;
	private String name;
	private String accountNumber;
	private String credentialId;

	private boolean excluded;
	private boolean favored;
	private boolean transactional;
	private boolean closed;

	private String type;

	@Embedded(prefix = "ownership")
	private ExactNumberEntity ownership;

	@Embedded(prefix = "balance")
	private AmountEntity balance;

	@Embedded
	private AccountDetailsEntity accountDetailsEntity;

	@Embedded
	private ImagesEntity imagesEntity;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCredentialId() {
		return credentialId;
	}

	public void setCredentialId(String credentialId) {
		this.credentialId = credentialId;
	}

	public boolean isExcluded() {
		return excluded;
	}

	public void setExcluded(boolean excluded) {
		this.excluded = excluded;
	}

	public boolean isFavored() {
		return favored;
	}

	public void setFavored(boolean favored) {
		this.favored = favored;
	}

	public boolean isTransactional() {
		return transactional;
	}

	public void setTransactional(boolean transactional) {
		this.transactional = transactional;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public AmountEntity getBalance() {
		return balance;
	}

	public void setBalance(AmountEntity balance) {
		this.balance = balance;
	}

	public AccountDetailsEntity getAccountDetailsEntity() {
		return accountDetailsEntity;
	}

	public void setAccountDetailsEntity(AccountDetailsEntity accountDetailsEntity) {
		this.accountDetailsEntity = accountDetailsEntity;
	}

	public ImagesEntity getImagesEntity() {
		return imagesEntity;
	}

	public void setImagesEntity(ImagesEntity imagesEntity) {
		this.imagesEntity = imagesEntity;
	}

	public ExactNumberEntity getOwnership() {
		return ownership;
	}

	public void setOwnership(ExactNumberEntity ownership) {
		this.ownership = ownership;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}
}
