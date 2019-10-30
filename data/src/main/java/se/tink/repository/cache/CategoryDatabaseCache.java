package se.tink.repository.cache;

import java.util.List;
import se.tink.converter.ModelConverter;
import se.tink.core.models.Category;
import se.tink.repository.cache.database.CacheDatabase;
import se.tink.repository.cache.models.CategoryDao;
import se.tink.repository.cache.models.CategoryEntity;

public class CategoryDatabaseCache extends AbstractDatabaseCache<List<Category>> {

	private CategoryDao dao;
	private final ModelConverter modelConverter;

	public CategoryDatabaseCache(CacheDatabase cacheDatabase, ModelConverter modelConverter) {
		super(cacheDatabase);
		this.dao = cacheDatabase.categoryDao();
		this.modelConverter = modelConverter;
	}

	@Override
	public synchronized List<Category> read() {
		List<Category> topLevelCategories = modelConverter
			.map(dao.getTopLevelCategories(), Category.class);
		for (int i = 0; i < topLevelCategories.size(); i++) {
			addChildren(topLevelCategories.get(i));
		}
		return topLevelCategories;
	}

	private synchronized void addChildren(Category parentCategory) {
		List<Category> children = modelConverter
			.map(dao.getChildrenForCode(parentCategory.getCode()), Category.class);
		for (Category child : children) {
			child.setParent(parentCategory);
			addChildren(child);
		}
		parentCategory.setChildren(children);
	}

	@Override
	public synchronized void clearAndInsert(List<Category> items) {
		dao.clear();
		insert(items);
	}

	@Override
	public synchronized void insert(List<Category> items) {
		List<CategoryEntity> entities = modelConverter.map(items, CategoryEntity.class);
		dao.insertAll(entities);
		for (Category item : items) {
			List<Category> children = item.getChildren();
			if (children != null && children.size() > 0) {
				insert(children);
			}
		}
	}

	@Override
	public synchronized void update(List<Category> items) {
		List<CategoryEntity> entities = modelConverter.map(items, CategoryEntity.class);
		dao.updateAll(entities);
	}

	@Override
	public synchronized void delete(List<Category> items) {
		List<CategoryEntity> entities = modelConverter.map(items, CategoryEntity.class);
		dao.deleteAll(entities);
	}

	@Override
	public synchronized void clear() {
		dao.clear();
	}
}
