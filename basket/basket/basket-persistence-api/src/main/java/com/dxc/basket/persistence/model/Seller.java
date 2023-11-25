package com.dxc.basket.persistence.model;

import java.util.Set;

public interface Seller {
  String getEmail();

  String getName();

  Login getLogin();

  Address getAddress();

  Set<SellerItem> getItems();

  SellerItem getItem(String serial);

  void addItem(SellerItem item);
}
