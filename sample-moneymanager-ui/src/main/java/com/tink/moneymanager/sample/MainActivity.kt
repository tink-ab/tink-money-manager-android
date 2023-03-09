package com.tink.moneymanager.sample

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.tink.core.Tink
import com.tink.moneymanagerui.FinanceOverviewFragment
import com.tink.moneymanagerui.OnCustomContainerCreatedListener
import com.tink.moneymanagerui.OnFragmentViewCreatedListener
import com.tink.moneymanagerui.OverviewFeature
import com.tink.moneymanagerui.OverviewFeatures
import com.tink.moneymanagerui.StatisticType
import com.tink.service.network.Environment
import com.tink.service.network.TinkConfiguration
import se.tink.android.tink_pfm_sdk_android.R

class MainActivity : FragmentActivity(), OnFragmentViewCreatedListener {

    private lateinit var currentFinanceOverviewFragment: FinanceOverviewFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val environment: Environment = Environment.Production
        // TODO: Create a client in Tink Console https://console.tink.com/
        val clientId: String = ""
        // TODO: Insert access token https://docs.tink.com/resources/api-setup/get-access-token
        val accessToken: String = ""

        val config = TinkConfiguration(
            environment,
            clientId,
            Uri.parse("https://localhost:3000/callback")
        )

        Tink.init(config, applicationContext)

        supportFragmentManager.beginTransaction().add(
            R.id.fragmentContainer,
            FinanceOverviewFragment.newInstance(
                accessToken = accessToken,
                styleResId = R.style.TinkStyle_ChewingGum,
                tracker = LogTracker(),
                overviewFeatures = getOverviewFeatures(),
                isEditableOnPendingTransaction = true,
                isTransactionDetailsEnabled = true,
                fragmentViewCreatedListener = this@MainActivity
            ).also {
                currentFinanceOverviewFragment = it
            }
        ).commit()
    }

    /**
     * Choose which features to display as well as in what order
     *
     */
    private fun getOverviewFeatures() =
        OverviewFeatures(
            features =
            listOf(
                OverviewFeature.ActionableInsights,
                OverviewFeature.Statistics(
                    listOf(
                        StatisticType.EXPENSES,
                        StatisticType.INCOME
                    )
                ),
                OverviewFeature.CustomContainerView(
                    containerViewId = R.id.myapp_custom_view,
                    width = FrameLayout.LayoutParams.MATCH_PARENT,
                    height = FrameLayout.LayoutParams.WRAP_CONTENT
                ),
                OverviewFeature.Accounts(),
                OverviewFeature.LatestTransactions,
                OverviewFeature.Budgets
            )
        )

    /**
     * Add your own custom component to the finance overview
     *
     */
    private fun setupCustomView(viewId: Int) {
        currentFinanceOverviewFragment.getContainerById(
            viewId,
            object : OnCustomContainerCreatedListener {
                override fun onCustomContainerCreated(container: FrameLayout) {
                    val customComponent = View.inflate(
                        this@MainActivity,
                        R.layout.layout_custom_button,
                        container
                    )

                    customComponent.findViewById<Button>(R.id.myapp_custom_button)
                        .setOnClickListener {
                            Toast.makeText(it.context, "Custom button clicked", Toast.LENGTH_SHORT)
                                .show()
                        }
                }
            }
        )
    }

    /**
     * Setup custom views inside this method
     * */
    override fun onFragmentViewCreated() {
        setupCustomView(R.id.myapp_custom_view)
        // Add more custom layouts here
    }

    override fun onBackPressed() {
        if (!currentFinanceOverviewFragment.handleBackPress()) {
            super.onBackPressed()
        }
    }
}
