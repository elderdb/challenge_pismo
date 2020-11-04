package com.pismo.challenge.utils.date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

public class CustomDateDeserializer extends JsonDeserializer<DateTime> implements ContextualDeserializer {
  private String format;

  public CustomDateDeserializer() {
  }

  public CustomDateDeserializer(String format) {
    this.format = format;
  }

  @Override
  public JsonDeserializer<?> createContextual(DeserializationContext dc, BeanProperty bp)
      throws JsonMappingException {
    return new CustomDateDeserializer(bp.getAnnotation(CustomDatetime.class).value());
  }

  @Override
  public DateTime deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
    DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
    DateTime dateTime = formatter.parseDateTime(jp.getText());
    return dateTime;
  }
}