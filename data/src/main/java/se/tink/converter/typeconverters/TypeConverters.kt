package se.tink.converter.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import org.joda.time.DateTime
import se.tink.extensions.fromJson

class StringListConverters {
    @TypeConverter
    fun stringListToJson(list: List<String>): String = Gson().toJson(list)

    @TypeConverter
    fun jsonToStringList(json: String): List<String> = Gson().fromJson(json) ?: emptyList()
}

class DateTimeConverters {
    @TypeConverter
    fun dateTimeToLong(dateTime: DateTime): Long = dateTime.millis

    @TypeConverter
    fun longToDateTime(millis: Long): DateTime = DateTime(millis)
}