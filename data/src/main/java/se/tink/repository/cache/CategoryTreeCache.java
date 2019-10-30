package se.tink.repository.cache;

import java.util.ArrayList;
import java.util.List;
import se.tink.core.models.Category;
import se.tink.core.models.category.CategoryTree;
import se.tink.privacy.Clearable;
import se.tink.privacy.DataWipeManager;
import se.tink.repository.ObjectChangeObserver;

public class CategoryTreeCache implements ObjectChangeObserver<CategoryTree>, Clearable {

	private Cache<List<Category>> categoryCache;

	public CategoryTreeCache(Cache<List<Category>> categoryCache) {
		this.categoryCache = categoryCache;
		DataWipeManager.sharedInstance().register(this);
	}

	public synchronized CategoryTree getTree() {
		CategoryTree tree = new CategoryTree();
		for (Category category : categoryCache.read()) {
			if (category.getType() == Category.Type.TYPE_EXPENSES) {
				tree.setExpenses(category);
			} else if (category.getType() == Category.Type.TYPE_INCOME) {
				tree.setIncome(category);
			} else if (category.getType() == Category.Type.TYPE_TRANSFER) {
				tree.setTransfers(category);
			}
		}
		return tree;
	}

	public synchronized void clearAllAndInsert(CategoryTree tree) {
		List<Category> categoryList = new ArrayList<>();
		categoryList.add(tree.getExpenses());
		categoryList.add(tree.getIncome());
		categoryList.add(tree.getTransfers());
		categoryCache.clearAndInsert(categoryList);
	}

	public synchronized void clear() {
		categoryCache.clear();
	}

	@Override
	public void onCreate(CategoryTree item) {
		clearAllAndInsert(item);
	}

	@Override
	public void onRead(CategoryTree item) {
		clearAllAndInsert(item);
	}

	@Override
	public void onUpdate(CategoryTree item) {
		clearAllAndInsert(item);
	}

	@Override
	public void onDelete(CategoryTree item) {
		clearAllAndInsert(item);
	}
}
