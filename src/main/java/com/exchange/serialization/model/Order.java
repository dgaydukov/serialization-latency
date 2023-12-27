package com.exchange.serialization.model;

import lombok.Data;

@Data
public class Order {

  private long orderId;
  private String clOrdId;
  private int account;
  private char side;
  private double price;
  private double orderQty;
  private String symbol;
  private int securityId;
  private char ordType;
}