package com.tink.pfmsdk;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.Nullable;
import com.google.gson.reflect.TypeToken;
import com.tink.pfmsdk.buildConfig.BuildConfigurations;
import com.tink.pfmsdk.buildConfig.Feature;
import com.tink.pfmsdk.buildConfig.NetworkConfiguration;
import com.tink.pfmsdk.security.SecuredClientDataStorage;
import com.tink.pfmsdk.util.JsonUtils;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import se.tink.privacy.Clearable;
import se.tink.privacy.DataWipeManager;

public class ClientDataStorage implements Clearable {

	private static ClientDataStorage instance;

	private static final String PREFS_NAME = "client-data-storage";
	private static final String LATEST_SEEN_FEED_EVENT_TIMESTAMP = "latestSeenFeedEventTimestamp";
	private static final String GCM_PUSH_TOKEN = "GcmPushToken";
	private static final String USE_TOUCH_ID_LOGIN = "UseTouchIdLogin";
	private static final String SESSION_ID = "sessionId";
	private static final String SELECTED_MARKET_CODE = "selectedMarketCode";
	private static final String PUSH_NOTIFICATIONS_RSA_KEYS = "PUSH_NOTIFICATIONS_RSA_KEYS";
	private static final String FIRST_LOGIN = "firstLogin";
	private static final String COUNTERPART_SUGGESTIONS_SKIPPED = "counterpart_suggestions_skipped";
	private static final String DISMISSED_BETA_BUBBLE = "dismissed_beta_bubble";
	private static final String DISMISSED_ERROR_CREDENTIALS = "dismissed_error_credentials";
	private static final String OPEN_BANKING_MIGRATED_PROVIDERS = "openBankingMigratedProviders";

	private static final String ICECREAM_HACK_SHOW_OPTIONS = "icecream_show_options";
	private static final String ICECREAM_HACK_ENABLED = "icecream_enabled";
	private static final String ICECREAM_HACK_NETWORK_SETTINGS = "icecream_hack_settings";
	private static final String ICECREAM_MARKET = "icecream_market";


	private final SharedPreferences settings;
	private SharedPreferences.Editor editor;
	private String uuid;
	private String sessionId;

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

	public ArrayList<String> getPreviousSearches() {
		String previousSearches = settings.getString("previousSearches", "");
		Type listType = new TypeToken<ArrayList<String>>() {}.getType();
		ArrayList<String> fromJson = JsonUtils.fromJson(previousSearches, listType);
		return  fromJson != null ? fromJson : new ArrayList<>();
	}

	public void setPreviousSearches(List<String> previousSearches) {
		editor.putString("previousSearches", JsonUtils.toJson(previousSearches));
		editor.remove("previousSerches"); //Remove old search result set.
		editor.apply();
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;

		SecuredClientDataStorage.getSharedInstance().edit().putString(SESSION_ID, sessionId)
			.commit();
	}

	public synchronized String getSessionId() {
		if (sessionId == null) {
			sessionId = SecuredClientDataStorage.getSharedInstance().getString(SESSION_ID, null);
		}
		return sessionId;
	}

	public void setLastVisitedPageInOverview(int page) {
		editor.putInt("lastViewPageInOverview", page);
		editor.apply();
	}

	public int getLastVisitedPageInOverview() {
		return settings.getInt("lastViewPageInOverview", 1); // left to spend is default
	}

	public String getLatestTransferFromAccount() {
		return settings.getString("latestTransferAccount", null);
	}

	public void setLatestTransferFromAccount(String latestTransferFromAccount) {
		editor.putString("latestTransferAccount", latestTransferFromAccount);
	}

	public void setPreselectedCredentialsIds(Set<String> preselectedCredentialsIds) {
		editor.putStringSet("preselectedCredentialsIds", preselectedCredentialsIds);
		editor.apply();
	}

	// TODO: PFMSDK: Do we need this? It has a facebook sdk dependency
	/*public Set<String> getPreselectedCredentialsIds() {
		return settings.getStringSet("preselectedCredentialsIds", Sets.<String>newHashSet());
	}*/

	public void setLatestSeenFeedEventTimestamp(long timestamp) {
		editor.putLong(LATEST_SEEN_FEED_EVENT_TIMESTAMP, timestamp);
		editor.apply();
	}

	public long getLatestSeenFeedEventTimestamp() {
		return settings.getLong(LATEST_SEEN_FEED_EVENT_TIMESTAMP, 0);
	}

	private static final String PASSWORD_PROTECTION = "settings.usePasswordProtection";

	public void setPasswordProtection(boolean usePasswordProtection) {
		editor.putBoolean(PASSWORD_PROTECTION, usePasswordProtection);
		editor.apply();
	}

	public boolean getPasswordProtection() {
		return settings.getBoolean(PASSWORD_PROTECTION, false);
	}

	public void removePushToken() {
		editor.putString(GCM_PUSH_TOKEN, null);
		editor.apply();
	}

	public String getPushToken() {
		return settings.getString(GCM_PUSH_TOKEN, null);
	}

	public void setPushToken(String token) {
		editor.putString(GCM_PUSH_TOKEN, token);
		editor.apply();
	}

	public void setUseTouchIdLogin(boolean useTouchIdLogin) {
		editor.putBoolean(USE_TOUCH_ID_LOGIN, useTouchIdLogin);
		editor.apply();
	}

