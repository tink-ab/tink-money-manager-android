package com.tink.pfmsdk.overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import com.google.common.collect.Lists;
import com.tink.pfmsdk.BaseFragment;
import com.tink.pfmsdk.R;
import com.tink.pfmsdk.analytics.AnalyticsScreen;
import com.tink.pfmsdk.transaction.StatusSubtitleMode;
import com.tink.pfmsdk.transaction.TransactionsListMetaData;
import com.tink.pfmsdk.util.FollowItemsUtils;
import com.tink.pfmsdk.view.CardGroupHeader;
import com.tink.pfmsdk.view.GroupPositionUtils;
import java.util.List;
import javax.inject.Inject;
import com.tink.pfmsdk.transaction.TransactionsListFragment;
import se.tink.core.models.category.CategoryTree;
import se.tink.core.models.follow.FollowItem;
import se.tink.core.models.transaction.Transaction;
import se.tink.repository.ChangeObserver;
import se.tink.repository.ObjectChangeObserver;
import se.tink.repository.SimpleObjectChangeObserver;
import se.tink.repository.service.CategoryService;
import se.tink.repository.service.FollowService;
import se.tink.repository.service.StreamingService;
import se.tink.repository.service.TransactionService;

public class OverviewFragment extends BaseFragment {

    @Inject
    Theme theme;

    @Inject
    StreamingService streamingService;

    @Inject
    CategoryService categoryService;

    @Inject
    FollowService followService;

    @Inject
    TransactionService transactionService;

    LinearLayout transactionContainer;

    private List<Transaction> latestTransactions;
    private List<FollowItem> savingsFollowItems = Lists.newArrayList();
    private List<FollowItem> expensesFollowItems = Lists.newArrayList();

    private CategoryTree categoryTree;
    private OverviewVM viewModel;

    @Override
    public int getLayoutId() {
        return R.layout.overview_fragment;
    }

    @Override
    public boolean needsLoginToBeAuthorized() {
        return true;
    }

    @Override
    protected Theme getTheme() {
        return theme;
    }

    @Override
    protected AnalyticsScreen getAnalyticsScreen() {
        return AnalyticsScreen.OVERVIEW;
    }

    @Override
    protected boolean hasToolbar() {
        return true;
    }

    @Override
    protected boolean doNotRecreateView() {
        return false;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.overview_title);
    }

    @Override
    protected boolean viewReadyAfterLayout() {
        return false;
    }

    @Override
    protected void onChildViewReady(BaseFragment child) {
        if (child instanceof OverviewChartFragment) {
            onViewReady();
        }
    }

    @Override
    public void authorizedOnCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {

        // TODO: PFMSDK: Don't use butterknife and using data binding after making this fragment kotlinized
        transactionContainer = view.findViewById(R.id.transactionContainer);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(OverviewVM.class);

        viewModel.getTransactions().observe(getViewLifecycleOwner(), value -> {
          latestTransactions = value;
          updateLatestTransactions();
        });

        categoryService.subscribe(categoryTreeObjectChangeObserver);

        followService.subscribe(followChangeObserver);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        categoryService.unsubscribe(categoryTreeObjectChangeObserver);

        followService.unsubscribe(followChangeObserver);
    }

    public interface Theme extends BaseFragment.Theme {

        CardGroupHeader.Theme getTransactionHeaderTheme();

        GroupPositionUtils.Theme getTransactionRowGroupPositionTheme();

        int getTransactionStatusBarColor();

        int getTransactionToolbarColor();
    }

    public void showTransactions() {
        TransactionsListMetaData metaData = new TransactionsListMetaData(
                getTheme().getTransactionStatusBarColor(),
                getTheme().getTransactionToolbarColor(),
                getString(R.string.overview_latest_transactions_title),
                false,
                null,
                null,
                true,
                StatusSubtitleMode.SHOW_REDUCED_AMOUNT,
                null
        );

        TransactionsListFragment fragment = TransactionsListFragment.newInstance(metaData);
        fragmentCoordinator.replace(fragment);
    }

    private ObjectChangeObserver<CategoryTree> categoryTreeObjectChangeObserver = new SimpleObjectChangeObserver<CategoryTree>() {
        @Override
        public void onRead(CategoryTree item) {
            categoryTree = item;

            if (latestTransactions != null) {
                runUiDependant(() -> updateLatestTransactions());
            }
        }
    };

    // TODO: PFMSDK: Don't use TransactionRow
