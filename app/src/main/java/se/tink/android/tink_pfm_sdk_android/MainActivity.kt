package se.tink.android.tink_pfm_sdk_android

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.tink.pfmsdk.ClientConfiguration
import com.tink.pfmsdk.TinkFragment
import com.tink.pfmsdk.overview.OverviewChartFragment

class MainActivity : FragmentActivity() {

    private var currentTinkFragment: TinkFragment? = null

    private val accessToken = "eyJhbGciOiJFUzI1NiIsImtpZCI6IjhkMWQ0ZDg5LTcyYmQtNDE5Yi04NDZkLTVjMTFiY2M0NTkxMyIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzQ3NzQzNDksImlhdCI6MTU3NDc2NzE0OSwiaXNzIjoidGluazovL2F1dGgiLCJqdGkiOiI5OTNkMjEzNy1kOWFlLTRlMzMtYTM1Zi1mZWQwYTBmNzFlNTAiLCJvcmlnaW4iOiJtYWluIiwic2NvcGVzIjpbImludmVzdG1lbnRzOnJlYWQiLCJzdHJlYW1pbmc6YWNjZXNzIiwidXNlcjpyZWFkIiwic3RhdGlzdGljczpyZWFkIiwiYWNjb3VudHM6cmVhZCIsImNyZWRlbnRpYWxzOnJlYWQiLCJ0cmFuc2FjdGlvbnM6cmVhZCIsImlkZW50aXR5OnJlYWQiXSwic3ViIjoidGluazovL2F1dGgvdXNlci85Y2Y1Yjk1MTAwZjA0MWVkYWE4ZDJhMWU3YjM4ODAyOCIsInRpbms6Ly9hcHAvaWQiOiJhMzY2MTQ4YThmY2I0Yjg2YTA0NWFmOTAzOThlZTU3YiJ9.-rsdqb9xS6vruhz5D9ls3okiJo3Bqco0e5F84_KbtN4HHHdgal_MoFau26zWVek-PV0WMBSrls-RfKXAV6vIyQ";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(
            R.id.fragmentContainer,
            TinkFragment.newInstance(
                styleResId = R.style.TinkSamplePfmStyle,
                clientConfiguration = ClientConfiguration(accessToken)
            ).also {
                currentTinkFragment = it
            }
        ).commit()
    }

    override fun onBackPressed() {
        if (currentTinkFragment?.handleBackPress() == false)
            super.onBackPressed()
    }
}
