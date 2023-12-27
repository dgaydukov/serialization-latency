package com.exchange.serialization.serializer.json;

import com.fasterxml.jackson.core.type.TypeReference;

public interface JsonSerializer {

  <T> String serialize(T obj);

  <T> T deserialize(String str, TypeReference<T> typeRef);
}