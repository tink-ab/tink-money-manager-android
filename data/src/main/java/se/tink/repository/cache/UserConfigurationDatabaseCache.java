package se.tink.repository.cache;

import java.util.List;
import se.tink.converter.ModelConverter;
import se.tink.core.models.user.UserConfiguration;
import se.tink.core.models.user.UserConfigurationI18NConfiguration;
import se.tink.repository.cache.database.CacheDatabase;
import se.tink.repository.cache.database.UserConfigurationDao;
import se.tink.repository.cache.models.FlagEntity;
import se.tink.repository.cache.models.I18ConfigurationEntity;

public class UserConfigurationDatabaseCache extends AbstractDatabaseCache<UserConfiguration> {

	private ModelConverter modelConverter;
	private UserConfigurationDao dao;

	public UserConfigurationDatabaseCache(CacheDatabase cacheDatabase,
		ModelConverter modelConverter) {
		super(cacheDatabase);
		this.modelConverter = modelConverter;
		dao = cacheDatabase.userConfigurationDao();
	}

	@Override
	public synchronized UserConfiguration read() {
		UserConfiguration configuration = new UserConfiguration();
		List<String> flags = modelConverter.map(dao.getFlags(), String.class);
		I18ConfigurationEntity i18ConfigurationEntity = dao.getI18Configuration();
		if (i18ConfigurationEntity != null) {
			UserConfigurationI18NConfiguration i18NConfiguration = modelConverter
				.map(i18ConfigurationEntity, UserConfigurationI18NConfiguration.class);
			configuration.setI18nConfiguration(i18NConfiguration);
		}
		configuration.setFlags(flags);
		return configuration;
	}

	@Override
	public synchronized void clearAndInsert(UserConfiguration item) {
		List<FlagEntity> flags = modelConverter.map(item.getFlags(), FlagEntity.class);
		I18ConfigurationEntity i18ConfigurationEntity = modelConverter
			.map(item.getI18nConfiguration(), I18ConfigurationEntity.class);
		dao.clearAndInsertAll(flags);
		dao.clearAndInsert(i18ConfigurationEntity);
	}

	@Override
	public synchronized void insert(UserConfiguration item) {
		List<FlagEntity> flags = modelConverter.map(item.getFlags(), FlagEntity.class);
		I18ConfigurationEntity i18ConfigurationEntity = modelConverter
			.map(item.getI18nConfiguration(), I18ConfigurationEntity.class);
		dao.insertAll(flags);
		dao.insert(i18ConfigurationEntity);
	}

	@Override
	public synchronized void update(UserConfiguration item) {
		List<FlagEntity> flags = modelConverter.map(item.getFlags(), FlagEntity.class);
		I18ConfigurationEntity i18ConfigurationEntity = modelConverter
			.map(item.getI18nConfiguration(), I18ConfigurationEntity.class);
		dao.updateAll(flags);
		dao.update(i18ConfigurationEntity);
	}

	@Override
	public synchronized void delete(UserConfiguration item) {
		List<FlagEntity> flags = modelConverter.map(item.getFlags(), FlagEntity.class);
		I18ConfigurationEntity i18ConfigurationEntity = modelConverter
			.map(item.getI18nConfiguration(), I18ConfigurationEntity.class);
		dao.delete(flags);
		dao.delete(i18ConfigurationEntity);
	}

	@Override
	public synchronized void clear() {
		dao.clearFlags();
		dao.clearI18Configuration();
	}
}
