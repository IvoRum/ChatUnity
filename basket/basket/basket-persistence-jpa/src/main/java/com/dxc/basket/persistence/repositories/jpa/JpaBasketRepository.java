package com.dxc.basket.persistence.repositories.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import com.dxc.basket.persistence.entities.jpa.JpaAddress;
import com.dxc.basket.persistence.entities.jpa.JpaCustomer;
import com.dxc.basket.persistence.entities.jpa.JpaItem;
import com.dxc.basket.persistence.entities.jpa.JpaOrder;
import com.dxc.basket.persistence.entities.jpa.JpaOrderItem;
import com.dxc.basket.persistence.entities.jpa.JpaSeller;
import com.dxc.basket.persistence.entities.jpa.JpaSellerItem;
import com.dxc.basket.persistence.model.Address;
import com.dxc.basket.persistence.model.Country;
import com.dxc.basket.persistence.model.Customer;
import com.dxc.basket.persistence.model.Item;
import com.dxc.basket.persistence.model.Login;
import com.dxc.basket.persistence.model.Order;
import com.dxc.basket.persistence.model.OrderItem;
import com.dxc.basket.persistence.model.Seller;
import com.dxc.basket.persistence.model.SellerItem;
import com.dxc.basket.persistence.repositories.BasketRepository;

@SuppressWarnings("nls")
public final class JpaBasketRepository implements BasketRepository {

    private enum Mode {
        JPQL, // Best when working with static/compiletime queries
        JPA_CRITERIA, // Best when working with dynamic/runtime queries
    }

    // Use this to switch mode JPQL/JPA_CRITERIA
    private static final Mode MODE = Mode.JPQL;

    private final EntityManager em;

    public JpaBasketRepository(final EntityManager em) {
        assert em != null : "EntityManager should not be null!";

        this.em = em;
    }

    @Override
    public Customer createCustomer(final String email, final String name, final Login login) {
        assert email != null : "Email should not be null!";
        assert name != null : "Name should not be null!";
        assert login != null : "Login should not be null!";

        final Customer customer = new JpaCustomer(email, name, login);
        em.persist(customer);
        return customer;
    }

    @Override
    public List<Customer> findAllCustomers() {
        if (MODE == Mode.JPQL) {
            return em.createNamedQuery("ql.customers", Customer.class).getResultList();
        } else {
            assert MODE == Mode.JPA_CRITERIA;
            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
            final Root<Customer> from = cq.from(Customer.class);
            cq.select(from);
            return em.createQuery(cq).getResultList();
        }
    }

    @Override
    public Customer findCustomer(final String email) {
        assert email != null : "Email should not be null!";

        return em.find(JpaCustomer.class, email);
    }

    @Override
    public Address addAddressTo(final Customer customer, final Country country, final String city, final String street,
                                final String postalCode) {
        assert customer != null : "Customer should not be null!";
        assert country != null : "Country should not be null!";
        assert city != null : "City should not be null!";
        assert street != null : "Street should not be null!";
        assert postalCode != null : "PostalCode should not be null!";

        final Address address = new JpaAddress(country, city, street, postalCode);
        customer.addAddress(address);
        em.persist(address);
        return address;
    }

    @Override
    public Seller createSeller(final String email, final String name, final Login login, final Country country,
                               final String city, final String street, final String postalCode) {
        assert email != null : "Email should not be null!";
        assert name != null : "Name should not be null!";
        assert login != null : "Login should not be null!";
        assert country != null : "Country should not be null!";
        assert city != null : "City should not be null!";
        assert street != null : "Street should not be null!";
        assert postalCode != null : "PostalCode should not be null!";

        final Address address = new JpaAddress(country, city, street, postalCode);
        em.persist(address);

        final Seller seller = new JpaSeller(email, name, login, address);
        em.persist(seller);
        return seller;
    }

    @Override
    public List<Seller> findAllSellers() {
        if (MODE == Mode.JPQL) {
            return em.createNamedQuery("ql.sellers", Seller.class).getResultList();
        } else {
            assert MODE == Mode.JPA_CRITERIA;

            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<Seller> cq = cb.createQuery(Seller.class);
            final Root<Seller> from = cq.from(Seller.class);
            cq.select(from);
            return em.createQuery(cq).getResultList();
        }
    }

    @Override
    public Seller findSeller(final String email) {
        assert email != null : "Email should not be null!";

        return em.find(JpaSeller.class, email);
    }

    @Override
    public Item findItem(final String serial) {
        assert serial != null : "Serial should not be null!";

        return em.find(JpaItem.class, serial);
    }

    @Override
    public SellerItem addItemTo(final Seller seller, final String serial, final String description,
                                final int availableQuantity, final double sellerPrice) {
        assert seller != null : "Seller should not be null!";
        assert serial != null : "Serial should not be null!";
        assert description != null : "Description should not be empty!";

        final Item item = new JpaItem(serial, description);
        em.persist(item);
        final SellerItem sellerItem = new JpaSellerItem(seller, item, availableQuantity, sellerPrice);
        em.persist(sellerItem);

        return sellerItem;
    }

    @Override
    public SellerItem addItemTo(final Seller seller, final Item item, final int availableQuantity,
                                final double sellerPrice) {
        assert seller != null : "Seller should not be null!";
        assert item != null : "Item should not be null!";

        final SellerItem sellerItem = new JpaSellerItem(seller, item, availableQuantity, sellerPrice);
        em.persist(sellerItem);
        return sellerItem;
    }

    @Override
    public Order createOrder(final Customer customer) {
        assert customer != null : "Customer should not be null!";

        final Order order = new JpaOrder(customer);
        em.persist(order);
        return order;
    }

