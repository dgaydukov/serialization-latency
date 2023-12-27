package com.exchange.serialization.serializer;

import com.exchange.serialization.MockData;
import com.exchange.serialization.helper.json.JsonSerializerImpl;
import com.exchange.serialization.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JsonSerializerTest {
  private Serializer serializer;

  @BeforeEach
  public void beforeEach(){
    serializer = new JsonOrderSerializer(new JsonSerializerImpl());
  }

  @Test
  public void nullTest(){
    Assertions.assertNull(serializer.serialize(null));
    Assertions.assertNull(serializer.deserialize(null));
  }

  @Test
  public void serializeTest(){
    Order order = MockData.buyLimitOrder();
    String serialized = serializer.serialize(order);
    Assertions.assertEquals("{\"orderId\":1,\"clOrdId\":\"api_12345\",\"account\":1,\"side\":\"1\",\"price\":100.0,\"orderQty\":10.0,\"quoteOrderQty\":0.0,\"symbol\":\"BTC/USDT\",\"securityId\":1,\"ordType\":\"2\"}", serialized, "serialized string should match");
    Order obj = (Order) serializer.deserialize(serialized);
    Assertions.assertEquals(order, obj, "deserialized object should match");
  }

}
