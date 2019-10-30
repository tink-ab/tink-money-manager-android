package se.tink.android.categories

import androidx.lifecycle.MutableLiveData
import se.tink.core.models.category.CategoryTree
import se.tink.repository.ObjectChangeObserver
import se.tink.repository.service.CategoryService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(service: CategoryService) {

    val categories: MutableLiveData<CategoryTree> = object : MutableLiveData<CategoryTree>() {
        override fun onActive() = service.subscribe(categoryTreeObserver)
        override fun onInactive() = service.unsubscribe(categoryTreeObserver)
    }

    private val categoryTreeObserver = object : ObjectChangeObserver<CategoryTree> {
        override fun onCreate(item: CategoryTree) = categories.postValue(item)
        override fun onRead(item: CategoryTree) = categories.postValue(item)
        override fun onUpdate(item: CategoryTree) = categories.postValue(item)
        override fun onDelete(item: CategoryTree) {}
    }
}