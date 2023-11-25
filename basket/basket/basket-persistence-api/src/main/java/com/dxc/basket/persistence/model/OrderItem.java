package com.dxc.basket.persistence.model;

public interface OrderItem {
  Order getOrder();

  SellerItem getSellerItem();

  int getOrderedQuantity();

  double getTotalPrice();
}
