package com.tink.sample

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.tink.core.Tink
import com.tink.moneymanagerui.FinanceOverviewFragment
import com.tink.moneymanagerui.OverviewFeature
import com.tink.moneymanagerui.OverviewFeatures
import com.tink.moneymanagerui.accounts.AccountEditConfiguration
import com.tink.moneymanagerui.accounts.AccountGroupByKind
import com.tink.moneymanagerui.accounts.EditAccountField
import com.tink.moneymanagerui.accounts.OverviewAccountsTypeAccounts
import com.tink.sample.configuration.Configuration
import com.tink.service.network.TinkConfiguration
import timber.log.Timber

class MainActivity : FragmentActivity() {

    private var currentFinanceOverviewFragment: FinanceOverviewFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
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

            val f = OverviewFeatures(
                listOf(
                    OverviewFeature.Accounts(
                        OverviewAccountsTypeAccounts(com.tink.model.account.Account.Type.SAVINGS),
                        AccountGroupByKind,
                        AccountEditConfiguration(listOf(EditAccountField.NAME, EditAccountField.IS_FAVORITE))
                    )
                )
            )
            supportFragmentManager.beginTransaction().add(
                R.id.fragmentContainer,
                FinanceOverviewFragment.newInstance(
                    // overviewFeatures = f,
                    accessToken = Configuration.sampleAccessToken,
                    styleResId = R.style.TinkStyle_Default,
                    tracker = LogTracker(),
                    backPressedListener = { Timber.d("User navigated back") },
                    isOverviewToolbarVisible = false
                ).also {
                    currentFinanceOverviewFragment = it
                }
            ).commit()
        } else {
            currentFinanceOverviewFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as FinanceOverviewFragment
        }
    }

    override fun onBackPressed() {
        if (currentFinanceOverviewFragment?.handleBackPress() == false)
            super.onBackPressed()
    }
}
