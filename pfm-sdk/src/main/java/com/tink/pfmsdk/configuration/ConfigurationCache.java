package com.tink.pfmsdk.configuration;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.inject.Inject;
import se.tink.android.di.application.ApplicationScoped;
import se.tink.core.models.device.DeviceConfigurationResponse;
import timber.log.Timber;

final class ConfigurationCache {

	private static final String CONFIGURATION_KEY = "configuration";
	private static final String PREF_NAME = "configurationCache";

	private final SharedPreferences sharedPreferences;

	@Inject
	ConfigurationCache(@ApplicationScoped Context context) {
		sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
	}

	void storeConfiguration(@NonNull DeviceConfigurationResponse configurationResponse) {
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

			objectOutputStream.writeObject(configurationResponse);

			String serialized = Base64
				.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
			sharedPreferences.edit().putString(CONFIGURATION_KEY, serialized).apply();

			byteArrayOutputStream.close();
			objectOutputStream.close();
		} catch (IOException e) {
			Timber.e(e, "Error serializing");
		}
	}

	@Nullable
	DeviceConfigurationResponse fetchConfiguration() {
		String stringRepresentation = sharedPreferences.getString(CONFIGURATION_KEY, null);
		DeviceConfigurationResponse deviceConfigurationResponse = null;
		if (stringRepresentation != null) {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				Base64.decode(stringRepresentation, Base64.DEFAULT));
			ObjectInputStream objectInputStream;
			try {
				objectInputStream = new ObjectInputStream(byteArrayInputStream);

				deviceConfigurationResponse = (DeviceConfigurationResponse) objectInputStream
					.readObject();

				byteArrayInputStream.close();
				objectInputStream.close();
			} catch (IOException | ClassNotFoundException e) {
				Timber.e(e, "Error deserializing");
			}
		}

		return deviceConfigurationResponse;
	}
}
