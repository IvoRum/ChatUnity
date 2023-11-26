package com.dxc.basket.persistence.entities.jpa;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.dxc.basket.persistence.model.Customer;
import com.dxc.basket.persistence.model.Order;
import com.dxc.basket.persistence.model.OrderItem;

@Entity
@NamedQuery(name = "ql.orders.customer", query = "SELECT DISTINCT o FROM JpaOrder o WHERE o.customer = :customer")
@NamedQuery(name = "ql.orders.seller",
    query = "SELECT DISTINCT o FROM JpaOrder o JOIN o.items item WHERE item.sellerItem.seller = :seller")
@NamedQuery(name = "ql.orders.customer.seller",
    query = "SELECT DISTINCT o FROM JpaOrder o INNER JOIN o.items item WHERE o.customer = :customer AND item.sellerItem.seller = :seller")
@NamedQuery(name = "ql.orders.customer.item",
    query = "SELECT DISTINCT o FROM JpaOrder o INNER JOIN o.items item WHERE o.customer = :customer AND item.sellerItem.item = :item")
@NamedQuery(name = "ql.orders.seller.item",
    query = "SELECT DISTINCT o FROM JpaOrder o INNER JOIN o.items item WHERE item.sellerItem.seller = :seller AND item.sellerItem.item = :item")
public class JpaOrder implements Order {

    @Id
    private String id;

    @ManyToOne(targetEntity = JpaCustomer.class)
    private Customer customer;

    @OneToMany(targetEntity = JpaOrderItem.class, mappedBy = "order", cascade = {
        CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE
    })
    private Set<OrderItem> items;

    @Transient
    private double totalPrice;

    protected JpaOrder() {
        // Needed by JPA.
    }

    @SuppressWarnings("nls")
    public JpaOrder(final Customer customer) {
        assert customer != null : "Customer should not be null!";

        this.id = UUID.randomUUID().toString();
        this.customer = customer;
        this.customer.addOrder(this);
        this.items = new HashSet<>();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public Set<OrderItem> getItems() {
        return items;
    }

    @SuppressWarnings("nls")
    @Override
    public void addItem(final OrderItem item) {
        assert item != null : "Order item should not be null!";

        items.add(item);
        this.totalPrice = calculateTotalPrice();
    }

    @Override
    public double getTotalPrice() {
        return totalPrice;
    }

    @SuppressWarnings("nls")
    private double calculateTotalPrice() {
        assert items != null : "Order items should not be null!";

        double result = 0;
        for (final OrderItem item : items) {
            result += item.getTotalPrice();
        }
        return result;
    }
}
