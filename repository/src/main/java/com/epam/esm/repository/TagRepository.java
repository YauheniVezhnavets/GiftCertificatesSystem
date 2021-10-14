package com.epam.esm.repository;


import com.epam.esm.entity.Tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface TagRepository extends JpaRepository<Tag, Long> {


    @Query(value = "select t FROM Tag t where t.name = :name")
    Optional<Tag> findByName(@Param("name") String name);


    @Query(value = "select t.id as id, t.name as name, t.is_active as is_active from user_order uo " +
            "inner join gift_certificate gc\n" +
            "on uo.certificate_id=gc.id inner join gift_certificate_tag gct on\n" +
            "gc.id=gct.gift_certificate_id inner join tag t on gct.tag_id=t.id \n" +
            "where uo.user_id=(:userId) group by t.id,t.name,t.is_active order by sum(cost) desc limit 1",
            nativeQuery = true)
    Optional<Tag> findMostUsedTagOfUserWithHighestCostOfAllOrders(@Param("userId") long userId);
}


