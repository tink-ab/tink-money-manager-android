package com.tink.pfmsdk.overview.charts

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tink.pfmsdk.BaseFragment
import com.tink.pfmsdk.R
import com.tink.pfmsdk.tracking.ScreenEvent
import com.tink.pfmsdk.theme.getCategorySelectionThemeForType
import com.tink.pfmsdk.view.TreeListSelectionAdapter
import com.tink.pfmsdk.view.TreeListSelectionItem
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_select_category.view.*
import se.tink.core.models.Category

private const val ARG_TYPE = "arg_type"
private const val ARG_CATEGORY_CODE = "arg_category"
private const val ARG_OPTIONS = "arg_options"

class CategorySelectionFragment : BaseFragment() {
    private val type by lazy {
        arguments?.getSerializable(ARG_TYPE) as? Category.Type ?: Category.Type.TYPE_EXPENSES
    }
    private val checkedCategoryCode by lazy { arguments?.getString(ARG_CATEGORY_CODE) }
    private val options: Options by lazy { requireNotNull(arguments?.getParcelable<Options>(ARG_OPTIONS) ) }
    private val viewModel by lazy {
        ViewModelProviders.of(
            this,
            viewModelFactory
        )[CategorySelectionViewModel::class.java]
    }
    private val ownTheme by lazy { getCategorySelectionThemeForType(context!!, type) }
    private val adapter: TreeListSelectionAdapter = TreeListSelectionAdapter()

    override fun getLayoutId(): Int = R.layout.fragment_select_category
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun getScreenEvent(): ScreenEvent = ScreenEvent.CATEGORY_SELECTION
    override fun hasToolbar(): Boolean = false
    override fun doNotRecreateView(): Boolean = false
    override fun getTheme(): Theme = ownTheme
    override fun getTitle(): String = ""
    override fun viewReadyAfterLayout(): Boolean = false

    override fun authorizedOnCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {

        if (!options.dropdownToolbarAppearance) {
            view.category.setCompoundDrawables(null, null, null, null)
        }

        val categories = viewModel.getCategoryItems(
            type,
            includeTransfers = options.includeTransferTypes,
            includeTopLevel = options.includeTopLevelItem
        )
        categories.observe(viewLifecycle, object : Observer<List<TreeListSelectionItem>> {
            override fun onChanged(it: List<TreeListSelectionItem>?) {
                it?.let { items ->
                    categories.removeObserver(this)
                    view.list.adapter = adapter
                    val selectedItem = checkedCategoryCode?.let { code ->
                        items
                            .flatMap {
                                if (it is TreeListSelectionItem.TopLevelItem) {
                                    listOf(it) + it.children
                                } else {
                                    listOf(it)
                                }
                            }
                            .find { it.id == code }
                    }
                    adapter.setData(items, selectedItem)
                    view.list.post { onViewReady() }
                }
            }
        })


        view.button.setOnClickListener {
            adapter.selectedItem?.let {
                (targetFragment as? CategorySelectionListener)?.onCategorySelected(it.id)
                fragmentCoordinator.popBackStack()
            }
        }
    }

    override fun onBackPressed(): Boolean {
        onCancelled()
        return super.onBackPressed()
    }

    override fun onUpPressed() {
        onCancelled()
        super.onUpPressed()
    }

    private fun onCancelled() =
        (targetFragment as? CategorySelectionListener)?.onCategorySelectionCancelled()

    companion object {
        fun newInstance(
            type: Category.Type,
            categoryCode: String?,
            options: Options = Options()
        ): CategorySelectionFragment {
            return CategorySelectionFragment().apply {
                arguments = bundleOf(
                    ARG_TYPE to type,
                    ARG_CATEGORY_CODE to categoryCode,
                    ARG_OPTIONS to options
                )
            }
        }
    }

    @Parcelize
    data class Options(
        val dropdownToolbarAppearance: Boolean = true,
        val includeTopLevelItem: Boolean = true,
        val includeTransferTypes: Boolean = false
    ) : Parcelable
}

interface CategorySelectionListener {
    fun onCategorySelected(updatedCategoryCode: String)
    fun onCategorySelectionCancelled() { }
}


