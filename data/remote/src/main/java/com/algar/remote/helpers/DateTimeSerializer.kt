package com.algar.remote.helpers

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import org.joda.time.DateTime

enum class DateTimeSerializer : JsonSerializer<DateTime>, JsonDeserializer<DateTime> {
    INSTANCE;

    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        tType: Type,
        ctx: JsonDeserializationContext
    ): DateTime {
        return json.asString.toDate()
    }

    override fun serialize(
        src: DateTime,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        return JsonPrimitive(src.toIsoString())
    }
}
