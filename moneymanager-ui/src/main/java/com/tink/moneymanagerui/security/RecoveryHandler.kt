package com.tink.moneymanagerui.security

import android.content.SharedPreferences
import java.security.KeyStore
import java.security.KeyStoreException
import java.util.Collections

internal abstract class RecoveryHandler {
    abstract fun recover(
        e: Exception,
        keyStore: KeyStore,
        keyAliases: List<String>,
        preferences: SharedPreferences
    ): Boolean

    @Throws(KeyStoreException::class)
    fun clearKeyStore(
        keyStore: KeyStore?,
        aliases: List<String?>?
    ) {
        if (keyStore != null && aliases != null) {
            for (alias in aliases) {
                if (keyStore.containsAlias(alias)) {
                    keyStore.deleteEntry(alias)
                }
            }
        }
    }

    @Throws(KeyStoreException::class)
    fun clearKeystore(keyStore: KeyStore?) {
        if (keyStore != null) {
            val aliases: List<String> =
                Collections.list(keyStore.aliases())
            for (alias in aliases) {
                if (keyStore.containsAlias(alias)) {
                    keyStore.deleteEntry(alias)
                }
            }
        }
    }

    fun clearPreferences(preferences: SharedPreferences?) {
        preferences?.edit()?.clear()?.apply()
    }
}