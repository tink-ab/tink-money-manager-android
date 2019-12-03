package se.tink.android.tink_pfm_sdk_android

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.tink.pfmsdk.ClientConfiguration
import com.tink.pfmsdk.FinanceOverviewFragment

class MainActivity : FragmentActivity() {

    private var currentFinanceOverviewFragment: FinanceOverviewFragment? = null

    private val accessToken = "eyJhbGciOiJFUzI1NiIsImtpZCI6IjE0N2QwZTY5LTJiOTYtNDcxNi1hNWYzLTljYzkyYzBjMGE5ZCIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzUzODAxMDUsImlhdCI6MTU3NTM3MjkwNSwiaXNzIjoidGluazovL2F1dGgiLCJqdGkiOiI2OTdmZjEwYS02YjI5LTRkNmQtODc3ZS00ODVmYjMxOTdkZGQiLCJvcmlnaW4iOiJtYWluIiwic2NvcGVzIjpbImludmVzdG1lbnRzOnJlYWQiLCJzdHJlYW1pbmc6YWNjZXNzIiwiY2F0ZWdvcmllczpyZWFkIiwidXNlcjpyZWFkIiwic3RhdGlzdGljczpyZWFkIiwiYWNjb3VudHM6cmVhZCIsImNyZWRlbnRpYWxzOnJlYWQiLCJ0cmFuc2FjdGlvbnM6cmVhZCIsImlkZW50aXR5OnJlYWQiXSwic3ViIjoidGluazovL2F1dGgvdXNlci9iMjUwMTZhYzZlZjg0YmIyOTY0NGZhNmUxZTI3YjE1YyIsInRpbms6Ly9hcHAvaWQiOiJhMzY2MTQ4YThmY2I0Yjg2YTA0NWFmOTAzOThlZTU3YiJ9.ZbqmGEj01dSHv8VYW8rlRE4VFODiV6EjS-p3qaVcWs7R0lqf4AHMoXXAwS3zlZGRUziRM5lG_G6M-I81MZIbOw"

    private var sampleClientConfiguration =
        ClientConfiguration(
            endpoint = "main-grpc.production.oxford.tink.se",
            certificate = "-----BEGIN CERTIFICATE-----\n"
                    + "MIIEkjCCA3qgAwIBAgIQCgFBQgAAAVOFc2oLheynCDANBgkqhkiG9w0BAQsFADA/\n"
                    + "MSQwIgYDVQQKExtEaWdpdGFsIFNpZ25hdHVyZSBUcnVzdCBDby4xFzAVBgNVBAMT\n"
                    + "DkRTVCBSb290IENBIFgzMB4XDTE2MDMxNzE2NDA0NloXDTIxMDMxNzE2NDA0Nlow\n"
                    + "SjELMAkGA1UEBhMCVVMxFjAUBgNVBAoTDUxldCdzIEVuY3J5cHQxIzAhBgNVBAMT\n"
                    + "GkxldCdzIEVuY3J5cHQgQXV0aG9yaXR5IFgzMIIBIjANBgkqhkiG9w0BAQEFAAOC\n"
                    + "AQ8AMIIBCgKCAQEAnNMM8FrlLke3cl03g7NoYzDq1zUmGSXhvb418XCSL7e4S0EF\n"
                    + "q6meNQhY7LEqxGiHC6PjdeTm86dicbp5gWAf15Gan/PQeGdxyGkOlZHP/uaZ6WA8\n"
                    + "SMx+yk13EiSdRxta67nsHjcAHJyse6cF6s5K671B5TaYucv9bTyWaN8jKkKQDIZ0\n"
                    + "Z8h/pZq4UmEUEz9l6YKHy9v6Dlb2honzhT+Xhq+w3Brvaw2VFn3EK6BlspkENnWA\n"
                    + "a6xK8xuQSXgvopZPKiAlKQTGdMDQMc2PMTiVFrqoM7hD8bEfwzB/onkxEz0tNvjj\n"
                    + "/PIzark5McWvxI0NHWQWM6r6hCm21AvA2H3DkwIDAQABo4IBfTCCAXkwEgYDVR0T\n"
                    + "AQH/BAgwBgEB/wIBADAOBgNVHQ8BAf8EBAMCAYYwfwYIKwYBBQUHAQEEczBxMDIG\n"
                    + "CCsGAQUFBzABhiZodHRwOi8vaXNyZy50cnVzdGlkLm9jc3AuaWRlbnRydXN0LmNv\n"
                    + "bTA7BggrBgEFBQcwAoYvaHR0cDovL2FwcHMuaWRlbnRydXN0LmNvbS9yb290cy9k\n"
                    + "c3Ryb290Y2F4My5wN2MwHwYDVR0jBBgwFoAUxKexpHsscfrb4UuQdf/EFWCFiRAw\n"
                    + "VAYDVR0gBE0wSzAIBgZngQwBAgEwPwYLKwYBBAGC3xMBAQEwMDAuBggrBgEFBQcC\n"
                    + "ARYiaHR0cDovL2Nwcy5yb290LXgxLmxldHNlbmNyeXB0Lm9yZzA8BgNVHR8ENTAz\n"
                    + "MDGgL6AthitodHRwOi8vY3JsLmlkZW50cnVzdC5jb20vRFNUUk9PVENBWDNDUkwu\n"
                    + "Y3JsMB0GA1UdDgQWBBSoSmpjBH3duubRObemRWXv86jsoTANBgkqhkiG9w0BAQsF\n"
                    + "AAOCAQEA3TPXEfNjWDjdGBX7CVW+dla5cEilaUcne8IkCJLxWh9KEik3JHRRHGJo\n"
                    + "uM2VcGfl96S8TihRzZvoroed6ti6WqEBmtzw3Wodatg+VyOeph4EYpr/1wXKtx8/\n"
                    + "wApIvJSwtmVi4MFU5aMqrSDE6ea73Mj2tcMyo5jMd6jmeWUHK8so/joWUoHOUgwu\n"
                    + "X4Po1QYz+3dszkDqMp4fklxBwXRsW10KXzPMTZ+sOPAveyxindmjkW8lGy+QsRlG\n"
                    + "PfZ+G6Z6h7mjem0Y+iWlkYcV4PIWL1iwBi8saCbGS5jN2p8M+X+Q7UNKEkROb3N6\n"
                    + "KOqkqm57TH2H3eDJAkSnh6/DNFu0Qg==\n"
                    + "-----END CERTIFICATE-----",
            port = 443
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(
            R.id.fragmentContainer,
            FinanceOverviewFragment.newInstance(
                accessToken = accessToken,
                styleResId = R.style.TinkSamplePfmStyle,
                clientConfiguration = sampleClientConfiguration,
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
