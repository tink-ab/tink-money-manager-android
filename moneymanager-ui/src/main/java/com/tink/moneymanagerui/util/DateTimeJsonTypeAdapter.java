package com.tink.moneymanagerui.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import org.joda.time.DateTime;

public class DateTimeJsonTypeAdapter implements JsonSerializer<DateTime>,
	JsonDeserializer<DateTime> {

	@Override
	public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject object = new JsonObject();
		object.addProperty("time", src.getMillis());
		return object;
	}

	@Override
	public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
		throws JsonParseException {
		return new DateTime(json.getAsJsonObject().get("time").getAsLong());
	}

}
