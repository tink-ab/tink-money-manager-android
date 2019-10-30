package se.tink.converter.category;

import se.tink.core.models.Category;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.models.CategoryEntity;


public class CategoryEntityToCategoryConverter extends AbstractConverter<CategoryEntity, Category> {

	public Category convert(CategoryEntity source) {
		Category destination = new Category();
		destination.setDefaultChild(source.isDefaultChild());
		destination.setType(Category.Type.valueOf(source.getType()));
		destination.setCode(source.getCode());
		destination.setName(source.getName());
		destination.setSortOrder(source.getSortOrder());
		return destination;
	}

	@Override
	public Class<CategoryEntity> getSourceClass() {
		return CategoryEntity.class;
	}

	@Override
	public Class<Category> getDestinationClass() {
		return Category.class;
	}
}
