package com.tink.pfmsdk.analytics

import io.intercom.android.sdk.Intercom

class IntercomApplicationTracker {
    companion object {
        fun trackProductCreateApplication(applicationType: String) {
            logEventWithName(makeName("application-created", applicationType))
        }

        fun trackProductResumeApplication(applicationType: String) {
            logEventWithName(makeName("application-resumed", applicationType))
        }

        fun trackProductPauseApplication(applicationType: String) {
            logEventWithName(makeName("application-paused", applicationType))
        }

        fun trackProductList(applicationType: String) {
            logEventWithName(makeName("product-list-shown", applicationType))
        }

        fun trackProductDetails(applicationType: String) {
            logEventWithName(makeName("product-details-shown", applicationType))
        }

        fun trackProductDetailsSubmitted(applicationType: String) {
            logEventWithName(makeName("product-details-submitted", applicationType))
        }

        fun trackProductSign(applicationType: String) {
            logEventWithName(makeName("product-sign-shown", applicationType))
        }

        private fun logEventWithName(name: String) {
            Intercom.client().logEvent(name)
        }

        private fun makeName(action: String, applicationType: String): String {
            return "$action-$applicationType"
        }
    }
}
