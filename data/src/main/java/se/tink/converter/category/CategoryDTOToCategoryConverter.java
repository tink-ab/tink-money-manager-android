package se.tink.converter.category;

import static se.tink.core.models.Category.Type.TYPE_EXPENSES;
import static se.tink.core.models.Category.Type.TYPE_INCOME;

import se.tink.converter.ModelConverter;
import se.tink.core.models.Category;
import se.tink.grpc.v1.models.CategoryNode;
import se.tink.modelConverter.AbstractConverter;

public class CategoryDTOToCategoryConverter extends
	AbstractConverter<CategoryNode, Category> {

	private final ModelConverter modelConverter;

	public CategoryDTOToCategoryConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public Category convert(se.tink.grpc.v1.models.CategoryNode source) {
		Category destination = new Category();
		destination.setType(getType(source.getCode()));
		destination.setCode(source.getCode());
//        destination.setParent(source.getParent());

		destination.setName(source.getName());
		destination.setSortOrder(source.getSortOrder());
		destination.setDefaultChild(source.getDefaultChild());

		if (source.getChildrenList() != null) {
			//noinspection unchecked
			destination.setChildren(modelConverter
				.map(source.getChildrenList(), Category.class));
			for (Category child : destination.getChildren()) {
				child.setParent(destination);
			}
		}

		return destination;
	}

	@Override
	public Class<CategoryNode> getSourceClass() {
		return CategoryNode.class;
	}

	@Override
	public Class<Category> getDestinationClass() {
		return Category.class;
	}

	private Category.Type getType(String code) {
		if (code.startsWith("expense")) {
			return TYPE_EXPENSES;
		} else if (code.startsWith("income")) {
			return TYPE_INCOME;
		} else {
			return Category.Type.TYPE_TRANSFER;
		}
	}
}
