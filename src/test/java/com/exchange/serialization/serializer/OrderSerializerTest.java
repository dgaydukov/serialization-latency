package com.exchange.serialization.serializer;

import com.exchange.serialization.MockData;
import com.exchange.serialization.helper.json.JsonSerializerImpl;
import com.exchange.serialization.model.Order;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class OrderSerializerTest {

  private static Stream<Arguments> getSerializers() {
    return Stream.of(
        Arguments.of(new JsonOrderSerializer(new JsonSerializerImpl()),
            "{\"orderId\":1,\"clOrdId\":\"api_12345\",\"account\":1,\"side\":\"1\",\"price\":100.0,\"orderQty\":10.0,\"symbol\":\"BTC/USDT\",\"securityId\":1,\"ordType\":\"2\"}"),
        Arguments.of(new CustomTextSerializer(), "1~api_12345~1~1~100.0~10.0~BTC/USDT~1~2")
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
  public void serializeTest(Serializer serializer, String expected) {
    Order order = MockData.buyLimitOrder();
    String serialized = serializer.serialize(order);
    Assertions.assertEquals(expected, serialized, "serialized string should match");
  }

  @ParameterizedTest
  @MethodSource("getSerializers")
  public void deserializeTest(Serializer serializer) {
    Order order = MockData.buyLimitOrder();
    String serialized = serializer.serialize(order);
    Order obj = (Order) serializer.deserialize(serialized);
    Assertions.assertEquals(order, obj, "deserialized object should match");
  }
}