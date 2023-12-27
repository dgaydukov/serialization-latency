package com.exchange.serialization.helper.json;

import com.exchange.serialization.exception.JsonSerializeException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializerImpl implements JsonSerializer {

  private final ObjectMapper mapper = new ObjectMapper()
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  @Override
  public <T> String serialize(T obj) {
    if (obj == null) {
      return null;
    }
    try {
      return mapper.writeValueAsString(obj);
    } catch (JsonProcessingException ex) {
      throw new JsonSerializeException("Failed to write: obj=" + obj, ex);
    }
  }

  @Override
  public <T> T deserialize(String str, TypeReference<T> typeRef) {
    if (str == null) {
      return null;
    }
    try {
      return mapper.readValue(str, typeRef);
    } catch (JsonProcessingException ex) {
      throw new JsonSerializeException("Failed to read: str=" + str, ex);
    }
  }
}