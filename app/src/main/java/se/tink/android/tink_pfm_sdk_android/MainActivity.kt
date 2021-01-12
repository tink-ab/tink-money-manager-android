package se.tink.android.tink_pfm_sdk_android

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
import com.tink.pfmui.FinanceOverviewFragment
import com.tink.pfmui.OverviewFeature
import com.tink.pfmui.OverviewFeatures
import com.tink.pfmui.StatisticType
import com.tink.service.network.TinkConfiguration
import se.tink.android.tink_pfm_sdk_android.configuration.Configuration

class MainActivity : FragmentActivity() {

    private var currentFinanceOverviewFragment: FinanceOverviewFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val config = TinkConfiguration(
            Configuration.sampleEnvironment,
            Configuration.sampleOAuthClientId,
            Uri.parse("https://localhost:3000/callback")
        )

        Tink.init(config, applicationContext)

        supportFragmentManager.beginTransaction().add(
            R.id.fragmentContainer,
            FinanceOverviewFragment.newInstance(
                accessToken = Configuration.sampleAccessToken,
                styleResId = R.style.TinkStyle_Default,
                tracker = LogTracker()
            ).also {
                currentFinanceOverviewFragment = it
            }
        ).commit()

        // Do this to handle creation of your custom views
        addFragmentLifecycleCallbacks()
    }

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
                OverviewFeature.Accounts,
                OverviewFeature.LatestTransactions
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
                    fm: FragmentManager,
                    f: Fragment,
                    context: Context
                ) {
                    super.onFragmentAttached(fm, f, context)
                    currentFinanceOverviewFragment
                        ?.childFragmentManager
                        ?.registerFragmentLifecycleCallbacks(
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

    private fun setupCustomViews(parent: View) {
        val customView = parent.findViewById<FrameLayout>(R.id.myapp_custom_view)
        if (customView != null) {
            if (customView.findViewById<RelativeLayout>(R.id.myapp_custom_button_container) == null) {
                val viewTwo = View.inflate(
                    this,
                    R.layout.layout_custom_button,
                    customView
                )
                val customButton = viewTwo.findViewById<Button>(R.id.myapp_custom_button)
                customButton.setOnClickListener {
                    Toast.makeText(it.context, "My custom button clicked", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onBackPressed() {
        if (currentFinanceOverviewFragment?.handleBackPress() == false)
            super.onBackPressed()
    }
}
