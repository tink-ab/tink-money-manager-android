package se.tink.repository.service;

import se.tink.core.models.category.CategoryTree;
import se.tink.repository.MutationHandler;
import se.tink.repository.ObjectChangeObserver;

public interface CategoryService extends TinkService {

	void list(MutationHandler<CategoryTree> handler);

	void subscribe(ObjectChangeObserver<CategoryTree> listener);

	void unsubscribe(ObjectChangeObserver<CategoryTree> listener);
}
