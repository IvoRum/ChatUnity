package com.tu.varna.chat.factory.jpa;

import com.tu.varna.chat.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.Closeable;
import java.io.IOException;

public class JpaChatUnityRepositoryFactory implements Closeable {

    private EntityManagerFactory entityManagerFactory;
    private final EntityManager manager;

    public JpaChatUnityRepositoryFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("chat-jpa-hibernate");
        manager = entityManagerFactory.createEntityManager();
    }

    public JpaChatUnityRepositoryFactory(EntityManager manager) {
        this.manager = manager;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public EntityManager getManager() {
        return manager;
    }

    public UserRepository getUserRepository() {
        return new UserRepository(manager);
    }

    @Override
    public void close() throws IOException {
        manager.close();
        if(entityManagerFactory!=null){
            entityManagerFactory.close();
        }
    }
}
