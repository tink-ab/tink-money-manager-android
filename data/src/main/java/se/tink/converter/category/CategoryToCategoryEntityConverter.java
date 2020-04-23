package se.tink.converter.category;

import se.tink.core.models.Category;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.models.CategoryEntity;

public class CategoryToCategoryEntityConverter extends AbstractConverter<Category, CategoryEntity> {

	@Override
	public CategoryEntity convert(Category source) {
		CategoryEntity destination = new CategoryEntity();
		destination.setDefaultChild(source.isDefaultChild());
		destination.setType(source.getType().name());
		destination.setCode(source.getCode());
		destination.setName(source.getName());
		destination.setSortOrder(source.getSortOrder());
		if (source.getParent() != null) {
			destination.setParentCode(source.getParent().getCode());
		}
		return destination;
	}

	@Override
	public Class<Category> getSourceClass() {
		return Category.class;
	}

	@Override
	public Class<CategoryEntity> getDestinationClass() {
		return CategoryEntity.class;
	}
}
