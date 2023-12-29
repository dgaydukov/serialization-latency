package com.exchange.serialization.serializer;

import com.exchange.serialization.MockData;
import com.exchange.serialization.model.Order;
import com.exchange.serialization.proto.ProtobufSchema;
import com.exchange.serialization.sbe.OrderDecoder;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class OrderSerializerTest {

  private static Stream<Arguments> getSerializers() {
    byte[] customByteArr = new byte[]{49, 126, 97, 112, 105, 95, 49, 50, 51, 52, 53, 126, 49, 126,
        49, 126, 49, 48, 48, 46, 48, 126, 49, 48, 46, 48, 126, 66, 84, 67, 47, 85, 83, 68, 84, 126,
        49, 126, 50};
    String customStr = "1~api_12345~1~1~100.0~10.0~BTC/USDT~1~2";
    byte[] jsonByteArr = new byte[]{123, 34, 111, 114, 100, 101, 114, 73, 100, 34, 58, 49, 44, 34,
        99, 108, 79, 114, 100, 73, 100, 34, 58, 34, 97, 112, 105, 95, 49, 50, 51, 52, 53, 34, 44,
        34, 97, 99, 99, 111, 117, 110, 116, 34, 58, 49, 44, 34, 115, 105, 100, 101, 34, 58, 34, 49,
        34, 44, 34, 112, 114, 105, 99, 101, 34, 58, 49, 48, 48, 46, 48, 44, 34, 111, 114, 100, 101,
        114, 81, 116, 121, 34, 58, 49, 48, 46, 48, 44, 34, 115, 121, 109, 98, 111, 108, 34, 58, 34,
        66, 84, 67, 47, 85, 83, 68, 84, 34, 44, 34, 115, 101, 99, 117, 114, 105, 116, 121, 73, 100,
        34, 58, 49, 44, 34, 111, 114, 100, 84, 121, 112, 101, 34, 58, 34, 50, 34, 125};
    String jsonStr = "{\"orderId\":1,\"clOrdId\":\"api_12345\",\"account\":1,\"side\":\"1\",\"price\":100.0,\"orderQty\":10.0,\"symbol\":\"BTC/USDT\",\"securityId\":1,\"ordType\":\"2\"}";
    byte[] protobufByteArr = new byte[]{8, 1, 18, 9, 97, 112, 105, 95, 49, 50, 51, 52, 53, 24, 1,
        32, 49, 41, 0, 0, 0, 0, 0, 0, 89, 64, 49, 0, 0, 0, 0, 0, 0, 36, 64, 58, 8, 66, 84, 67, 47,
        85, 83, 68, 84, 64, 1, 72, 50};
    byte[] sbeByteArr = new byte[]{98, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 97, 112, 105,
        95, 49, 50, 51, 52, 53, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        1, 0, 0, 0, 49, 0, 0, 0, 0, 0, 0, 0, 89, 64, 0, 0, 0, 0, 0, 0, 36, 64, 1, 0, 50, 0, 66, 84,
        67, 47, 85, 83, 68, 84, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    return Stream.of(
        Arguments.of(new CustomTextSerializer(), customByteArr, customStr),
        Arguments.of(new JsonOrderSerializer(), jsonByteArr, jsonStr),
        Arguments.of(new ProtobufSerializer(), protobufByteArr, null),
        Arguments.of(new SbeOrderSerializer(), sbeByteArr, null)
    );
  }

  @ParameterizedTest
  @MethodSource("getSerializers")
  public void nullValueTest(Serializer serializer) {
    Assertions.assertNull(serializer.serialize(null));
    Assertions.assertNull(serializer.deserialize(null));
  }

  @ParameterizedTest
  @MethodSource("getSerializers")
  public void serializeTest(Serializer serializer, byte[] expectedByteArr, String expectedStr) {
    Order order = MockData.buyLimitOrder();
    byte[] serialized = serializer.serialize(order);
    Assertions.assertArrayEquals(expectedByteArr, serialized, "serialized array mismatch");
    if (expectedStr != null) {
      Assertions.assertEquals(expectedStr, new String(serialized), "serialized string mismatch");
    }
    Object obj = serializer.deserialize(serialized);
    Order deserialized;
    if (obj instanceof Order jsonOrder) {
      deserialized = jsonOrder;
    } else if (obj instanceof ProtobufSchema.Order protobufOrder) {
      deserialized = new Order();
      deserialized.setOrderId(protobufOrder.getOrderId());
      deserialized.setClOrdId(protobufOrder.getClOrdId());
      deserialized.setAccount(protobufOrder.getAccount());
      deserialized.setSide((char) protobufOrder.getSide());
      deserialized.setPrice(protobufOrder.getPrice());
      deserialized.setOrderQty(protobufOrder.getOrderQty());
      deserialized.setSymbol(protobufOrder.getSymbol());
      deserialized.setSecurityId(protobufOrder.getSecurityId());
      deserialized.setOrdType((char) protobufOrder.getOrdType());
    } else if (obj instanceof OrderDecoder sbeOrder) {
      deserialized = new Order();
      deserialized.setOrderId(sbeOrder.orderId());
      deserialized.setClOrdId(sbeOrder.clOrdId());
      deserialized.setAccount(sbeOrder.account());
      deserialized.setSide((char) sbeOrder.side());
      deserialized.setPrice(sbeOrder.price());
      deserialized.setOrderQty(sbeOrder.orderQty());
      deserialized.setSymbol(sbeOrder.symbol());
      deserialized.setSecurityId(sbeOrder.securityId());
      deserialized.setOrdType((char) sbeOrder.ordType());
    } else {
      throw new RuntimeException("unknown order object returned");
    }
    Assertions.assertEquals(order, deserialized, "deserialized object mismatch");
  }
}