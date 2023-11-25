package com.dxc.basket.persistence.model;

import java.util.Set;

public interface Order {
    String getId();

    Customer getCustomer();

    Set<OrderItem> getItems();

    void addItem(OrderItem item);

    double getTotalPrice();
}
