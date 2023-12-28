package com.exchange.serialization.serializer;

import com.exchange.serialization.model.Order;
import com.exchange.serialization.sbe.MessageHeaderDecoder;
import com.exchange.serialization.sbe.MessageHeaderEncoder;
import com.exchange.serialization.sbe.OrderDecoder;
import com.exchange.serialization.sbe.OrderEncoder;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.agrona.concurrent.UnsafeBuffer;

public class SbeOrderSerializer implements Serializer {

  public SbeOrderSerializer() {

  }

  @Override
  public String serialize(Object obj) {
    Order order = (Order) obj;
    UnsafeBuffer buffer = new UnsafeBuffer(ByteBuffer.allocate(64));
    MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
    OrderEncoder encoder = new OrderEncoder().wrapAndApplyHeader(buffer, 0, headerEncoder);
    encoder.orderId(order.getOrderId());
    encoder.clOrdId();
    encoder.account(order.getAccount());
    encoder.side(order.getSide());
    encoder.price(order.getPrice());
    encoder.orderQty(order.getOrderQty());
    encoder.securityId(order.getSecurityId());
    encoder.ordType(order.getOrdType());
    encoder.symbol();
    return new String(buffer.byteArray());
  }

  @Override
  public Object deserialize(String str) {
    UnsafeBuffer buffer = new UnsafeBuffer(ByteBuffer.allocate(128));
    buffer.wrap(str.getBytes());

    MessageHeaderDecoder headerDecoder = new MessageHeaderDecoder();
    OrderDecoder dataDecoder = new OrderDecoder();
    dataDecoder.wrapAndApplyHeader(buffer, 0, headerDecoder);
    return dataDecoder;
  }
}