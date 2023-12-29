package com.exchange.serialization.serializer;

import com.exchange.serialization.MockData;
import com.exchange.serialization.model.Order;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class OrderSerializerTest {

  private static Stream<Arguments> getSerializers() {
    return Stream.of(
        Arguments.of(new CustomTextSerializer()),
        Arguments.of(new JsonOrderSerializer()),
        Arguments.of(new ProtobufSerializer()),
        Arguments.of(new SbeOrderSerializer())
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
    byte[] serialized = serializer.serialize(order);
    Assertions.assertEquals(expected, serialized, "serialized string should match");
    Object obj = serializer.deserialize(serialized);
    Assertions.assertEquals(order, obj, "deserialized object should match");
  }
}