    @Override
    public List<Order> findOrders(final Customer customer) {
        assert customer != null : "Customer should not be null!";

        if (MODE == Mode.JPQL) {
            return em
                .createNamedQuery("ql.orders.customer", Order.class)
                .setParameter("customer", customer)
                .getResultList();
        } else {
            assert MODE == Mode.JPA_CRITERIA;

            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<Order> cq = cb.createQuery(Order.class);
            final Root<Order> from = cq.from(Order.class);
            final ParameterExpression<Customer> cp = cb.parameter(Customer.class);
            cq.select(from).where(cb.equal(from.get("customer"), cp));
            return em.createQuery(cq).setParameter(cp, customer).getResultList();
        }
    }

    @Override
    public List<Order> findOrders(final Seller seller) {
        assert seller != null : "Seller should not be null!";

        if (MODE == Mode.JPQL) {
            return em.createNamedQuery("ql.orders.seller", Order.class).setParameter("seller", seller).getResultList();
        } else {
            assert MODE == Mode.JPA_CRITERIA;

            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<Order> cq = cb.createQuery(Order.class);
            final Root<Order> from = cq.from(Order.class);
            final Join<Order, OrderItem> join = from.join("items", JoinType.INNER);
            final ParameterExpression<Seller> sp = cb.parameter(Seller.class);
            cq.select(from).where(cb.equal(join.get("seller"), sp));
            return em.createQuery(cq).setParameter(sp, seller).getResultList();
        }
    }

    @Override
    public List<Order> findOrders(final Customer customer, final Seller seller) {
        assert customer != null : "Customer should not be null!";
        assert seller != null : "Seller should not be null!";

        if (MODE == Mode.JPQL) {
            return em
                .createNamedQuery("ql.orders.customer.seller", Order.class)
                .setParameter("customer", customer)
                .setParameter("seller", seller)
                .getResultList();
        } else {
            assert MODE == Mode.JPA_CRITERIA;

            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<Order> cq = cb.createQuery(Order.class);
            final Root<Order> from = cq.from(Order.class);
            final Join<Order, OrderItem> join = from.join("items", JoinType.INNER);
            final ParameterExpression<Customer> cp = cb.parameter(Customer.class);
            final ParameterExpression<Seller> sp = cb.parameter(Seller.class);
            cq.select(from).where(cb.equal(from.get("customer"), cp), cb.equal(join.get("seller"), sp));
            return em.createQuery(cq).setParameter(cp, customer).setParameter(sp, seller).getResultList();
        }
    }

    @Override
    public List<Order> findOrders(final Customer customer, final Item item) {
        assert customer != null : "Customer should not be null!";
        assert item != null : "Item should not be null!";

        if (MODE == Mode.JPQL) {
            return em
                .createNamedQuery("ql.orders.customer.item", Order.class)
                .setParameter("customer", customer)
                .setParameter("item", item)
                .getResultList();
        } else {
            assert MODE == Mode.JPA_CRITERIA;

            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<Order> cq = cb.createQuery(Order.class);
            final Root<Order> from = cq.from(Order.class);
            final Join<Order, OrderItem> join = from.join("items", JoinType.INNER);
            final ParameterExpression<Customer> cp = cb.parameter(Customer.class);
            final ParameterExpression<Item> ip = cb.parameter(Item.class);
            cq.select(from).where(cb.equal(from.get("customer"), cp), cb.equal(join.get("item"), ip));
            return em.createQuery(cq).setParameter(cp, customer).setParameter(ip, item).getResultList();
        }
    }

    @Override
    public List<Order> findOrders(final Seller seller, final Item item) {
        assert seller != null : "Seller should not be null!";
        assert item != null : "Item should not be null!";

        if (MODE == Mode.JPQL) {
            return em
                .createNamedQuery("ql.orders.seller.item", Order.class)
                .setParameter("seller", seller)
                .setParameter("item", item)
                .getResultList();
        } else {
            assert MODE == Mode.JPA_CRITERIA;

            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<Order> cq = cb.createQuery(Order.class);
            final Root<Order> from = cq.from(Order.class);
            final Join<Order, OrderItem> join = from.join("items", JoinType.INNER);
            final ParameterExpression<Seller> sp = cb.parameter(Seller.class);
            final ParameterExpression<Item> ip = cb.parameter(Item.class);
            cq.select(from).where(cb.equal(join.get("seller"), sp), cb.equal(join.get("item"), ip));
            return em.createQuery(cq).setParameter(sp, seller).setParameter(ip, item).getResultList();
        }
    }

    @Override
    public OrderItem addOrderItemTo(final Order order, final Seller seller, final Item item,
                                    final int orderedQuantity) {
        assert order != null : "Order should not be null!";
        assert seller != null : "Seller should not be null!";
        assert item != null : "Item should not be null!";

        final SellerItem sellerItem = seller.getItem(item.getSerial());
        final OrderItem orderItem = new JpaOrderItem(order, sellerItem, orderedQuantity);
        em.persist(order); // Also persists orderItems (see CascadeType.PERSIST). Is this the right way?
        return orderItem;
    }

    @Override
    public OrderItem addOrderItemTo(final Order order, final SellerItem sellerItem, final int orderedQuantity) {
        assert order != null : "Order should not be null!";
        assert sellerItem != null : "SellerItem should not be null!";

        em.persist(order); // Also persists orderItems (see CascadeType.PERSIST). Is this the right way?
        // JPA EntityManager cascades PERSIST at flush time.
        return new JpaOrderItem(order, sellerItem, orderedQuantity);
    }
}
