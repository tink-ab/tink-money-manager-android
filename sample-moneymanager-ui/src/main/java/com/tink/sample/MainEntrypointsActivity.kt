package com.tink.sample

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.tink.core.Tink
import com.tink.moneymanagerui.OnCustomContainerCreatedListener
import com.tink.moneymanagerui.OnFragmentViewCreatedListener
import com.tink.moneymanagerui.OverviewFeature
import com.tink.moneymanagerui.OverviewFeatures
import com.tink.moneymanagerui.StatisticType
import com.tink.moneymanagerui.TinkMoneyManager
import com.tink.moneymanagerui.accounts.AccountEditConfiguration
import com.tink.moneymanagerui.accounts.EditAccountField
import com.tink.moneymanagerui.accounts.FilterAll
import com.tink.moneymanagerui.accounts.FilterByFavorites
import com.tink.moneymanagerui.accounts.GroupingByKind
import com.tink.moneymanagerui.entrypoints.EntryPoint
import com.tink.moneymanagerui.entrypoints.transactions.DateInterval
import com.tink.moneymanagerui.statistics.ChartType
import com.tink.rest.models.SearchQuery
import com.tink.sample.databinding.ActivityEntrypointMainBinding
import com.tink.service.network.Environment
import com.tink.service.network.TinkConfiguration
import timber.log.Timber
import java.time.LocalDate
import java.time.ZoneId

class MainEntrypointsActivity : FragmentActivity(), OnFragmentViewCreatedListener {

    // Configuration class for the Overview list entrypoint.
    private val overviewEntrypoint = EntryPoint.Overview(
        overviewFeatures = OverviewFeatures(
            listOf(
                OverviewFeature.ActionableInsights,
                OverviewFeature.Statistics(
                    listOf(
                        StatisticType.EXPENSES,
                        StatisticType.LEFT_TO_SPEND,
                        StatisticType.INCOME
                    )
                ),
                // Define the id and the desired width and height for the custom view.
                // The inflation of the actual layout is done in the onFragmentViewCreated() method.
                OverviewFeature.CustomContainerView(
                    containerViewId = R.id.custom_view,
                    width = FrameLayout.LayoutParams.MATCH_PARENT,
                    height = FrameLayout.LayoutParams.WRAP_CONTENT
                ),
                OverviewFeature.Accounts(
                    FilterByFavorites,
                    GroupingByKind,
                    AccountEditConfiguration(
                        listOf(
                            EditAccountField.NAME,
                            EditAccountField.KIND,
                            EditAccountField.IS_FAVORITE,
                            EditAccountField.IS_INCLUDED,
                            EditAccountField.IS_SHARED
                        )
                    )
                ),
                OverviewFeature.LatestTransactions,
                OverviewFeature.Budgets
            )
        ),
        toolbarVisible = false,
        featureSpecificThemes = emptyMap(),
        insightActionHandler = null,
        fragmentViewCreatedListener = this
    )

    // Configuration class for the Insights entrypoint.
    private val insightsEntrypoint = EntryPoint.ActionableInsights(
        insightActionHandler = null,
        showArchiveButton = true
    )

    // Configuration class for the Income statistics entrypoint.
    private val statisticsIncomeEntrypoint = EntryPoint.Statistics(
        ChartType.INCOME
    )

    // Configuration class for the Expense statistics entrypoint.
    private val statisticsExpenseEntrypoint = EntryPoint.Statistics(
        ChartType.EXPENSES
    )

    // Configuration class for the 'Left to Spend' statistics entrypoint.
    private val statisticsLeftToSpendEntrypoint = EntryPoint.Statistics(
        ChartType.LEFT_TO_SPEND
    )

    // Configuration class for the Accounts list entrypoint.
    private val accountsEntrypoint = EntryPoint.Accounts(
        groupingMode = GroupingByKind,
        filteringMode = FilterAll,
        accountEditConfiguration = AccountEditConfiguration(
            listOf(
                EditAccountField.NAME,
                EditAccountField.KIND
            )
        )
    )

    // Configuration class for the Transactions list entrypoint.
    private val transactionsEntryPoint = EntryPoint.Transactions(
        title = "All transactions",
        accounts = emptyList(),
        categories = emptyList(),
        query = "",
        includeUpcoming = true,
        dateInterval = DateInterval(
            LocalDate.of(2022, 1, 1).atStartOfDay(ZoneId.of("UTC")).toInstant(),
            LocalDate.now().atStartOfDay(ZoneId.of("UTC")).toInstant()
        ),
        sort = SearchQuery.SortEnum.DATE,
        order = SearchQuery.OrderEnum.DESC,
        limit = 100
    )

