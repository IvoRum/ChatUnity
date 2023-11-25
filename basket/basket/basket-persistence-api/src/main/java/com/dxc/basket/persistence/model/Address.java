package com.dxc.basket.persistence.model;

public interface Address {
  Country getCountry();

  String getCity();

  String getStreet();

  String getPostalCode();
}
