package com.dxc.basket.persistence.repositories.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dxc.basket.persistence.factories.jpa.JpaBasketRepositoryFactory;
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

/**
 * Unit test for JpaBasketRepository. Never ever write your tests like that
 * !!!!!!! During our training we will learn how to properly test repositories.
 */
@SuppressWarnings("nls")
class JpaBasketRepositoryTest {

    private JpaBasketRepositoryFactory factory;
    private BasketRepository repo;

    @BeforeEach
    void init() {
        System.out.println("in setup");
        factory = new JpaBasketRepositoryFactory();
        repo = factory.getRepository();
    }

    @AfterEach
    void cleanup() {
        factory.close();
    }

    /**
     * Test repository.
     */
    @Test
    void badTestForRepository() {
        final EntityTransaction trx = factory.getManager().getTransaction();

        trx.begin();

        /* TEST FACTORY METHODS */
        final Customer customer1 = repo
            .createCustomer("customer1@dxc.com", "Customer1", new Login("customer1", "qwerty"));
        assertNotNull(customer1);
        final Customer customer2 = repo
            .createCustomer("customer2@dxc.com", "Customer2", new Login("customer2", "qwerty"));
        assertNotNull(customer2);

        final Address address1 = repo.addAddressTo(customer1, Country.BULGARIA, "Sofia", "King Simeon str.", "1000");
        assertNotNull(address1);
        final Address address2 = repo.addAddressTo(customer1, Country.BULGARIA, "Burgas", "King Samuil str.", "8000");
        assertNotNull(address2);

        final Seller seller1 = repo
            .createSeller("office@dxc.com", "DXC Technologies", new Login("office", "qwerty"), Country.BULGARIA,
                          "Sofia", "Street1", "1000");
        assertNotNull(seller1);
        final Seller seller2 = repo
            .createSeller("office@hpe.com", "HPE", new Login("office", "qwerty"), Country.BULGARIA, "Sofia", "Street2",
                          "1000");
        assertNotNull(seller2);

        final SellerItem sellerItem1 = repo.addItemTo(seller1, "SN11111111", "Item1", 1000, 9.99);
        assertNotNull(sellerItem1);
        final SellerItem sellerItem2 = repo.addItemTo(seller1, "SN22222222", "Item2", 100, 99.99);
        assertNotNull(sellerItem2);

        final Order order1 = repo.createOrder(customer1);
        assertNotNull(order1);
        final Order order2 = repo.createOrder(customer1);
        assertNotNull(order2);

        final OrderItem orderItem21 = repo.addOrderItemTo(order2, sellerItem1, 5);
        assertNotNull(orderItem21);
        final OrderItem orderItem11 = repo.addOrderItemTo(order1, sellerItem1, 10);
        assertNotNull(orderItem11);
        final OrderItem orderItem12 = repo.addOrderItemTo(order1, sellerItem2, 5);
        assertNotNull(orderItem12);

        trx.commit(); // Not finished - where is rollback?

        /* TEST SEARCH METHODS */
        // Return all customers.
        final List<Customer> customers = repo.findAllCustomers();
        assertNotNull(customers);
        assertEquals(2, customers.size());

        // Search customer by email.
        final Customer customer = repo.findCustomer("customer1@dxc.com");
        assertNotNull(customer);
        assertNotNull(customer.getEmail());
        assertNotNull(customer.getName());
        assertNotNull(customer.getLogin());
        assertNotNull(customer.getAddresses());
        assertEquals(2, customer.getAddresses().size());
        assertNotNull(customer.getOrders());
        assertEquals(2, customer.getOrders().size());

        // Return all sellers.
        final List<Seller> sellers = repo.findAllSellers();
        assertNotNull(sellers);
        assertEquals(2, sellers.size());

        // Search seller by email.
        final Seller seller = repo.findSeller("office@dxc.com");
        assertNotNull(seller);
        assertNotNull(seller.getEmail());
        assertNotNull(seller.getName());
        assertNotNull(seller.getLogin());
        assertNotNull(seller.getAddress());
        assertNotNull(seller.getItems());
        assertEquals(2, seller.getItems().size());

        // Search item by serial number.
        final Item item = repo.findItem("SN11111111");
        assertNotNull(item);

        // Search orders by customer.
        final List<Order> ordersByCustomer = repo.findOrders(customer1);
        assertEquals(2, ordersByCustomer.size());
        System.out.println(ordersByCustomer.get(0).getItems().size());
        System.out.println(ordersByCustomer.get(1).getItems().size());
        assertTrue(ordersByCustomer.get(0).getItems().size() > 0);
        assertTrue(ordersByCustomer.get(1).getItems().size() > 0);

        // Search orders by seller.
        final List<Order> ordersBySeller = repo.findOrders(seller1);
        assertEquals(2, ordersBySeller.size());

        // Search orders by customer & seller.
        final List<Order> ordersByCustomerAndSeller = repo.findOrders(customer1, seller1);
        assertEquals(2, ordersByCustomerAndSeller.size());

        // Search orders by customer & item.
        final List<Order> ordersByCustomerAndItem = repo.findOrders(customer1, item);
        assertEquals(2, ordersByCustomerAndItem.size());

        // Search orders by seller & item.
        final List<Order> ordersBySellerAndItem = repo.findOrders(seller1, item);
        assertEquals(2, ordersBySellerAndItem.size());
    }
}
