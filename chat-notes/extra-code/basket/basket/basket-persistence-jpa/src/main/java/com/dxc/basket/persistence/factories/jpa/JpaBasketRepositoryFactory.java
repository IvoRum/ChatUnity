package com.dxc.basket.persistence.factories.jpa;

import java.io.Closeable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.dxc.basket.persistence.repositories.BasketRepository;
import com.dxc.basket.persistence.repositories.jpa.JpaBasketRepository;

/**
 * This code is only to run the basket example. The real code to work with
 * EntityManagerFactory and EntityManager is more complex. During our training
 * we will learn how to manage these persistence classes.
 */
public class JpaBasketRepositoryFactory implements Closeable {

    private EntityManagerFactory entityManagerFactory;
    private final EntityManager manager;

    @SuppressWarnings("nls")
    public JpaBasketRepositoryFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("basket-jpa-hibernate");
        manager = entityManagerFactory.createEntityManager();
    }

    public JpaBasketRepositoryFactory(final EntityManagerFactory entityManagerFactory) {
        manager = entityManagerFactory.createEntityManager();
    }

    public EntityManager getManager() {
        return manager;
    }

    public BasketRepository getRepository() {
        return new JpaBasketRepository(manager);
    }

    @Override
    public void close() {
        manager.close();
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
