package com.exchange.serialization.serializer;

public interface Serializer {

  String serialize(Object obj);

  Object deserialize(String str);
}