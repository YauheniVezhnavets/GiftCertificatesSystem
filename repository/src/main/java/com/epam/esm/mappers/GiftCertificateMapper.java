package com.epam.esm.mappers;

import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class GiftCertificateMapper implements ResultSetExtractor <List<GiftCertificate>> {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PRICE = "price";
    private static final String DURATION = "duration";
    private static final String CREATE_DATE = "create_date";
    private static final String LAST_UPDATE_DATE = "last_update_date";
    private static final String TAG_NAME = "tag_name";
    private static final String TAG_ID = "tag_id";
    
    @Override
    public List<GiftCertificate> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        if (resultSet.next()) {
            while (!resultSet.isAfterLast()) {
                long giftCertificateId = resultSet.getLong(ID);
                String giftCertificateName = resultSet.getString(NAME);
                String description = resultSet.getString(DESCRIPTION);
                BigDecimal price = resultSet.getBigDecimal(PRICE);
                int duration = resultSet.getInt(DURATION);
                LocalDateTime createDate =  resultSet.getObject(CREATE_DATE, LocalDateTime.class);
                LocalDateTime lastUpdateDate = resultSet.getObject(LAST_UPDATE_DATE, LocalDateTime.class);

                Set<Tag> tags = extractTags(resultSet, giftCertificateId);
                GiftCertificate giftCertificate = new GiftCertificate(giftCertificateId, giftCertificateName,
                        description, price, duration, createDate, lastUpdateDate);


                giftCertificate.setTags(tags);
                giftCertificates.add(giftCertificate);
            }
        }
        return giftCertificates;
    }

    private Set <Tag> extractTags(ResultSet resultSet, long giftCertificateId) throws SQLException {
        Set<Tag> tags = new HashSet<>();
        do {
            long tagId = resultSet.getLong(TAG_ID);
            String tagName = resultSet.getString(TAG_NAME);
            if (tagId != 0 && tagName != null) {
                tags.add(new Tag(tagId, tagName));
            }
        } while (resultSet.next() && resultSet.getLong(ID) == giftCertificateId);
        return tags;
    }
}
