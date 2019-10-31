package com.tink.pfmsdk.configuration;

import android.content.res.Resources;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.os.ConfigurationCompat;
import com.tink.pfmsdk.buildConfig.BuildConfigurations;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;

public final class SuitableLocaleFinder {

	private final Resources resources;

	@Inject
	public SuitableLocaleFinder() {
		this.resources = Resources.getSystem();
	}

	public String findLocaleString() {
		return getLocaleAsUnderscoredLanguageTag(findLocale());
	}

	public Locale findLocale() {
		List<Locale> localesSupportedByApp = getLocalesSupportedByApp();
		Locale localesOnDevice = getLocaleOnDevice();

		Locale suitableLocale = findMostSuitableLocale(localesSupportedByApp, localesOnDevice);
		return suitableLocale;
	}

	private List<Locale> getLocalesSupportedByApp() {
		List<Locale> locales = new ArrayList<>();
		for (String languageTag : BuildConfigurations.INSTANCE.getInstance().getSupportedLangugaes()) {
			String dashedLanguageTag = convertLanguageTagToDashedLanguageTag(languageTag);
			locales.add(Locale.forLanguageTag(dashedLanguageTag));
		}

		return locales;
	}

	private String convertLanguageTagToDashedLanguageTag(String languageTag) {
		return languageTag.replace("_", "-");
	}

	@NonNull
	private Locale getLocaleOnDevice() {
		return ConfigurationCompat
			.getLocales(resources.getConfiguration()).get(0);
	}

	private Locale findMostSuitableLocale(List<Locale> localesSupportedByApp,
		Locale localeOnDevice) {
		Locale localeWithSupportedLanguage = findLocaleWithLanguage(
			localesSupportedByApp,
			localeOnDevice.getLanguage());

		if (localeWithSupportedLanguage != null) {
			return localeWithSupportedLanguage;
		}
		return localesSupportedByApp.get(0);
	}

	@Nullable
	private Locale findLocaleWithLanguage(List<Locale> locales, String language) {
		for (Locale locale : locales) {
			if (locale.getLanguage().equalsIgnoreCase(language)) {
				return locale;
			}
		}

		return null;
	}

	private String getLocaleAsUnderscoredLanguageTag(Locale locale) {
		return locale.toLanguageTag().replace("-", "_");
	}
}
