package com.dxc.basket.persistence.entities.jpa;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.dxc.basket.persistence.model.Item;
import com.dxc.basket.persistence.model.Seller;
import com.dxc.basket.persistence.model.SellerItem;

@Entity
public class JpaSellerItem implements SellerItem {

    @EmbeddedId
    JpaSellerItemId id;

    @MapsId("sellerEmail")
    @ManyToOne(targetEntity = JpaSeller.class)
    @JoinColumn(name = "sellerEmail", referencedColumnName = "email")
    Seller seller;

    @MapsId("itemSerial")
    @ManyToOne(targetEntity = JpaItem.class)
    @JoinColumn(name = "itemSerial", referencedColumnName = "serial")
    Item item;

    private int availableQuantity;

    private double sellerPrice;

    JpaSellerItem() {
        // Needed by JPA.
    }

    @SuppressWarnings("nls")
    public JpaSellerItem(final Seller seller, final Item item, final int availableQuantity, final double sellerPrice) {
        assert seller != null : "Seller should not be null!";
        assert item != null : "Item should not be null!";

        id = new JpaSellerItemId(seller.getEmail(), item.getSerial());
        this.seller = seller;
        this.seller.addItem(this);
        this.item = item;
        this.availableQuantity = availableQuantity;
        this.sellerPrice = sellerPrice;
    }

    @Override
    public Seller getSeller() {
        return seller;
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public int getAvailableQuantity() {
        return availableQuantity;
    }

    @Override
    public double getSellerPrice() {
        return sellerPrice;
    }
}
