package com.epam.esm.dao;

import com.epam.esm.entities.Order;
import com.epam.esm.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@Repository
public class UserDao implements EntityDao<User>{

    private static final String FIND_ALL = "SELECT u FROM User u ";

    @PersistenceContext
    protected EntityManager entityManager;


    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(entityManager.find(User.class,id));
    }


    public List <User> findAll() {
        return entityManager.createQuery(FIND_ALL, User.class).getResultList();
    }


    @Override
    public long create(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }
}
