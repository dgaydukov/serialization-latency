package com.exchange.serialization.serializer;

import com.exchange.serialization.model.Order;

public class CustomTextSerializer implements Serializer {

  private final static String DIVIDER = "~";

  @Override
  public String serialize(Object obj) {
    if (obj == null){
      return null;
    }
    Order order = (Order) obj;
    StringBuilder sb = new StringBuilder();
    sb.append(order.getOrderId()).append(DIVIDER)
        .append(order.getClOrdId()).append(DIVIDER)
        .append(order.getAccount()).append(DIVIDER)
        .append(order.getSide()).append(DIVIDER)
        .append(order.getPrice()).append(DIVIDER)
        .append(order.getOrderQty()).append(DIVIDER)
        .append(order.getSymbol()).append(DIVIDER)
        .append(order.getSecurityId()).append(DIVIDER)
        .append(order.getOrdType());
    return sb.toString();
  }

  @Override
  public Object deserialize(String str) {
    if (str == null){
      return null;
    }
    Order order = new Order();
    String[] split = str.split(DIVIDER);
    order.setOrderId(Integer.parseInt(split[0]));
    order.setClOrdId(split[1]);
    order.setAccount(Integer.parseInt(split[2]));
    order.setSide(split[3].charAt(0));
    order.setPrice(Double.parseDouble(split[4]));
    order.setOrderQty(Double.parseDouble(split[5]));
    order.setSymbol(split[6]);
    order.setSecurityId(Integer.parseInt(split[7]));
    order.setOrdType(split[8].charAt(0));
    return order;
  }
}