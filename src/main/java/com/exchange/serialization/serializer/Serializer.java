package com.exchange.serialization.serializer;

public interface Serializer {

  String serialize(Object str);

  Object deserialize(String str);
}