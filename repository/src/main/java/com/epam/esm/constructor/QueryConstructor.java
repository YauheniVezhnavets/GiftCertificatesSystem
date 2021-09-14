package com.epam.esm.constructor;

import com.epam.esm.constant.SqlGiftCertificateQuery;
import org.springframework.stereotype.Component;

@Component
public class QueryConstructor {

    private static final String SELECT_CERTIFICATES_BY_TAG_NAME_FiRST_PART = "t.name IN ('";
    private static final String SELECT_CERTIFICATES_BY_TAG_NAME_SECOND_PART = "') ";
    private static final String SELECT_CERTIFICATES_WHERE_MATCH_NAME_FIRST_PART = "gc.name like '%";
    private static final String SELECT_CERTIFICATES_WHERE_MATCH_NAME_SECOND_PART = "%' ";
    private static final String WHERE = "WHERE ";
    private static final String AND = "AND ";
    private static final String ORDER_BY = "ORDER BY ";
    private static final String COMMA = ", ";
    private static final String SELECT_CERTIFICATES_WHERE_MATCH_DESCRIPTION_FIRST_PART = "gc.description IN ('";
    private static final String SELECT_CERTIFICATES_WHERE_MATCH_DESCRIPTION_SECOND_PART = "') ";
    private static final String SELECT_CERTIFICATES_BY_NAME = "gc.name ";
    private static final String SELECT_CERTIFICATES_BY_NAME_DESC = "gc.name DESC ";
    private static final String SELECT_CERTIFICATES_BY_DATE = "gc.last_update_date ";
    private static final String SELECT_CERTIFICATES_BY_DATE_DESC = "gc.last_update_date DESC ";


    public String constructQuery(String tagName, String giftCertificateName, String description, String sortByName,
                                 String sortByDate) {
        StringBuilder query = new StringBuilder(SqlGiftCertificateQuery.FIND_ALL_CERTIFICATES);


        if (isAnyParameterNotNull(tagName, giftCertificateName, description)) {
            query.append(WHERE);
        }

        if (tagName != null) {
            getAppendTag(tagName, query);
            if (giftCertificateName == null && description != null) {
                query.append(AND);
                getAppendDescription(description, query);
            }
        }

        if (giftCertificateName != null) {
            if (tagName != null) {
                query.append(AND);
            }
            getAppendName(giftCertificateName, query);

            if (description != null) {
                query.append(AND);
                getAppendDescription(description, query);
            }
        }

        if (tagName == null && giftCertificateName == null && description != null) {
            getAppendDescription(description, query);
        }

        sortQuery(sortByName, sortByDate, query);

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

    private void getAppendName(String giftCertificateName, StringBuilder query) {
        query.append(SELECT_CERTIFICATES_WHERE_MATCH_NAME_FIRST_PART).append(giftCertificateName)
                .append(SELECT_CERTIFICATES_WHERE_MATCH_NAME_SECOND_PART);
    }

    private void getAppendTag(String tagName, StringBuilder query) {
        query.append(SELECT_CERTIFICATES_BY_TAG_NAME_FiRST_PART).append(tagName)
                .append(SELECT_CERTIFICATES_BY_TAG_NAME_SECOND_PART);
    }

    private StringBuilder getAppendDescription(String description, StringBuilder query) {
        return query.append(SELECT_CERTIFICATES_WHERE_MATCH_DESCRIPTION_FIRST_PART).append(description)
                .append(SELECT_CERTIFICATES_WHERE_MATCH_DESCRIPTION_SECOND_PART);
    }

    private boolean isAnyParameterNotNull(String tagName, String giftCertificateName, String description) {
        return tagName != null || giftCertificateName != null || description != null;
    }
}
