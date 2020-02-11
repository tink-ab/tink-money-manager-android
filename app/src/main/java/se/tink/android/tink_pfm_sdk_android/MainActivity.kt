package se.tink.android.tink_pfm_sdk_android

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.tink.pfmsdk.FinanceOverviewFragment
import se.tink.android.tink_pfm_sdk_android.configuration.Configuration

class MainActivity : FragmentActivity() {

    private var currentFinanceOverviewFragment: FinanceOverviewFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(
            R.id.fragmentContainer,
            FinanceOverviewFragment.newInstance(
                accessToken = Configuration.sampleAccessToken,
                styleResId = R.style.TinkStyle_Default,
                clientConfiguration = Configuration.sampleClientConfiguration,
                tracker = LogTracker(),
                featureSet = Configuration.sampleFeatureSet
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
