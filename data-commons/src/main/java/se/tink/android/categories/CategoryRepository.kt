package se.tink.android.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tink.annotations.PfmScope
import com.tink.model.category.CategoryTree
import com.tink.service.category.CategoryService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import javax.inject.Inject

@PfmScope
class CategoryRepository @Inject constructor(private val service: CategoryService) {

    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // TODO: Don't expose LiveData directly from a repository. They belong in ViewModels.
    // Perhaps use a StateFlow instead (or wait until it's out of experimental).


    private val _categories = object : MutableLiveData<CategoryTree>() {
        override fun onActive() {
            refresh()
        }

        override fun onInactive() {
            // Not sure if this is really needed since we do a [postValue] in [onActive] anyway...
            scope.coroutineContext.cancelChildren()
        }
    }
    val categories: LiveData<CategoryTree> = _categories

    fun refresh() {
        scope.launch {
            try {
                _categories.postValue(service.getCategoryTree())
            } catch (e: IllegalStateException) {
                // Fail silently if category tree didn't manage to update.
            }
        }
    }
}