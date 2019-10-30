package se.tink.repository.manager;

import com.google.common.collect.Lists;
import java.util.List;
import javax.inject.Inject;
import se.tink.converter.ModelConverter;
import se.tink.core.models.category.CategoryTree;
import se.tink.repository.MutationHandler;
import se.tink.repository.ObjectChangeObserver;
import se.tink.repository.cache.CategoryTreeCache;
import se.tink.repository.service.CategoryService;
import se.tink.repository.service.StreamingService;

public class CategoryServiceCachedImpl implements CategoryService {

	private final ModelConverter converter;
	private CategoryTreeCache cache;
	private final StreamingService service;
	private final List<ObjectChangeObserver<CategoryTree>> changeObserverers;
	private CategoryTree categories;

	@Inject
	public CategoryServiceCachedImpl(StreamingService stub, ModelConverter converter,
		CategoryTreeCache cache) {
		this.service = stub;
		this.converter = converter;
		this.cache = cache;
		changeObserverers = Lists.newArrayList();
		changeObserverers.add(cache);
		setupStreamingService();
	}

	@Override
	public void list(MutationHandler<CategoryTree> handler) {
		handler.onNext(categories);
		handler.onCompleted();
	}


	private void setupStreamingService() {
		service.subscribeForCategories(new ObjectChangeObserver<CategoryTree>() {
			@Override
			public void onCreate(CategoryTree item) {
				categories = item;
				for (int i = changeObserverers.size() - 1; i >= 0; i--) {
					ObjectChangeObserver<CategoryTree> changeObserver = changeObserverers.get(i);
					changeObserver.onCreate(item);
				}
			}

			@Override
			public void onRead(CategoryTree item) {
				categories = item;
				for (int i = changeObserverers.size() - 1; i >= 0; i--) {
					ObjectChangeObserver<CategoryTree> changeObserver = changeObserverers.get(i);
					changeObserver.onRead(item);
				}
			}

			@Override
			public void onUpdate(CategoryTree item) {
				categories = item;
				for (int i = changeObserverers.size() - 1; i >= 0; i--) {
					ObjectChangeObserver<CategoryTree> changeObserver = changeObserverers.get(i);
					changeObserver.onUpdate(item);
				}
			}

			@Override
			public void onDelete(CategoryTree item) {
				categories = item;
				for (int i = changeObserverers.size() - 1; i >= 0; i--) {
					ObjectChangeObserver<CategoryTree> changeObserver = changeObserverers.get(i);
					changeObserver.onDelete(item);
				}
			}
		});
	}

	@Override
	public void subscribe(ObjectChangeObserver<CategoryTree> listener) {
		changeObserverers.add(listener);
		readFromCache(listener);
	}

	@Override
	public void unsubscribe(ObjectChangeObserver<CategoryTree> listener) {
		changeObserverers.remove(listener);
	}


	private void readFromCache(final ObjectChangeObserver<CategoryTree> listener) {

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				CategoryTree categories = cache.getTree();
				if (categories != null && categories.getExpenses() != null) {
					listener.onRead(categories);
				}
			}
		});
		t.start();
	}
}