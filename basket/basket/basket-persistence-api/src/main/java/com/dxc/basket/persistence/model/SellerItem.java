package com.dxc.basket.persistence.model;

public interface SellerItem {
  Seller getSeller();

  Item getItem();

  int getAvailableQuantity();

  double getSellerPrice();
}