    // Configuration class for the Budgets list entrypoint.
    private val budgetsEntryPoint = EntryPoint.Budgets

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUI()

        if (savedInstanceState == null) {
            // Create a TinkConfiguration, specifying an Environment and a clientID.
            // Read the TinkConfiguration code documentation for more information on each parameter.
            val config = TinkConfiguration(
                environment = Environment.Production,
                oAuthClientId = ""
            )
            try {
                // Initialize the Tink SDK. To keep the sample app as clear as possible here the initialization is done in the Activity.
                // Anyway, this should be done in the Application class, to avoid the risk of calling this method multiple times.
                Tink.init(config, applicationContext)
            } catch (e: IllegalStateException) {
                // If Tink has already been initialized, it throws an IllegalStateException.
            }
        } else {
            TinkMoneyManager.onRestore(supportFragmentManager) // Call this method after restore changes
            showHideViews(false) // Note: This is specific to this sample app
        }
    }

    private fun startTinkEntrypoint(entryPoint: EntryPoint) {
        // For more detailed information on each parameter, please read the TinkMoneyManager code documentation.
        TinkMoneyManager.init(
            accessToken = "",
            styleResId = R.style.TinkStyle_DayNight,
            tracker = LogTracker(),
            backPressedListener = { Timber.d("User navigated back") },
            editPendingTransaction = false,
            enableTransactionDetail = false,
            entryPoint = entryPoint,
            containerId = R.id.fragmentContainer,
            fragmentManager = supportFragmentManager
        )
        showHideViews(false) // Note: This is specific to this sample app
    }

    /**
     * When this method is called, the Overview page is created.
     * Initialize custom views here, inflating the layout and defining behaviours.
     * */
    override fun onFragmentViewCreated() {
        TinkMoneyManager.getContainerById(
            R.id.custom_view,
            // Define a OnCustomContainerCreatedListener object to receive a callback when the CustomView container is created.
            object : OnCustomContainerCreatedListener {
                override fun onCustomContainerCreated(container: FrameLayout) {
                    // Inflate the layout
                    val customComponent = View.inflate(
                        this@MainEntrypointsActivity,
                        R.layout.layout_custom_button,
                        container
                    )
                    // Define the behaviour of your custom view.
                    customComponent.findViewById<Button>(R.id.myapp_custom_button)
                        .setOnClickListener {
                            Toast.makeText(
                                it.context,
                                "My custom button clicked",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                }
            }
        )
    }

    /**
     * Callback method for handling the back press event
     */
    override fun onBackPressed() {
        Timber.tag("Navigation").d("onBackPressed")
        if (!TinkMoneyManager.isSdkActive()) {
            super.onBackPressed()
        } else {
            TinkMoneyManager.onBackPressed()

            if (!TinkMoneyManager.isSdkActive()) { // Note: Simple UI logic and check to make this sample app work.
                showHideViews(true)
            }
        }
    }

    private fun showHideViews(isSdkHidden: Boolean) {
        binding.mainGroup.isVisible = isSdkHidden
        binding.fragmentContainer.isVisible = !isSdkHidden
    }

    private lateinit var binding: ActivityEntrypointMainBinding

    private fun initUI() {
        binding = ActivityEntrypointMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.accountsButton.setOnClickListener {
            startTinkEntrypoint(accountsEntrypoint)
        }
        binding.insightsButton.setOnClickListener {
            startTinkEntrypoint(insightsEntrypoint)
        }
        binding.statisticsIncomeButton.setOnClickListener {
            startTinkEntrypoint(statisticsIncomeEntrypoint)
        }
        binding.statisticsExpenseButton.setOnClickListener {
            startTinkEntrypoint(statisticsExpenseEntrypoint)
        }
        binding.statisticsL2sButton.setOnClickListener {
            startTinkEntrypoint(statisticsLeftToSpendEntrypoint)
        }
        binding.budgetsButton.setOnClickListener {
            startTinkEntrypoint(budgetsEntryPoint)
        }
        binding.overviewButton.setOnClickListener {
            startTinkEntrypoint(overviewEntrypoint)
        }
        binding.transactionsButton.setOnClickListener {
            startTinkEntrypoint(transactionsEntryPoint)
        }
    }
}
