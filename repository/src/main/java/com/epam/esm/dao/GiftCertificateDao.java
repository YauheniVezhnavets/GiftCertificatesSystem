package com.epam.esm.dao;


import com.epam.esm.constructor.QueryConstructor;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.utils.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
public class GiftCertificateDao implements EntityDao<GiftCertificate> {

    private final QueryConstructor queryConstructor;


    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private Paginator paginator;

    @Autowired
    public GiftCertificateDao(QueryConstructor queryConstructor) {
        this.queryConstructor = queryConstructor;
    }

    @Override
    public long create(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
        return giftCertificate.getCertificateId();
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    public List<GiftCertificate> findGiftCertificates(Map<String, String> mapWithParameters,
                                                      Set<String> tagsName, int currentPage) {
        String queryWithCondition = queryConstructor.constructQuery(mapWithParameters, tagsName, currentPage);
        return entityManager.createNativeQuery(queryWithCondition, GiftCertificate.class).getResultList();
    }

    @Override
    public void delete(GiftCertificate giftCertificate) {
        entityManager.remove(giftCertificate);
    }

    public void update(GiftCertificate giftCertificate) {
        entityManager.merge(giftCertificate);
    }
}
