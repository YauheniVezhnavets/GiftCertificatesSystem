package com.epam.esm.dao;

import com.epam.esm.constant.SqlTagQuery;
import com.epam.esm.entities.Tag;
import com.epam.esm.mappers.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return keyHolder.getKey().longValue();
    }


    @Override
    public Optional<Tag> findById(long id) {
        return jdbcTemplate.query(SqlTagQuery.FIND_TAG_BY_ID, tagMapper, new Object[]{id}).stream().findAny();
    }


    public Optional<Tag> findByName(String name) {
        return jdbcTemplate.query(SqlTagQuery.FIND_TAG_BY_NAME,tagMapper,new Object[]{name}).stream().findAny();
    }

    public List<Long> getAllTagsIdConnectedWithGiftCertificate(List <Tag> tags) {
        List<Long> listOfId = new ArrayList<>();
        tags.stream().forEach(tag -> listOfId.add(jdbcTemplate.queryForObject(SqlTagQuery.FIND_TAG_ID_BY_NAME,
                Long.class, tag.getName())));
        return listOfId;
    }


    public List<Tag> findAll() {
        return jdbcTemplate.query(SqlTagQuery.FIND_ALL_TAGS, tagMapper);
    }


    @Override
    public void delete(long id) {
        jdbcTemplate.update(SqlTagQuery.DELETE_TAG,id);
    }
}
