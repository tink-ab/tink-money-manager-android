package com.tink.moneymanagerui.security

import android.content.Context
import android.content.SharedPreferences
import se.tink.android.privacy.Clearable
import timber.log.Timber
import java.io.IOException
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException
import java.security.UnrecoverableEntryException
import java.security.cert.CertificateException
import javax.crypto.NoSuchPaddingException

internal class SecuredClientDataStorage private constructor(appContext: Context) :
    SharedPreferences, Clearable {
    private val mPrefs: SharedPreferences?
    val encryptionManager: EncryptionManager

    override fun getAll(): Map<String, String?> {
        val all = mPrefs!!.all
        val dAll: MutableMap<String, String?> =
            HashMap(all.size)
        if (all.isNotEmpty()) {
            for (key in all.keys) {
                try {
                    dAll[key] = encryptionManager.decrypt(all[key] as String?)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return dAll
    }

    override fun getString(key: String, defValue: String?): String? {
        try {
            val hashedKey = EncryptionManager.getHashed(key)
            val value = mPrefs!!.getString(hashedKey, null)
            if (value != null) {
                return encryptionManager.decrypt(value)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return defValue
    }

    override fun getStringSet(
        key: String,
        defValues: Set<String>?
    ): Set<String>? {
        try {
            val hashedKey = EncryptionManager.getHashed(key)
            val eSet = mPrefs!!.getStringSet(hashedKey, null)
            if (eSet != null) {
                val dSet: MutableSet<String> =
                    HashSet(eSet.size)
                for (`val` in eSet) {
                    dSet.add(encryptionManager.decrypt(`val`))
                }
                return dSet
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return defValues
    }

    override fun getInt(key: String, defValue: Int): Int {
        val value = getString(key, null)
        return value?.toInt() ?: defValue
    }

    override fun getLong(key: String, defValue: Long): Long {
        val value = getString(key, null)
        return value?.toLong() ?: defValue
    }

    override fun getFloat(key: String, defValue: Float): Float {
        val value = getString(key, null)
        return value?.toFloat() ?: defValue
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        val value = getString(key, null)
        return if (value != null) {
            java.lang.Boolean.parseBoolean(value)
        } else defValue
    }

    override fun contains(key: String): Boolean {
        try {
            val hashedKey = EncryptionManager.getHashed(key)
            return mPrefs!!.contains(hashedKey)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    override fun edit(): Editor {
        return Editor()
    }

    override fun registerOnSharedPreferenceChangeListener(
        onSharedPreferenceChangeListener: SharedPreferences.OnSharedPreferenceChangeListener
    ) {
        mPrefs?.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener)
    }

    override fun unregisterOnSharedPreferenceChangeListener(
        onSharedPreferenceChangeListener: SharedPreferences.OnSharedPreferenceChangeListener
    ) {
        mPrefs?.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener)
    }

    override fun clear() {
        edit().clear().commit()
    }

    inner class Editor : SharedPreferences.Editor {
        private var mEditor: SharedPreferences.Editor = mPrefs!!.edit()
        override fun putString(
            key: String,
            value: String?
        ): SharedPreferences.Editor {
            try {
                val hashedKey = EncryptionManager.getHashed(key)
                val evalue = encryptionManager.encrypt(value)
                mEditor.putString(hashedKey, evalue)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return this
        }

        override fun putStringSet(
            key: String,
            values: Set<String>?
        ): SharedPreferences.Editor {
            try {
                val hashedKey = EncryptionManager.getHashed(key)
                val eSet: MutableSet<String> =
                    HashSet(values!!.size)
                for (`val` in values) {
                    eSet.add(encryptionManager.encrypt(`val`))
                }
                mEditor.putStringSet(hashedKey, eSet)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return this
        }

        override fun putInt(
            key: String,
            value: Int
        ): SharedPreferences.Editor {
            val `val` = value.toString()
            return putString(key, `val`)
        }

        override fun putLong(
            key: String,
            value: Long
        ): SharedPreferences.Editor {
            val `val` = value.toString()
            return putString(key, `val`)
        }

        override fun putFloat(
            key: String,
            value: Float
        ): SharedPreferences.Editor {
            val `val` = value.toString()
            return putString(key, `val`)
        }

        override fun putBoolean(
            key: String,
            value: Boolean
        ): SharedPreferences.Editor {
            val `val` = java.lang.Boolean.toString(value)
            return putString(key, `val`)
        }

        override fun remove(key: String): SharedPreferences.Editor {
            try {
                val hashedKey = EncryptionManager.getHashed(key)
                mEditor.remove(hashedKey)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return this
        }

        override fun clear(): SharedPreferences.Editor {
            mEditor.clear()
            return this
        }

        override fun commit(): Boolean {
            return mEditor.commit()
        }

        override fun apply() {
            mEditor.apply()
        }
    }

    interface KeyStoreRecoveryNotifier {
        /**
         * @return true if the error could be resolved
         */
        fun onRecoveryRequired(
            e: Exception,
            keyStore: KeyStore,
            keyAliases: List<String>
        ): Boolean
    }

    companion object {
        private const val TAG = "SecuredCDStorage"
        private const val PREF_FILE_NAME = "SCDS_file"
        private var mRecoveryHandler: RecoveryHandler? = null
        private var mInstance: SecuredClientDataStorage? = null
        private fun setRecoveryHandler(recoveryHandler: RecoveryHandler?) {
            mRecoveryHandler = recoveryHandler
        }

        @get:Synchronized
        val sharedInstance: SecuredClientDataStorage?
            get() {
                checkNotNull(mInstance) { "Must call init() before using SecuredClientDataStorage" }
                return mInstance
            }

        /**
         * Must be called once before using the SecuredClientDataStorage to initialize the shared
         * instance. You may call it in @code{onCreate} method of your application class or launcher
         * activity
         */
        @Throws(
            IOException::class,
            CertificateException::class,
            NoSuchAlgorithmException::class,
            KeyStoreException::class,
            UnrecoverableEntryException::class,
            InvalidAlgorithmParameterException::class,
            NoSuchPaddingException::class,
            InvalidKeyException::class,
            NoSuchProviderException::class
        )

        @JvmStatic
        fun init(
            appContext: Context,
            recoveryHandler: RecoveryHandler?
        ) {
            if (mInstance != null) {
                Timber.tag(TAG)
                    .w("init called when there already is a non-null instance of the class")
                return
            }
            setRecoveryHandler(recoveryHandler)
            mInstance = SecuredClientDataStorage(appContext)
        }
    }

    init {
        Timber.d("Creating SecuredClientDataStorage instance")
        mPrefs = appContext.getSharedPreferences(
            PREF_FILE_NAME,
            Context.MODE_PRIVATE
        )
        encryptionManager =
            EncryptionManager(
                appContext, mPrefs,
                object : KeyStoreRecoveryNotifier {
                    override fun onRecoveryRequired(
                        e: Exception,
                        keyStore: KeyStore,
                        keyAliases: List<String>
                    ): Boolean {
                        if (mRecoveryHandler != null) {
                            return mRecoveryHandler!!.recover(
                                e,
                                keyStore,
                                keyAliases,
                                mPrefs
                            )
                        } else {
                            throw RuntimeException(e)
                        }
                    }
                }
            )
    }
}
