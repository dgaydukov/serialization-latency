package com.exchange.serialization;

import com.exchange.serialization.model.Order;
import com.exchange.serialization.serializer.ProtobufSerializer;
import com.exchange.serialization.serializer.Serializer;
import org.junit.jupiter.api.Test;

public class MockData {

  public static Order buyLimitOrder() {
    Order order = new Order();
    order.setOrderId(1);
    order.setClOrdId("api_12345");
    order.setAccount(1);
    order.setSide('1');
    order.setPrice(100);
    order.setOrderQty(10);
    order.setSymbol("BTC/USDT");
    order.setSecurityId(1);
    order.setOrdType('2');
    return order;
  }

  @Test
  public void test(){
    Serializer serializer = new ProtobufSerializer();
    String serialized = serializer.serialize(buyLimitOrder());
    System.out.println(serialized);
    Object obj = serializer.deserialize(serialized);
    System.out.println(obj);
  }
}