	public boolean useTouchIdLogin() {
		if (BuildConfigurations.INSTANCE.getInstance().getFeatureConfigurations()
			.hasFeature(Feature.TOUCH_ID_LOGIN)) {
			return settings.getBoolean(USE_TOUCH_ID_LOGIN, false);
		}
		return false;
	}

	public String getSelectedMarketCode() {
		return settings.getString(SELECTED_MARKET_CODE, null);
	}

	public void setSelectedMarketCode(String selectedMarketCode) {
		editor.putString(SELECTED_MARKET_CODE, selectedMarketCode);
		editor.apply();
	}

	public boolean isFirstLogin() {
		return settings.getBoolean(FIRST_LOGIN, true);
	}

	public void setFirstLogin(boolean firstLogin) {
		editor.putBoolean(FIRST_LOGIN, firstLogin);
		editor.apply();
	}

	public boolean hasDismissedBetaBubble() {
		return settings.getBoolean(DISMISSED_BETA_BUBBLE, false);
	}

	public void setHasDismissedBetaBubble(boolean dismissedBetaBubble) {
		editor.putBoolean(DISMISSED_BETA_BUBBLE, dismissedBetaBubble);
		editor.apply();
	}

	public Set<String> getSkippedCounterpartSuggestionTransactions() {
		return settings.getStringSet(COUNTERPART_SUGGESTIONS_SKIPPED, new HashSet<String>());
	}

	public void addCounterpartSuggestionSkipped(String transactionId) {
		Set<String> transactionIds = getSkippedCounterpartSuggestionTransactions();
		transactionIds.add(transactionId);
		editor.putStringSet(COUNTERPART_SUGGESTIONS_SKIPPED, transactionIds).apply();
	}

	public boolean shouldShowIceCreamHackOptions() {
		return settings.getBoolean(ICECREAM_HACK_SHOW_OPTIONS, false);
	}

	public void showIceCreamHackOptions(boolean shouldShow) {
		editor.putBoolean(ICECREAM_HACK_SHOW_OPTIONS, shouldShow);
		editor.apply();
	}

	public boolean isIceCreamEnabled() {
		boolean isEnabled = settings.getBoolean(ICECREAM_HACK_ENABLED, false);
		return shouldShowIceCreamHackOptions() && isEnabled;
	}

	public void setIceCreamHackEnabled(boolean isEnabled) {
		editor.putBoolean(ICECREAM_HACK_ENABLED, isEnabled);
		editor.apply();
	}

	public NetworkConfiguration getIcecreamHackNetworkSettings() {
		if (isIceCreamEnabled()) {
			return getNetworkSettingsSetByUser();
		} else {
			return null;
		}
	}

	public NetworkConfiguration getNetworkSettingsSetByUser() {
        return JsonUtils.fromJson(
                settings.getString(ICECREAM_HACK_NETWORK_SETTINGS, null),
                NetworkConfiguration.class);
	}

	public void setIcecreamHackNetworkSettings(NetworkConfiguration config) {
		editor.putString(ICECREAM_HACK_NETWORK_SETTINGS, JsonUtils.toJson(config));
		editor.commit(); // commit immediately
	}

    @Nullable
	public String getIcecreamMarket() {
		String market = settings.getString(ICECREAM_MARKET, null);
		if (isIceCreamEnabled()) {
			return market;
		} else {
			return null;
		}
	}

	public void setIcecreamMarket(@Nullable String marketCode) {
		editor.putString(ICECREAM_MARKET, marketCode);
		editor.commit();
	}

	public void clear() {
		editor.clear().apply();
	}

	// TODO: PFMSDK: Do we need this?
	/*public PushNotificationsRsaKeysModel getPushNotificationsRsaKeys() {
		PushNotificationsRsaKeysModel model;
		String modelJson = SecuredClientDataStorage.getSharedInstance()
			.getString(PUSH_NOTIFICATIONS_RSA_KEYS, null);

		if (Strings.isNullOrEmpty(modelJson)) {
			model = PushNotificationsRsaKeysModel.newInstance();
			modelJson = JsonUtil.getGson().toJson(model, PushNotificationsRsaKeysModel.class);
			SecuredClientDataStorage.getSharedInstance().edit()
				.putString(PUSH_NOTIFICATIONS_RSA_KEYS, modelJson).apply();
		} else {
			model = JsonUtil.getGson().fromJson(modelJson, PushNotificationsRsaKeysModel.class);
		}

		return model;
	}*/

	@NotNull
	public Map<String, Long> dismissedErrorCredentialIds() {
		Type type = new TypeToken<Map<String, Long>>(){}.getType();
		return JsonUtils.fromJson(settings.getString(DISMISSED_ERROR_CREDENTIALS, "{}"), type);
	}

	public void setDismissedErrorCredentialIds(@NotNull Map<String, Long> dismissed) {
		editor.putString(DISMISSED_ERROR_CREDENTIALS, JsonUtils.toJson(dismissed));
		editor.apply();
	}

	public ArrayList<String> getMigratedProviderNames() {
		String migratedProviderNames = settings.getString(OPEN_BANKING_MIGRATED_PROVIDERS, "");
		Type listType = new TypeToken<ArrayList<String>>() {}.getType();
		ArrayList<String> fromJson = JsonUtils.fromJson(migratedProviderNames, listType);
		return  fromJson != null ? fromJson : new ArrayList<>();
	}

	public void setMigratedProviderNames(List<String> migratedProviderNames) {
		editor.putString(OPEN_BANKING_MIGRATED_PROVIDERS, JsonUtils.toJson(migratedProviderNames));
		editor.apply();
	}

}
