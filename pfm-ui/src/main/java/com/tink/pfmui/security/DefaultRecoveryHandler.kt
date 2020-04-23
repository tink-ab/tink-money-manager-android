package com.tink.pfmui.security

import android.content.SharedPreferences
import java.security.KeyStore
import java.security.KeyStoreException

internal class DefaultRecoveryHandler : RecoveryHandler() {
    override fun recover(
        e: Exception,
        keyStore: KeyStore,
        keyAliases: List<String>,
        preferences: SharedPreferences
    ): Boolean {
        e.printStackTrace()
        try {
            clearKeyStore(keyStore, keyAliases)
            clearPreferences(preferences)
            return true
        } catch (e1: KeyStoreException) {
            e1.printStackTrace()
        }
        return false
    }
}