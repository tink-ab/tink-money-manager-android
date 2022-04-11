package com.tink.sample

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.tink.core.Tink
import com.tink.moneymanagerui.FinanceOverviewFragment
import com.tink.moneymanagerui.OverviewFeature
import com.tink.moneymanagerui.OverviewFeatures
import com.tink.moneymanagerui.accounts.AccountGroupByKind
import com.tink.sample.configuration.Configuration
import com.tink.service.network.TinkConfiguration
import timber.log.Timber

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

        try {
            // TODO: Move to App class
            Tink.init(config, applicationContext)
        } catch (e: IllegalStateException) {
            // Tink has already been initialized
        }

        val features = OverviewFeatures(listOf(OverviewFeature.Accounts(
            accountGroupType = AccountGroupByKind
        )))

        supportFragmentManager.beginTransaction().add(
            R.id.fragmentContainer,
            FinanceOverviewFragment.newInstance(
                overviewFeatures = features,
                accessToken = Configuration.sampleAccessToken,
                styleResId = R.style.TinkStyle_Default,
                tracker = LogTracker(),
                backPressedListener = { Timber.d("User navigated back") },
                isOverviewToolbarVisible = false
            ).also {
                currentFinanceOverviewFragment = it
            }
        ).commit()
    }

    override fun onBackPressed() {
        if (currentFinanceOverviewFragment?.handleBackPress() == false)
            super.onBackPressed()
    }
}
