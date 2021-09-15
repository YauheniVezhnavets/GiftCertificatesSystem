package com.epam.esm.dao;


import com.epam.esm.constant.SqlGiftCertificateQuery;
import com.epam.esm.constructor.QueryConstructor;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.mappers.GiftCertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDao implements EntityDao<GiftCertificate> {

    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateMapper giftCertificateMapper;
    private final QueryConstructor queryConstructor;

    @Autowired
    public GiftCertificateDao(JdbcTemplate jdbcTemplate, GiftCertificateMapper giftCertificateMapper,
                              QueryConstructor queryConstructor) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateMapper = giftCertificateMapper;
        this.queryConstructor = queryConstructor;
    }

    @Override
    public long create(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                (connection) -> {
                    PreparedStatement statement =
                            connection.prepareStatement(SqlGiftCertificateQuery.CREATE_CERTIFICATE,
                                    Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, giftCertificate.getName());
                    statement.setString(2, giftCertificate.getDescription());
                    statement.setBigDecimal(3, giftCertificate.getPrice());
                    statement.setObject(4, giftCertificate.getDuration());
                    statement.setObject(5, giftCertificate.getCreateDate());
                    statement.setObject(6, giftCertificate.getLastUpdateDate());
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
    public Optional<GiftCertificate> findById(long id) {
        return jdbcTemplate.query(SqlGiftCertificateQuery.FIND_CERTIFICATES_BY_ID, giftCertificateMapper,
                        new Object[]{id}).stream().findAny();

    }

    public List<GiftCertificate> getGiftCertificates(String tagName, String giftCertificateName,
                                                     String description, String sortByName, String sortByDate) {
        String queryWithCondition = queryConstructor.constructQuery(tagName, giftCertificateName, description,
                sortByName, sortByDate);
        return jdbcTemplate.query(queryWithCondition, giftCertificateMapper);
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update(SqlGiftCertificateQuery.DELETE_CERTIFICATE, id);
    }

    public void update(long id, GiftCertificate giftCertificate) {
        jdbcTemplate.update(SqlGiftCertificateQuery.UPDATE_CERTIFICATE, giftCertificate.getName(),
                giftCertificate.getDescription(), giftCertificate.getPrice(),giftCertificate.getDuration(),
                giftCertificate.getLastUpdateDate(), id);
    }
}
