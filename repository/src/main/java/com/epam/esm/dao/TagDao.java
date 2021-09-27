package com.epam.esm.dao;


import com.epam.esm.entities.Tag;
import com.epam.esm.utils.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;


@Repository
public class TagDao implements EntityDao<Tag> {


    private static final String FIND_ALL = "SELECT t FROM Tag t ";
    private static final String FIND_TAG_BY_TAG_NAME = "SELECT t FROM Tag t WHERE t.name = :name";
    private static final String TAG_NAME = "name";

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    Paginator paginator;


    @Override
    public long create(Tag tag) {
        entityManager.persist(tag);
        entityManager.flush();
        return tag.getTagId();
    }


    @Override
    public Optional<Tag> findById(long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    public Optional<Tag> findByName(String name) {

        Optional<Tag> optionalTag;
        try {
            optionalTag = Optional.ofNullable(entityManager.createQuery(FIND_TAG_BY_TAG_NAME, Tag.class).
                    setParameter(TAG_NAME, name).getSingleResult());
        } catch (NoResultException e) {
            optionalTag = Optional.empty();
        }
        return optionalTag;


    }

    public List<Tag> findAll(int currentPage) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery <Tag> criteria = criteriaBuilder.createQuery(Tag.class);
        Root <Tag> root = criteria.from(Tag.class);
        CriteriaQuery<Tag> findAllQuery = criteria.select(root);
        TypedQuery<Tag> typedQuery = entityManager.createQuery(findAllQuery);
        paginator.paginateQuery(currentPage,typedQuery);


        return typedQuery.getResultList();
    }



    @Override
    public void delete(long id) {
        Tag tag = entityManager.find(Tag.class, id);
        if (tag != null) {
            entityManager.remove(tag);
        }
    }
}
