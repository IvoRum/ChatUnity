package com.dxc.basket.persistence.entities.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.dxc.basket.persistence.model.Item;
import com.dxc.basket.persistence.model.SellerItem;

@Entity
@NamedQuery(name = "ql.item.serial", query = "SELECT item FROM JpaItem AS item WHERE item.serial = :serial")
public class JpaItem implements Item {

    @Id
    private String serial;

    private String description;

    @OneToMany(targetEntity = JpaSellerItem.class, mappedBy = "item")
    private Set<SellerItem> sellerItems;

    protected JpaItem() {
        // Needed by JPA.
    }

    @SuppressWarnings("nls")
    public JpaItem(final String serial, final String description) {
        assert serial != null : "Serial code should not be empty!";
        assert description != null : "Description should not be empty!";

        this.serial = serial;
        this.description = description;
        this.sellerItems = new HashSet<>();
    }

    @Override
    public String getSerial() {
        return serial;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Set<SellerItem> getSellerItems() {
        return sellerItems;
    }
}
