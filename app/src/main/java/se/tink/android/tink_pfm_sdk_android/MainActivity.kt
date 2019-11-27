package se.tink.android.tink_pfm_sdk_android

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.tink.pfmsdk.ClientConfiguration
import com.tink.pfmsdk.TinkFragment

class MainActivity : FragmentActivity() {

    private var currentTinkFragment: TinkFragment? = null

    // TODO: PFMSDK: Cleanup and add documentation
    private val accessToken = "eyJhbGciOiJFUzI1NiIsImtpZCI6IjgxZjRjNDI5LTkxNTQtNGQ0Ny05NDlmLWViZWIxNmU1MjUzZSIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzQ4NTkzMTAsImlhdCI6MTU3NDg1MjExMCwiaXNzIjoidGluazovL2F1dGgiLCJqdGkiOiIwYmY2ODMwMy05MmI1LTRjMzAtODVlNC05ZWE2ZGJlN2YyMTMiLCJvcmlnaW4iOiJtYWluIiwic2NvcGVzIjpbImludmVzdG1lbnRzOnJlYWQiLCJzdHJlYW1pbmc6YWNjZXNzIiwidXNlcjpyZWFkIiwic3RhdGlzdGljczpyZWFkIiwiYWNjb3VudHM6cmVhZCIsImNyZWRlbnRpYWxzOnJlYWQiLCJ0cmFuc2FjdGlvbnM6cmVhZCIsImlkZW50aXR5OnJlYWQiXSwic3ViIjoidGluazovL2F1dGgvdXNlci80M2FkZmY1NDA0OGY0ZTBmOWVlYThkNTQyM2E2MTMxYSIsInRpbms6Ly9hcHAvaWQiOiJhMzY2MTQ4YThmY2I0Yjg2YTA0NWFmOTAzOThlZTU3YiJ9.WjBUSBvE_n7eHPZtMWxEUaM2hZ_chrokq91ansDu0tEZwP4jzW0lyQD9Qa0iFqKM45qNYGAtGdDTFean0OLPKw";

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
