package com.exchange.serialization.serializer;

import com.exchange.serialization.model.Order;
import com.exchange.serialization.helper.json.JsonSerializer;
import com.fasterxml.jackson.core.type.TypeReference;

public class JsonOrderSerializer implements Serializer {

  private final JsonSerializer jsonSerializer;

  public JsonOrderSerializer(JsonSerializer jsonSerializer) {
    this.jsonSerializer = jsonSerializer;
  }

  @Override
  public String serialize(Object obj) {
    return jsonSerializer.serialize(obj);
  }

  @Override
  public Object deserialize(String str) {
    return jsonSerializer.deserialize(str, new TypeReference<Order>() {
    });
  }
}