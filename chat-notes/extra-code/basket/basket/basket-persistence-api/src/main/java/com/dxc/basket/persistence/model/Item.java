package com.dxc.basket.persistence.model;

import java.util.Set;

public interface Item {
  String getSerial();

  String getDescription();

  Set<SellerItem> getSellerItems();
}
