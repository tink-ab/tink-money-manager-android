package com.tink.moneymanagerui.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.lang.reflect.Type;
import org.joda.time.DateTime;

public class JsonUtils {

	static {
		GsonBuilder gsonBuilder;
		gsonBuilder = new GsonBuilder();
		gsonBuilder.serializeNulls();
		gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeJsonTypeAdapter());

		gson = gsonBuilder.create();
	}

	private static final Gson gson;

	public static String toJson(Object object) {
		return gson.toJson(object);
	}

	public static JsonElement toJsonTree(Object object) {
		return gson.toJsonTree(object);
	}

	public static JsonElement fromJson(String json) {
		return new JsonParser().parse(json);
	}

	public static <T> T fromJson(String json, Class<T> classOfT) {
		if (json == null) {
			return null;
		}
		return gson.fromJson(json, classOfT);
	}

	public static <T> T fromJson(String json, Type typeOfT) {
		return gson.fromJson(json, typeOfT);
	}
}
