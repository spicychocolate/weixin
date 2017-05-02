package com.spicy.wechat.common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class DateJsonSerializer extends JsonSerializer<Date> {
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DateConstants.DATE_TIME);

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeString(SIMPLE_DATE_FORMAT.format(date));
    }
}
