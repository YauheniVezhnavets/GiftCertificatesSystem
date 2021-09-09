package com.epam.esm.constant;

public class SqlGiftCertificateTagQuery {

    private SqlGiftCertificateTagQuery() {
    }

    public static final String CONNECT_TAG_TO_CERTIFICATE = "INSERT INTO gift_certificate_tag (gift_certificate_id, " +
            "tag_id) values (?, ?)";

    public static final String DISCONNECT_TAG_TO_CERTIFICATE = "DELETE FROM gift_certificate_tag where " +
            "gift_certificate_id =? and tag_id = ?";

    public static final String SELECT_ALL_TAG_ID_BY_CERTIFICATE_ID = "SELECT tag_id FROM gift_certificate_tag WHERE " +
            "gift_certificate_id = ?";


    public static final String MAKE_TIDED_TAGS_AND_CERTIFICATIONS = "INSERT INTO gift_certificate_tag " +
            "(gift_certificate_id, tag_id) VALUES (?, (SELECT id from tag WHERE name = ?))";

    public static final String MAKE_UNTIED_CERTIFICATE = "DELETE FROM gift_certificate_tag WHERE " +
            "gift_certificate_id = ?";

    public static final String MAKE_UNTIED_TAG = "DELETE FROM gift_certificate_tag WHERE tag_id = ?";

}
