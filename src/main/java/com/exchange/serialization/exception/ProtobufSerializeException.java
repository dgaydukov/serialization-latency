package com.exchange.serialization.exception;

public class ProtobufSerializeException extends RuntimeException {
  public ProtobufSerializeException(String msg, Exception ex) {
    super(msg, ex);
  }
}