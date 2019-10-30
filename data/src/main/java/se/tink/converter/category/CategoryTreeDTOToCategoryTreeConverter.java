package se.tink.converter.category;

import se.tink.converter.ModelConverter;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.Category;
import se.tink.core.models.category.CategoryTree;
import se.tink.modelConverter.AbstractConverter;

public class CategoryTreeDTOToCategoryTreeConverter extends
	AbstractConverter<se.tink.grpc.v1.models.CategoryTree, CategoryTree> {

	private ModelConverter modelConverter;

	public CategoryTreeDTOToCategoryTreeConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public CategoryTree convert(se.tink.grpc.v1.models.CategoryTree source) {
		CategoryTree tree = new CategoryTree();
		tree.setExpenses(modelConverter.map(source.getExpenses(), Category.class));
		tree.setIncome(modelConverter.map(source.getIncome(), Category.class));
		tree.setTransfers(modelConverter.map(source.getTransfers(), Category.class));

		return tree;
	}

	@Override
	public Class<se.tink.grpc.v1.models.CategoryTree> getSourceClass() {
		return se.tink.grpc.v1.models.CategoryTree.class;
	}

	@Override
	public Class<CategoryTree> getDestinationClass() {
		return CategoryTree.class;
	}
}
