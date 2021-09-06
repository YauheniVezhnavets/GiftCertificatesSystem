package com.epam.esm.mappers;

import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {

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
    public GiftCertificate mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        List<Tag> listOfTags = new ArrayList<>();
        GiftCertificate giftCertificate = new GiftCertificate();
        while (resultSet.next()) {
            if (giftCertificate == null) {
                giftCertificate.setCertificateId(resultSet.getLong(ID));
                giftCertificate.setName(resultSet.getString(NAME));
                giftCertificate.setDescription(resultSet.getString(DESCRIPTION));
                giftCertificate.setPrice(resultSet.getBigDecimal(PRICE));
                giftCertificate.setDuration(resultSet.getInt(DURATION));
                giftCertificate.setCreateDate(ZonedDateTime.parse(resultSet.getString(CREATE_DATE)));
                giftCertificate.setLastUpdateDate(ZonedDateTime.parse(resultSet.getString(LAST_UPDATE_DATE)));
            }
            Long tagId = resultSet.getLong(TAG_ID);
            String tagName = resultSet.getString(TAG_NAME);
            listOfTags.add(new Tag(tagId, tagName));
        }
        giftCertificate.setTags(listOfTags);
        return giftCertificate;
    }
}
