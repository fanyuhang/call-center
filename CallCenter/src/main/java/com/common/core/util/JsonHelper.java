package com.common.core.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * User: Allen
 * Date: 9/21/12
 */
public class JsonHelper {
    private static Logger logger = LoggerFactory.getLogger(JsonHelper.class);

    public static <T> T deserialize(String json, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            logger.error("parse json error:{}", e.getMessage());
        }
        return null;
    }

    public static <T> T deserialize(InputStream inputStream, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(inputStream, clazz);
        } catch (IOException e) {
            logger.error("parse json error:{}", e.getMessage());
        }
        return null;
    }

    public static boolean serialize(OutputStream out, Object value) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(out, value);
        } catch (IOException e) {
            logger.error("write json error:{}", e.getMessage());
            return false;
        }
        return true;
    }

    public static String serialize(Object value) {
        ObjectMapper mapper = new ObjectMapper();
        String result = "";
        try {
            result = mapper.writeValueAsString(value);
        } catch (IOException e) {
            logger.error("write json error:{}", e.getMessage());
        }
        return result;
    }
}
