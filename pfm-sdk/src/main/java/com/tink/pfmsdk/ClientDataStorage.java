package com.tink.pfmsdk;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.UUID;
import se.tink.privacy.Clearable;
import se.tink.privacy.DataWipeManager;

public class ClientDataStorage implements Clearable {

	private static ClientDataStorage instance;

	private static final String PREFS_NAME = "client-data-storage";

	private final SharedPreferences settings;
	private SharedPreferences.Editor editor;
	private String uuid;

	public static synchronized ClientDataStorage sharedInstance(Context context) {
		if (instance == null) {
			instance = new ClientDataStorage(context);
			DataWipeManager.sharedInstance().register(instance);
		}
		return instance;
	}

	private ClientDataStorage(Context context) {
		settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		editor = settings.edit();
	}

	public String getUUID() {
		String uuid = settings.getString("tinkDeviceId", null);
		if (uuid == null) {
			uuid = this.uuid;
			setUUID(uuid);
		}
		if (uuid == null) {
			uuid = UUID.randomUUID().toString();
			setUUID(uuid);
		}
		return uuid;
	}

	public void setUUID(String uuid) {
		this.uuid = uuid;
		editor.putString("tinkDeviceId", uuid);
		editor.apply();
	}

	public void setLastVisitedPageInOverview(int page) {
		editor.putInt("lastViewPageInOverview", page);
		editor.apply();
	}

	public int getLastVisitedPageInOverview() {
		return settings.getInt("lastViewPageInOverview", 1); // left to spend is default
	}

	public void clear() {
		editor.clear().apply();
	}

}
