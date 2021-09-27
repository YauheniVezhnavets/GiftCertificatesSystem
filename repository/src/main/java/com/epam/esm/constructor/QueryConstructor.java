package com.epam.esm.constructor;


import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Component
public class QueryConstructor {

    private static final String SELECT_CERTIFICATES_BY_TAG_NAME_FiRST_PART = "gift_certificate.id IN \n" +
            "(SELECT gc.id FROM gift_certificate gc JOIN gift_certificate_tag gct ON \n" +
            " gct.gift_certificate_id = gc.id  JOIN tag t ON gct.tag_id = t.id \n" +
            " WHERE t.name = '";
    private static final String SELECT_CERTIFICATES_BY_TAG_NAME_SECOND_PART = "') ";
    private static final String SELECT_CERTIFICATES_WHERE_MATCH_NAME_FIRST_PART = "gift_certificate.name Like '%";
    private static final String SELECT_CERTIFICATES_WHERE_MATCH_NAME_SECOND_PART = "%' ";
    private static final String WHERE = "WHERE ";
    private static final String AND = "AND ";
    private static final String ORDER_BY = "ORDER BY ";
    private static final String COMMA = ", ";
    private static final String SELECT_CERTIFICATES_WHERE_MATCH_DESCRIPTION_FIRST_PART = "gift_certificate.description IN ('";
    private static final String SELECT_CERTIFICATES_WHERE_MATCH_DESCRIPTION_SECOND_PART = "') ";
    private static final String SELECT_CERTIFICATES_BY_NAME = "gift_certificate.name ";
    private static final String SELECT_CERTIFICATES_BY_NAME_DESC = "gift_certificate.name DESC ";
    private static final String SELECT_CERTIFICATES_BY_DATE = "gift_certificate.last_update_date ";
    private static final String SELECT_CERTIFICATES_BY_DATE_DESC = "gift_certificate.last_update_date DESC ";
    private static final String SELECT_ALL_CERTIFICATES = "Select * from gift_certificate ";
    private static final String GROUP_BY = " GROUP BY id HAVING ";
    private static final String LIMIT = " LIMIT ";
    private static final String OFFSET = " OFFSET ";
    private static final int PAGE_SIZE = 5;



    public String constructQuery(Map<String,String> mapWithParameters, Set<String> tagsName, int currentPage) {
        StringBuilder query = new StringBuilder(SELECT_ALL_CERTIFICATES);

        if (mapWithParameters.get("giftCertificateName")!=null || mapWithParameters.get("description")!=null) {
            query.append(WHERE);

            if (mapWithParameters.get("giftCertificateName")!=null) {
                getAppendName(mapWithParameters.get("giftCertificateName"), query);
                if (mapWithParameters.get("description")!=null){
                    query.append(AND);
                    getAppendDescription(mapWithParameters.get("description"), query);
                }
            } else {
                getAppendDescription(mapWithParameters.get("description"), query);
            }
        }

            if (tagsName != null) {
                getAppendTags(tagsName, query);
            }

            sortQuery(mapWithParameters.get("sortByName"), mapWithParameters.get("sortByDate"), query);


            query.append(OFFSET).append(PAGE_SIZE * (currentPage - 1)).append(LIMIT).append(PAGE_SIZE);


            return query.toString();
        }


    private void sortQuery(String sortByName, String sortByDate, StringBuilder query) {
        if (sortByName != null || sortByDate != null) {
            query.append(ORDER_BY);
            if (sortByName != null) {
                if ("ASC".equals(sortByName)) {
                    query.append(SELECT_CERTIFICATES_BY_NAME);
                } else if ("DESC".equals(sortByName)) {
                    query.append(SELECT_CERTIFICATES_BY_NAME_DESC);
                }
            }
            if (sortByName != null && sortByDate != null) {
                query.append(COMMA);
            }
            if (sortByDate != null) {
                if ("ASC".equals(sortByDate)) {
                    query.append(SELECT_CERTIFICATES_BY_DATE);
                } else if ("DESC".equals(sortByDate)) {
                    query.append(SELECT_CERTIFICATES_BY_DATE_DESC);
                }
            }
        }
    }

    private StringBuilder getAppendName(String giftCertificateName, StringBuilder query) {
        query.append(SELECT_CERTIFICATES_WHERE_MATCH_NAME_FIRST_PART).append(giftCertificateName)
                .append(SELECT_CERTIFICATES_WHERE_MATCH_NAME_SECOND_PART);
        return query;
    }


    private StringBuilder getAppendDescription(String description, StringBuilder query) {
        return query.append(SELECT_CERTIFICATES_WHERE_MATCH_DESCRIPTION_FIRST_PART).append(description)
                .append(SELECT_CERTIFICATES_WHERE_MATCH_DESCRIPTION_SECOND_PART);
    }

    private void getAppendTags(Set<String> tagsName, StringBuilder query) {
        query.append(GROUP_BY);
        Iterator<String> iterator = tagsName.iterator();
        while (iterator.hasNext()) {
            String tag = iterator.next();
            query.append(SELECT_CERTIFICATES_BY_TAG_NAME_FiRST_PART).append(tag)
                    .append(SELECT_CERTIFICATES_BY_TAG_NAME_SECOND_PART);
            if (iterator.hasNext()) {
                query.append("AND ");
            }
        }
    }
}

