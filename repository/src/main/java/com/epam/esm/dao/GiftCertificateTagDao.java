package com.epam.esm.dao;

import com.epam.esm.constant.SqlGiftCertificateTagQuery;
import com.epam.esm.entities.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GiftCertificateTagDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftCertificateTagDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addTagId(long giftCertificateId, long tagId) {
       jdbcTemplate.update(SqlGiftCertificateTagQuery.CONNECT_TAG_TO_CERTIFICATE,giftCertificateId, tagId);
    }

    public void deleteTagId(long giftCertificateId, long tagId) {
        jdbcTemplate.update(SqlGiftCertificateTagQuery.DISCONNECT_TAG_TO_CERTIFICATE,giftCertificateId,tagId);
    }

    public void createConnections(long giftCertificateId, List <Tag> tags) {
        for (Tag tag : tags) {
            jdbcTemplate.update(SqlGiftCertificateTagQuery.MAKE_TIDED_TAGS_AND_CERTIFICATIONS,
                    giftCertificateId, tag.getName());
        }
    }

    public List <Long> getAllTagIdConnectedWithCertificateId (long giftCertificateId){
        return jdbcTemplate.queryForList(SqlGiftCertificateTagQuery.SELECT_ALL_TAG_ID_BY_CERTIFICATE_ID,
                Long.class,giftCertificateId);
    }

    public void deleteConnectionBetweenGiftCertificateAndTagsByGiftCertificateId(long giftCertificateId) {
        jdbcTemplate.update(SqlGiftCertificateTagQuery.MAKE_UNTIED_CERTIFICATE,giftCertificateId);
    }

    public void deleteByTagId(long tagId) {
        jdbcTemplate.update(SqlGiftCertificateTagQuery.MAKE_UNTIED_TAG,tagId);
    }

}
