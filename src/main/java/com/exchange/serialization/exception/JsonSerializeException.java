package com.exchange.serialization.exception;

public class JsonSerializeException extends RuntimeException {
  public JsonSerializeException(String msg, Exception ex) {
    super(msg, ex);
  }
}