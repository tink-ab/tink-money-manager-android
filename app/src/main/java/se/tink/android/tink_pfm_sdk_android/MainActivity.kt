package se.tink.android.tink_pfm_sdk_android

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.tink.pfmsdk.TinkFragment
import com.tink.pfmsdk.overview.OverviewChartFragment

class MainActivity : FragmentActivity() {

    private var currentTinkFragment: TinkFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(
            R.id.fragmentContainer,
            TinkFragment.newInstance(R.style.TinkSamplePfmStyle).also {
                currentTinkFragment = it
            }
        ).commit()
    }

    override fun onBackPressed() {
        if (currentTinkFragment?.handleBackPress() == false)
            super.onBackPressed()
    }
}
