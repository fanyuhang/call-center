package com.common.core.util;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class JsonTimestampDeserializer extends JsonDeserializer<Date> {
    public DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String text = jsonParser.getText();
        try {
            return df.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
