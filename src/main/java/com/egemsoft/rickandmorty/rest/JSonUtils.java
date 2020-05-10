package com.egemsoft.rickandmorty.rest;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSonUtils {

    public static String jsonToString(Object jsonObject) {
        String json;

        if (jsonObject == null) {
            json = "Null Object";
        } else {
            ObjectMapper mapper = new ObjectMapper();
            try {
                json = mapper.writeValueAsString(jsonObject);
            } catch (Exception e) {
                json = "Object could not be converted to Json Format";
            }
        }

        return json;
    }

    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(jsonString, clazz);
        } catch (Exception e) {
            return null;
        }
    }
}
