package com.epam.esm.dao;

import com.epam.esm.constant.SqlTagQuery;
import com.epam.esm.entities.Tag;
import com.epam.esm.mappers.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@Repository
public class TagDao implements EntityDao<Tag> {

    private final JdbcTemplate jdbcTemplate;
    private final TagMapper tagMapper;

    @Autowired
    public TagDao(JdbcTemplate jdbcTemplate, TagMapper tagMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagMapper = tagMapper;
    }


    @Override
    public long create(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SqlTagQuery.CREATE_TAG,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tag.getName());
            return statement;
        }, keyHolder);
        Long newId;
        if (keyHolder.getKeys().size() > 1) {
            newId = (Long)keyHolder.getKeys().get("id");
        } else {
            newId= keyHolder.getKey().longValue();
        }
        return newId;
    }


    @Override
    public Optional<Tag> findById(long id) {
        return jdbcTemplate.query(SqlTagQuery.FIND_TAG_BY_ID, tagMapper, new Object[]{id}).stream().findAny();
    }


    public Optional<Tag> findByName(String name) {
        return jdbcTemplate.query(SqlTagQuery.FIND_TAG_BY_NAME,tagMapper,new Object[]{name}).stream().findAny();
    }

    public List<Long> getAllTagsIdConnectedWithGiftCertificate(Set<Tag> tags) {
        List<Long> listOfId = new ArrayList<>();
        tags.stream().forEach(tag -> listOfId.add(jdbcTemplate.queryForObject(SqlTagQuery.FIND_TAG_ID_BY_NAME,
                Long.class, tag.getName())));
        return listOfId;
    }


    public List <Tag> findAll() {
        return jdbcTemplate.query(SqlTagQuery.FIND_ALL_TAGS, tagMapper);
    }


    public List <Tag> getAllTagsConnectedWithCertificateId (long giftCertificateId){
        return jdbcTemplate.query(SqlTagQuery.SELECT_ALL_TAGS_BY_CERTIFICATE_ID, tagMapper,
                giftCertificateId);
    }


    @Override
    public void delete(long id) {
        jdbcTemplate.update(SqlTagQuery.DELETE_TAG,id);
    }
}