//    private OnClickListener transactionRowOnclickListeer = v -> {
//        TransactionRow transactionRow = (TransactionRow) v;
//        Transaction transaction = transactionRow.getTransaction();
//        fragmentCoordinator.replace(
//                TransactionFragment.newInstance(transaction));
//    };

    private ChangeObserver<FollowItem> followChangeObserver = new ChangeObserver<FollowItem>() {
        @Override
        public void onCreate(List<FollowItem> items) {
            List<FollowItem> expensesFollowItemsToAdd = FollowItemsUtils
                    .filterExpensesFollowItemsWithTarget(items);
            expensesFollowItems = FollowItemsUtils
                    .add(expensesFollowItems, expensesFollowItemsToAdd);

            List<FollowItem> savingsFollowItemsToAdd = FollowItemsUtils
                    .filterSavingsFollowItemsWithTarget(items);
            savingsFollowItems = FollowItemsUtils.add(savingsFollowItems, savingsFollowItemsToAdd);
        }

        @Override
        public void onRead(List<FollowItem> items) {
            expensesFollowItems = FollowItemsUtils.filterExpensesFollowItemsWithTarget(items);
            savingsFollowItems = FollowItemsUtils.filterSavingsFollowItemsWithTarget(items);
        }

        @Override
        public void onUpdate(List<FollowItem> items) {
            List<FollowItem> expensesFollowItemsToBeUpdated = FollowItemsUtils
                    .filterExpensesFollowItemsWithTarget(items);
            expensesFollowItems = FollowItemsUtils
                    .replace(expensesFollowItems, expensesFollowItemsToBeUpdated);

            List<FollowItem> savingsFollowItemsToBeUpdated = FollowItemsUtils
                    .filterSavingsFollowItemsWithTarget(items);
            savingsFollowItems = FollowItemsUtils
                    .replace(savingsFollowItems, savingsFollowItemsToBeUpdated);
        }

        @Override
        public void onDelete(List<FollowItem> items) {
            List<FollowItem> expensesFollowItemsToDelete = FollowItemsUtils
                    .filterExpensesFollowItemsWithTarget(items);
            expensesFollowItems = FollowItemsUtils
                    .delete(expensesFollowItems, expensesFollowItemsToDelete);

            List<FollowItem> savingsFollowItemsToBeDelete = FollowItemsUtils
                    .filterSavingsFollowItemsWithTarget(items);
            savingsFollowItems = FollowItemsUtils
                    .delete(savingsFollowItems, savingsFollowItemsToBeDelete);
        }
    };

    private void updateLatestTransactions() {
        // TODO: PFMSDK: Do we need accounts for this?
//        if (categoryTree == null || latestTransactions == null || accounts == null) {
//            return;
//        }
        if (categoryTree == null || latestTransactions == null) {
            return;
        }

		transactionContainer.removeAllViews();

		CardGroupHeader header = new CardGroupHeader(getContext());
		header.setTheme(getTheme().getTransactionHeaderTheme());
		header.setHeaderText(getString(R.string.overview_latest_transactions_title));
		header.setButtonText(getString(R.string.overview_latest_transactions_view_all_link));
		header.setButtonOnClickListener(v -> showTransactions());
		header.setElevation(getResources().getDimension(R.dimen.card_elevation));
		transactionContainer.addView(header);

		// TODO: PFMSDK: Add new view for transaction list. Don't use TransactionRow.
		/*for (int i = 0; i < latestTransactions.size(); i++) {
			Transaction t = latestTransactions.get(i);

			TransactionRow row = new TransactionRow(getContext());
			row.setBackground(null);
			row.setCategoryTree(categoryTree);
			row.setAccount(accountsMap.get(t.getAccountId()));
			row.setDescriptionMode(DescriptionMode.DATE);
			row.setElevation(getResources().getDimension(R.dimen.card_elevation));
			row.setTransaction(t);
			row.setOnClickListener(transactionRowOnclickListeer);

			GroupPosition position =
					i == latestTransactions.size() - 1 ? GroupPosition.Bottom
							: GroupPosition.Middle;
			GroupPositionUtils.applyGroupPosition(position, row,
					getTheme().getTransactionRowGroupPositionTheme());
			transactionContainer.addView(row);
		}*/
    }
}
