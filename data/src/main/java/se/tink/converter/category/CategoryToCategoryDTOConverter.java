package se.tink.converter.category;

import se.tink.converter.ModelConverter;
import se.tink.core.models.Category;
import se.tink.grpc.v1.models.CategoryNode;
import se.tink.modelConverter.AbstractConverter;

public class CategoryToCategoryDTOConverter extends AbstractConverter<Category, CategoryNode> {

	private final ModelConverter modelConverter;

	public CategoryToCategoryDTOConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public CategoryNode convert(Category source) {
		CategoryNode.Builder destination = CategoryNode.newBuilder();
		destination.setCode(source.getCode());
		destination.setName(source.getName());
		destination.setSortOrder(source.getSortOrder());
		return destination.build();
	}

	@Override
	public Class<Category> getSourceClass() {
		return Category.class;
	}

	@Override
	public Class<CategoryNode> getDestinationClass() {
		return CategoryNode.class;
	}
}
