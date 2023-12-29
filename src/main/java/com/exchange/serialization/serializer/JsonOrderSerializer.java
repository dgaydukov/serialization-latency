package com.exchange.serialization.serializer;

import com.exchange.serialization.helper.json.JsonSerializerImpl;
import com.exchange.serialization.model.Order;
import com.exchange.serialization.helper.json.JsonSerializer;
import com.fasterxml.jackson.core.type.TypeReference;

public class JsonOrderSerializer implements Serializer {

  private final JsonSerializer jsonSerializer;

  public JsonOrderSerializer() {
    this(new JsonSerializerImpl());
  }

  public JsonOrderSerializer(JsonSerializer jsonSerializer) {
    this.jsonSerializer = jsonSerializer;
  }

  @Override
  public byte[] serialize(Order order) {
    if (order == null){
      return null;
    }
    return jsonSerializer.serialize(order).getBytes();
  }

  @Override
  public Object deserialize(byte[] arr) {
    if (arr == null){
      return null;
    }
    return jsonSerializer.deserialize(new String(arr), new TypeReference<Order>() {
    });
  }
}