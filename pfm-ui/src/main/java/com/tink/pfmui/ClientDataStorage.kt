package com.tink.pfmui

import android.content.Context
import android.content.SharedPreferences
import se.tink.privacy.Clearable
import se.tink.privacy.DataWipeManager
import java.util.UUID

internal class ClientDataStorage private constructor(context: Context) : Clearable {
    private val settings: SharedPreferences
    private val editor: SharedPreferences.Editor
    var uuid: String? = null
    var uUID: String?
        get() {
            var uuid = settings.getString(DEVICE_ID, null)
            if (uuid == null) {
                uuid = this.uuid
                uUID = uuid
            }
            if (uuid == null) {
                uuid = UUID.randomUUID().toString()
                uUID = uuid
            }
            return uuid
        }
        set(uuid) {
            this.uuid = uuid
            editor.putString(DEVICE_ID, uuid)
            editor.apply()
        }

    // left to spend is default
    var lastVisitedPageInOverview: Int
        get() = settings.getInt(LAST_VIEW_PAGE_IN_OVERVIEW, 1) // left to spend is default
        set(page) {
            editor.putInt(LAST_VIEW_PAGE_IN_OVERVIEW, page)
            editor.apply()
        }

    override fun clear() {
        editor.clear().apply()
    }

    companion object {
        private const val PREFS_NAME = "client-data-storage"
        private const val DEVICE_ID = "tinkDeviceId"
        private const val LAST_VIEW_PAGE_IN_OVERVIEW = "lastViewPageInOverview"

        private var instance: ClientDataStorage? = null
        @JvmStatic
        @Synchronized
        fun sharedInstance(context: Context): ClientDataStorage =
            instance
                ?.let { it }
                ?: ClientDataStorage(context)
                    .let {
                        DataWipeManager.sharedInstance().register(it)
                        instance = it
                        it
                    }

    }

    init {
        settings = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        editor = settings.edit()
    }
}