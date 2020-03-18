package se.tink.android.categories

import androidx.lifecycle.MutableLiveData
import com.tink.annotations.PfmScope
import com.tink.model.category.CategoryTree
import com.tink.service.category.CategoryService
import com.tink.service.observer.ChangeObserver
import javax.inject.Inject

@PfmScope
class CategoryRepository @Inject constructor(service: CategoryService) {

    val categories: MutableLiveData<CategoryTree> = object : MutableLiveData<CategoryTree>() {
        override fun onActive() = service.subscribe(categoryTreeObserver)
        override fun onInactive() = service.unsubscribe(categoryTreeObserver)
    }

    private val categoryTreeObserver = object : ChangeObserver<CategoryTree> {
        override fun onCreate(items: CategoryTree) = categories.postValue(items)
        override fun onRead(items: CategoryTree) = categories.postValue(items)
        override fun onUpdate(items: CategoryTree) = categories.postValue(items)
        override fun onDelete(items: CategoryTree) {}
    }
}