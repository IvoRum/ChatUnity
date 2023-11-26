package com.dxc.basket.persistence.entities.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.dxc.basket.persistence.model.Address;
import com.dxc.basket.persistence.model.Login;
import com.dxc.basket.persistence.model.Seller;
import com.dxc.basket.persistence.model.SellerItem;

@Entity
@NamedQuery(name = "ql.sellers", query = "SELECT seller FROM JpaSeller AS seller")
public class JpaSeller implements Seller {

    @Id
    private String email;

    private String name;

    @Embedded
    private JpaLogin login;

    @OneToOne(targetEntity = JpaAddress.class, cascade = {
        CascadeType.PERSIST, CascadeType.MERGE
    })
    private Address address;

    @OneToMany(targetEntity = JpaSellerItem.class, mappedBy = "seller", cascade = {
        CascadeType.PERSIST, CascadeType.MERGE
    })
    private Set<SellerItem> items;

    protected JpaSeller() {
        // Required by JPA
    }

    @SuppressWarnings("nls")
    public JpaSeller(final String email, final String name, final Login login, final Address address) {
        assert email != null : "Email should not be empty!";
        assert name != null : "Name should not be empty!";
        assert login != null : "Login should not be empty!";
        assert address != null : "Address should not be empty!";

        this.email = email;
        this.name = name;
        this.login = new JpaLogin(login.userName(), login.password());
        this.address = address;
        items = new HashSet<>();
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
    public Address getAddress() {
        return address;
    }

    @Override
    public Set<SellerItem> getItems() {
        return items;
    }

    @Override
    public SellerItem getItem(final String serial) {
        for (final SellerItem item : items) {
            if (serial.equals(item.getItem().getSerial())) {
                return item;
            }
        }
        return null;
    }

    @Override
    public void addItem(final SellerItem item) {
        items.add(item);
    }

}
