package com.dxc.basket.persistence.entities.jpa;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class JpaOrderItemId implements Serializable {
    private static final long serialVersionUID = 1;

    private String orderId;
    private JpaSellerItemId sellerItemId;

    public JpaOrderItemId() {
        // Needed by JPA
    }

    @SuppressWarnings("nls")
    public JpaOrderItemId(final String orderId, final JpaSellerItemId sellerItemId) {
        assert orderId != null : "Order should not be null!";
        assert sellerItemId != null : "SellerItem should not be null!";

        this.orderId = orderId;
        this.sellerItemId = sellerItemId;
    }

    public String getOrderId() {
        return orderId;
    }

    public JpaSellerItemId getSellerItemId() {
        return sellerItemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, sellerItemId);
    }

    @Override
    public boolean equals(final Object other) {
        return other instanceof final JpaOrderItemId otherId
            && Objects.equals(orderId, otherId.orderId)
            && Objects.equals(sellerItemId, otherId.sellerItemId);
    }
}
