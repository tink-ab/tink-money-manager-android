package com.tink.moneymanagerui.overview.charts

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tink.moneymanagerui.BaseFragment
import com.tink.moneymanagerui.R
import com.tink.moneymanagerui.tracking.ScreenEvent
import com.tink.moneymanagerui.view.TreeListSelectionAdapter
import com.tink.moneymanagerui.view.TreeListSelectionItem
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.tink_fragment_select_category.view.*
import com.tink.model.category.Category
import com.tink.moneymanagerui.MoneyManagerFeatureType

private const val ARG_TYPE = "arg_type"
private const val ARG_CATEGORY_ID = "arg_category"
private const val ARG_OPTIONS = "arg_options"

internal class CategorySelectionFragment : BaseFragment() {
    private val type by lazy {
        arguments?.getSerializable(ARG_TYPE) as? Category.Type ?: Category.Type.EXPENSE
    }
    private val checkedCategoryId by lazy { arguments?.getString(ARG_CATEGORY_ID) }
    private val featureType: MoneyManagerFeatureType? by lazy { arguments?.getSerializable(ARG_MONEY_MANAGER_FEATURE_TYPE) as? MoneyManagerFeatureType }
    private val options: Options by lazy { requireNotNull(arguments?.getParcelable<Options>(ARG_OPTIONS) ) }
    private val viewModel by lazy {
        ViewModelProviders.of(
            this,
            viewModelFactory
        )[CategorySelectionViewModel::class.java]
    }
    private val adapter: TreeListSelectionAdapter = TreeListSelectionAdapter()

    override fun getLayoutId(): Int = R.layout.tink_fragment_select_category
    override fun needsLoginToBeAuthorized(): Boolean = true
    override fun getScreenEvent(): ScreenEvent = ScreenEvent.CATEGORY_SELECTION
    override fun hasToolbar(): Boolean = true
    override fun doNotRecreateView(): Boolean = false
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
                    val selectedItem = checkedCategoryId?.let { id ->
                        items
                            .flatMap {
                                if (it is TreeListSelectionItem.TopLevelItem) {
                                    listOf(it) + it.children
                                } else {
                                    listOf(it)
                                }
                            }
                            .find { it.id == id }
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

    override fun getMoneyManagerFeatureType() = featureType

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
            categoryId: String?,
            options: Options = Options(),
            moneyManagerFeatureType: MoneyManagerFeatureType? = null
        ): CategorySelectionFragment {
            return CategorySelectionFragment().apply {
                arguments = bundleOf(
                    ARG_TYPE to type,
                    ARG_CATEGORY_ID to categoryId,
                    ARG_OPTIONS to options,
                    ARG_MONEY_MANAGER_FEATURE_TYPE to moneyManagerFeatureType
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

internal interface CategorySelectionListener {
    fun onCategorySelected(updatedCategoryId: String)
    fun onCategorySelectionCancelled() { }
}
