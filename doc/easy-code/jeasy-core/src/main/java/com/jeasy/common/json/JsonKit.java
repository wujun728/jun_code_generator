package com.jeasy.common.json;

import com.google.common.collect.Maps;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.jeasy.common.Func;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public final class JsonKit {

    private JsonKit() {
    }

    public static final TypeToken<Map<String, String>> MAP_STR_TYPE_TOKEN = new TypeToken<Map<String, String>>() {
    };

    public static final TypeToken<Map<String, Object>> MAP_OBJ_TYPE_TOKEN = new TypeToken<Map<String, Object>>() {
    };

    public static final JsonDeserializer<Map<String, Object>> MAP_OBJ_JSON_DESE = new JsonDeserializer<Map<String, Object>>() {
        @Override
        public Map<String, Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Map<String, Object> map = Maps.newHashMap();
            JsonObject jsonObject = json.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
            for (Map.Entry<String, JsonElement> entry : entrySet) {
                map.put(entry.getKey(), entry.getValue());
            }
            return map;
        }
    };

    public static final TypeAdapter<Map<String, Object>> MAP_OBJ_TYPE_ADAPTER = new TypeAdapter<Map<String, Object>>() {
        @Override
        public void write(JsonWriter out, Map<String, Object> value) throws IOException {

        }

        @Override
        public Map<String, Object> read(JsonReader in) throws IOException {
            return null;
        }
    };

    public static JsonObject parseJsonObject(final String json) {
        return new JsonParser().parse(json).getAsJsonObject();
    }

    public static JsonArray parseJsonArray(String fromJson, String alias) {
        JsonObject jsonObject = parseJsonObject(fromJson);
        return jsonObject.getAsJsonArray(alias);
    }

    public static <T> T fromJson(final String json, final Type type) {
        Gson gson = new GsonBuilder().disableHtmlEscaping()
            .registerTypeAdapter(MAP_OBJ_TYPE_TOKEN.getType(), MAP_OBJ_JSON_DESE)
            .create();
        return gson.fromJson(json, type);
    }

    public static <T> T fromJson(final String json, final Class<T> clazz) {
        return fromJson(json, (Type) clazz);
    }

    public static <T> T fromJson(final JsonElement jsonElement, final Type type) {
        Gson gson = new GsonBuilder().disableHtmlEscaping()
            .registerTypeAdapter(MAP_OBJ_TYPE_TOKEN.getType(), MAP_OBJ_JSON_DESE)
            .create();
        return gson.fromJson(jsonElement, type);
    }

    public static String fromJson(final String json, final String key) {
        return fromJson(json, key, String.class);
    }

    public static <T> T fromJson(final String json, final String key, final Class<T> clazz) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        if (!jsonObject.has(key)) {
            return null;
        }

        JsonElement targetElement = jsonObject.get(key);
        return fromJson(targetElement, (Type) clazz);
    }

    public static String toJson(final Object src) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(src);
    }

    public static String toJsonSerializeNulls(final Object src) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(src);
    }

    public static String put(final String json, final String key, final String value) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        Map<String, String> map = gson.fromJson(json, MAP_STR_TYPE_TOKEN.getType());
        map.put(key, value);
        return gson.toJson(map);
    }

    public static Map<String, String> jsonToMap(final String json) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.fromJson(json, MAP_STR_TYPE_TOKEN.getType());
    }

    public static boolean isJsonPrimitive(final String json, final String key) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        JsonElement targetElement = jsonObject.get(key);
        return !Func.isEmpty(targetElement) && targetElement.isJsonPrimitive();
    }

    public static boolean isJsonArray(final String json, final String key) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        JsonElement targetElement = jsonObject.get(key);
        return !Func.isEmpty(targetElement) && targetElement.isJsonArray();
    }

    public static boolean isJsonObject(final String json, final String key) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        JsonElement targetElement = jsonObject.get(key);
        return !Func.isEmpty(targetElement) && targetElement.isJsonObject();
    }

    public static boolean hasKey(String json, String key) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        return jsonObject.has(key);
    }

    public static void main(String[] args) {
        String json = "{\"id\":0, \"name\":\"tmk\", \"date\":\"2017-12-02 10:00:00\"}";
        System.out.println(JsonKit.fromJson("2017-12-02 10:00:00", String.class));
    }
}

