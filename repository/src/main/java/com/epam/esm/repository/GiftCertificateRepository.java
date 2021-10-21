package com.epam.esm.repository;


import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long>,
        QuerydslPredicateExecutor<GiftCertificate> {


  @Query ("select gc from GiftCertificate gc JOIN gc.tags t WHERE t.name IN (:tags) " +
            "group by gc.id having count(t.name) = (:count)")
  Page<GiftCertificate> findByTagsIn(@Param("tags") Set <String> tags,
                                     @Param("count") long sizeOfSet,
                                     Pageable pageable);

}
