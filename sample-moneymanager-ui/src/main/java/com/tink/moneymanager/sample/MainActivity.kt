package com.tink.moneymanager.sample

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.tink.core.Tink
import com.tink.moneymanagerui.FinanceOverviewFragment
import com.tink.moneymanagerui.OverviewFeature
import com.tink.moneymanagerui.OverviewFeatures
import com.tink.moneymanagerui.StatisticType
import com.tink.service.network.Environment
import com.tink.service.network.TinkConfiguration
import se.tink.android.tink_pfm_sdk_android.R

class MainActivity : FragmentActivity() {

    private lateinit var currentFinanceOverviewFragment: FinanceOverviewFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val environment: Environment = Environment.Production
        // TODO: Create a client in Tink Console https://console.tink.com/
        val clientId: String =
        // TODO: Insert access token https://docs.tink.com/resources/api-setup/get-access-token
        val accessToken: String =

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
                isTransactionDetailsEnabled = true
            ).also {
                currentFinanceOverviewFragment = it
            }
        ).commit()

        // Do this to handle creation of your custom views
        addFragmentLifecycleCallbacks()
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
     * Add fragment lifecycle callbacks to update the custom views after they are added to
     * the finance overview screen.
     */
    private fun addFragmentLifecycleCallbacks() {
        supportFragmentManager.registerFragmentLifecycleCallbacks(
            object: FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentAttached(
                    fragmentManager: FragmentManager,
                    fragment: Fragment,
                    context: Context
                ) {
                    super.onFragmentAttached(fragmentManager, fragment, context)
                    currentFinanceOverviewFragment
                        .childFragmentManager
                        .registerFragmentLifecycleCallbacks(
                            object : FragmentManager.FragmentLifecycleCallbacks() {
                                override fun onFragmentViewCreated(
                                    fm: FragmentManager,
                                    f: Fragment,
                                    v: View,
                                    savedInstanceState: Bundle?
                                ) {
                                    super.onFragmentViewCreated(fm, f, v, savedInstanceState)
                                    setupCustomViews(v)
                                }
                            },
                            true
                        )
                }
            },
            false
        )
    }

    /**
     * Add your own custom component to the finance overview
     *
     */
    private fun setupCustomViews(parent: View) {
        val customView = parent.findViewById<FrameLayout>(R.id.myapp_custom_view)
        if (customView != null) {
            if (customView.findViewById<RelativeLayout>(R.id.myapp_custom_button_container) == null) {
                val customComponent = View.inflate(
                    this,
                    R.layout.layout_custom_button,
                    customView
                )
                val customButton = customComponent.findViewById<Button>(R.id.myapp_custom_button)
                customButton.setOnClickListener {
                    Toast.makeText(it.context, "My custom button clicked", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onBackPressed() {
        if (!currentFinanceOverviewFragment.handleBackPress())
            super.onBackPressed()
    }
}
