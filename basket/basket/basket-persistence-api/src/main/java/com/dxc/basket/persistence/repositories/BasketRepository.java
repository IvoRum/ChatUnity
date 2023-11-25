package com.dxc.basket.persistence.repositories;

import java.util.List;

import com.dxc.basket.persistence.model.Address;
import com.dxc.basket.persistence.model.Country;
import com.dxc.basket.persistence.model.Customer;
import com.dxc.basket.persistence.model.Item;
import com.dxc.basket.persistence.model.Login;
import com.dxc.basket.persistence.model.Order;
import com.dxc.basket.persistence.model.OrderItem;
import com.dxc.basket.persistence.model.Seller;
import com.dxc.basket.persistence.model.SellerItem;

// Not a good design. Should be split.
public interface BasketRepository {
    Customer createCustomer(String email, String name, Login login);

    List<Customer> findAllCustomers();

    Customer findCustomer(String email);

    Address addAddressTo(Customer customer, Country country, String city, String street, String postalCode);

    Seller createSeller(String email, String name, Login login, Country country, String city, String street,
                        String postalCode);

    List<Seller> findAllSellers();

    Seller findSeller(String email);

    Item findItem(String serial);

    SellerItem addItemTo(Seller seller, String serial, String description, int availableQuantity, double sellerPrice);

    SellerItem addItemTo(Seller seller, Item item, int availableQuantity, double sellerPrice);

    Order createOrder(Customer customer);

    List<Order> findOrders(Customer customer);

    List<Order> findOrders(Seller seller);

    List<Order> findOrders(Customer customer, Seller seller);

    List<Order> findOrders(Customer customer, Item item);

    List<Order> findOrders(Seller seller, Item item);

    OrderItem addOrderItemTo(Order order, Seller seller, Item item, int orderedQuantity);

    OrderItem addOrderItemTo(Order order, SellerItem sellerItem, int orderedQuantity);
}
