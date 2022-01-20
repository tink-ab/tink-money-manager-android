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
import com.tink.service.network.ResponseState
import com.tink.service.network.ErrorState
import com.tink.service.network.LoadingState
import com.tink.service.network.SuccessState
import java.lang.Exception
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
            } catch (e: Exception) {
                // Fail silently if category tree didn't manage to update.
            }
        }
    }

    private val _categoriesState = object : MutableLiveData<ResponseState<CategoryTree>>() {
        override fun onActive() {
            refresh()
        }

        fun refresh() {
            refreshState()
        }
    }

    val categoriesState: LiveData<ResponseState<CategoryTree>> = _categoriesState

    fun refreshState() {
        _categoriesState.postValue(LoadingState)
        scope.launch {
            try {
                val categoryTree = service.getCategoryTree()
                _categoriesState.postValue(SuccessState(categoryTree))
            } catch (e: Exception) {
                _categoriesState.postValue(ErrorState(e))
            }
        }
    }
}