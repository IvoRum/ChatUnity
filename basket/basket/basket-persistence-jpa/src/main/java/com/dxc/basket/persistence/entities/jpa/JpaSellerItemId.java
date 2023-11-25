package com.dxc.basket.persistence.entities.jpa;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class JpaSellerItemId implements Serializable {
    private static final long serialVersionUID = 1;

    private String sellerEmail;
    private String itemSerial;

    public JpaSellerItemId() {
        // Needed by JPA
    }

    @SuppressWarnings("nls")
    public JpaSellerItemId(final String sellerEmail, final String itemSerial) {
        Objects.requireNonNull(sellerEmail, "Seller should not be empty!");
        Objects.requireNonNull(itemSerial, "Item should not be empty!");

        this.sellerEmail = sellerEmail;
        this.itemSerial = itemSerial;
    }

    public String getSeller() {
        return sellerEmail;
    }

    public String getItem() {
        return itemSerial;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sellerEmail, itemSerial);
    }

    @Override
    public boolean equals(final Object other) {
        return other instanceof final JpaSellerItemId otherId
            && Objects.equals(sellerEmail, otherId.sellerEmail)
            && Objects.equals(itemSerial, otherId.itemSerial);
    }
}
