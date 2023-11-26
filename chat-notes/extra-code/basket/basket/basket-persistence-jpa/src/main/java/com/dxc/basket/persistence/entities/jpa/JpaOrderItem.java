package com.dxc.basket.persistence.entities.jpa;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Transient;

import com.dxc.basket.persistence.model.Order;
import com.dxc.basket.persistence.model.OrderItem;
import com.dxc.basket.persistence.model.SellerItem;

@Entity
public class JpaOrderItem implements OrderItem {

    @EmbeddedId
    private JpaOrderItemId id;

    @MapsId("orderId")
    @ManyToOne(targetEntity = JpaOrder.class)
    @JoinColumn(name = "orderId", referencedColumnName = "id")
    private Order order;

    // For some reason this combination of annotations doesn't work when seller item
    // is defined using its interface SellerItem. Until we find the reason we will
    // use the entity class and a down cast in the constructor.
    @MapsId("sellerItemId")
    @ManyToOne(targetEntity = JpaSellerItem.class)
    // @JoinColumn(name = "sellerEmail", referencedColumnName = "sellerEmail")
    // @JoinColumn(name = "itemSerial", referencedColumnName = "itemSerial")
    private JpaSellerItem sellerItem;

    private int orderedQuantity;

    @Transient
    private double totalPrice;

    protected JpaOrderItem() {
        // Needed by JPA.
    }

    @SuppressWarnings("nls")
    public JpaOrderItem(final Order order, final SellerItem sellerItem, final int orderedQuantity) {
        assert order != null : "Order should not be empty!";
        assert sellerItem != null : "SellerItem should not be empty!";

        id = new JpaOrderItemId(order.getId(),
            new JpaSellerItemId(sellerItem.getSeller().getEmail(), sellerItem.getItem().getSerial()));

        this.order = order;
        this.order.addItem(this);

        this.sellerItem = JpaSellerItem.class.cast(sellerItem);
        this.orderedQuantity = orderedQuantity;

        this.totalPrice = orderedQuantity * sellerItem.getSellerPrice();
    }

    @Override
    public Order getOrder() {
        return order;
    }

    @Override
    public SellerItem getSellerItem() {
        return sellerItem;
    }

    @Override
    public int getOrderedQuantity() {
        return orderedQuantity;
    }

    @Override
    public double getTotalPrice() {
        return totalPrice;
    }
}
