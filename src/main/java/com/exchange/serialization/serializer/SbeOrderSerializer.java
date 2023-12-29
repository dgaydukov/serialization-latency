package com.exchange.serialization.serializer;

import com.exchange.serialization.model.Order;
import com.exchange.serialization.sbe.MessageHeaderDecoder;
import com.exchange.serialization.sbe.MessageHeaderEncoder;
import com.exchange.serialization.sbe.OrderDecoder;
import com.exchange.serialization.sbe.OrderEncoder;
import java.nio.ByteBuffer;
import org.agrona.concurrent.UnsafeBuffer;

public class SbeOrderSerializer implements Serializer {

  public SbeOrderSerializer() {

  }

  @Override
  public byte[] serialize(Order order) {
    if (order == null){
      return null;
    }
    UnsafeBuffer buffer = new UnsafeBuffer(ByteBuffer.allocate(256));
    MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
    OrderEncoder encoder = new OrderEncoder().wrapAndApplyHeader(buffer, 0, headerEncoder);
    encoder.orderId(order.getOrderId());
    encoder.clOrdId(order.getClOrdId());
    encoder.account(order.getAccount());
    encoder.side(order.getSide());
    encoder.price(order.getPrice());
    encoder.orderQty(order.getOrderQty());
    encoder.securityId(order.getSecurityId());
    encoder.ordType(order.getOrdType());
    encoder.symbol(order.getSymbol());
    return buffer.byteArray();
  }

  @Override
  public Object deserialize(byte[] arr) {
    if (arr == null){
      return null;
    }
    UnsafeBuffer buffer = new UnsafeBuffer(ByteBuffer.allocate(128));
    buffer.wrap(arr);
    MessageHeaderDecoder headerDecoder = new MessageHeaderDecoder();
    OrderDecoder dataDecoder = new OrderDecoder();
    dataDecoder.wrapAndApplyHeader(buffer, 0, headerDecoder);
    return dataDecoder;
  }
}