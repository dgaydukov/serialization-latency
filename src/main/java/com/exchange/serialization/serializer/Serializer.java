package com.exchange.serialization.serializer;

import com.exchange.serialization.model.Order;

public interface Serializer {

  byte[] serialize(Order order);

  Object deserialize(byte[] arr);
}