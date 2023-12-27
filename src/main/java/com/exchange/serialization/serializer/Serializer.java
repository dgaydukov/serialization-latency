package com.exchange.serialization.serializer;

import com.exchange.serialization.model.Order;

public interface Serializer {

  String serialize(Object obj);

  Object deserialize(String str);
}