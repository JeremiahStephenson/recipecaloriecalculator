package com.jerry.recipe.calorie.calculator.util;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.TypeReference;

public class Json {

    private static ObjectMapper sParseMapper;

    public static String toString(Object object) {
        String json = null;
        if (object != null) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);

            try {
                json = mapper.writeValueAsString(object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return json;
    }

    public static <T> T parse(String json, TypeReference<T> typeRef) {
        T object = null;

        if (json != null) {
            if (sParseMapper == null) {
                JsonFactory factory = new JsonFactory();
                sParseMapper = new ObjectMapper(factory);
            }

            try {
                object = sParseMapper.readValue(json, typeRef);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return object;
    }
}
