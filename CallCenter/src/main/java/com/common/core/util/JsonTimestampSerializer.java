package com.common.core.util;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class JsonTimestampSerializer extends JsonSerializer<Date> {
	public DateFormat df=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

	@Override
	public void serialize(Date value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
            JsonProcessingException {
		jgen.writeString(df.format(value));
		
	}

}
