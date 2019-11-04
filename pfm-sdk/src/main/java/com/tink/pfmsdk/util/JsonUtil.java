package com.tink.pfmsdk.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.joda.time.DateTime;

public class JsonUtil {

	private static Gson gson;

	static {
		GsonBuilder gsonBuilder;
		gsonBuilder = new GsonBuilder();
		gsonBuilder.serializeNulls();
		gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeJsonTypeAdapter());

		gson = gsonBuilder.create();
	}

	public static Gson getGson() {
		return gson;
	}
}