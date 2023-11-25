package com.dxc.basket.persistence.model;

import java.util.Set;

public interface Customer {
  String getEmail();

  String getName();

  Login getLogin();

  Set<Address> getAddresses();

  void addAddress(Address address);

  Set<Order> getOrders();

  void addOrder(Order order);
}
