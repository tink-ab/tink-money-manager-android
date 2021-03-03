package com.tink.sample

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.tink.core.Tink
import com.tink.moneymanagerui.FinanceOverviewFragment
import com.tink.service.network.TinkConfiguration
import com.tink.sample.configuration.Configuration

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
    }

    override fun onBackPressed() {
        if (currentFinanceOverviewFragment?.handleBackPress() == false)
            super.onBackPressed()
    }
}
