package com.dxc.basket.persistence.entities.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.dxc.basket.persistence.model.Address;
import com.dxc.basket.persistence.model.Customer;
import com.dxc.basket.persistence.model.Login;
import com.dxc.basket.persistence.model.Order;

@Entity
@NamedQuery(name = "ql.customers", query = "SELECT customer FROM JpaCustomer AS customer")
public class JpaCustomer implements Customer {

    @Id
    private String email;

    private String name;

    @Embedded
    private JpaLogin login;

    @ManyToMany(targetEntity = JpaAddress.class, cascade = {
        CascadeType.PERSIST, CascadeType.MERGE
    })
    private Set<Address> addresses;

    @OneToMany(targetEntity = JpaOrder.class, mappedBy = "customer", cascade = CascadeType.REMOVE)
    private Set<Order> orders;

    protected JpaCustomer() {
        // Needed by JPA.
    }

    @SuppressWarnings("nls")
    public JpaCustomer(final String email, final String name, final Login login) {
        assert email != null : "Email should not be empty!";
        assert name != null : "Name should not be empty!";
        assert login != null : "Login should not be empty!";

        this.email = email;
        this.name = name;
        this.login = new JpaLogin(login.userName(), login.password());
        this.addresses = new HashSet<>();
        this.orders = new HashSet<>();
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Login getLogin() {
        return new Login(login.getUsername(), login.getPassword());
    }

    @Override
    public Set<Address> getAddresses() {
        return addresses;
    }

    @Override
    public void addAddress(final Address address) {
        addresses.add(address);
    }

    @Override
    public Set<Order> getOrders() {
        return orders;
    }

    @SuppressWarnings("nls")
    @Override
    public void addOrder(final Order order) {
        assert order != null : "Order should not be empty!";

        orders.add(order);
    }

}
