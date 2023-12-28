package com.exchange.serialization.serializer;

import com.exchange.serialization.exception.ProtobufSerializeException;
import com.exchange.serialization.model.Order;
import com.exchange.serialization.proto.ProtobufSchema;
import com.google.protobuf.InvalidProtocolBufferException;

public class ProtobufSerializer implements Serializer {

  @Override
  public String serialize(Object obj) {
    Order order = (Order) obj;
    ProtobufSchema.Order protobufOrder = ProtobufSchema.Order.newBuilder()
        .setOrderId(order.getOrderId())
        .setClOrdId(order.getClOrdId())
        .setAccount(order.getAccount())
        .setSide(order.getSide())
        .setPrice(order.getPrice())
        .setOrderQty(order.getOrderQty())
        .setSymbol(order.getSymbol())
        .setSecurityId(order.getSecurityId())
        .setOrdType(order.getOrdType())
        .build();
    return new String(protobufOrder.toByteArray());
  }

  @Override
  public Object deserialize(String str) {
    try{
      return ProtobufSchema.Order.newBuilder()
          .mergeFrom(str.getBytes())
          .build();
    } catch (InvalidProtocolBufferException ex){
      throw new ProtobufSerializeException("Failed to deserialize string", ex);
    }
  }
}