package com.common.core.util;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.TypeSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
@Component
public class JsonDateSerializer extends JsonSerializer<Date> {
	private DateFormat df=new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void serialize(Date value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
            JsonProcessingException {
		jgen.writeString(df.format(value));
	}

	@Override
	public void serializeWithType(Date value, JsonGenerator jgen,
			SerializerProvider provider, TypeSerializer typeSer)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		super.serializeWithType(value, jgen, provider, typeSer);
	}

	@Override
	public Class<Date> handledType() {
		// TODO Auto-generated method stub
		return super.handledType();
	}

	
}
