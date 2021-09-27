package com.epam.esm.dao;

import com.epam.esm.entities.User;
import com.epam.esm.utils.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;


@Repository
public class UserDao implements EntityDao<User>{

    private static final String FIND_ALL = "SELECT u FROM User u ";

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    Paginator paginator;


    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(entityManager.find(User.class,id));
    }


    public List <User> findAll(int currentPage) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        CriteriaQuery<User> findAllQuery = criteria.select(root);
        TypedQuery<User> typedQuery = entityManager.createQuery(findAllQuery);
        paginator.paginateQuery(currentPage,typedQuery);

        return typedQuery.getResultList();
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
