package com.epam.esm.constant;

public class SqlGiftCertificateQuery {

    private SqlGiftCertificateQuery() {
    }

    public static final String CREATE_CERTIFICATE = "INSERT INTO gift_certificate (name, description, price," +
            "duration, create_date, last_update_date) VALUES (?,?,?,?,?,?)";

    public static final String DELETE_CERTIFICATE = "DELETE FROM gift_certificate where id =?";

    public static final String FIND_ALL_CERTIFICATES = "SELECT gc.id, gc.name, gc.description, gc.price, gc.duration," +
            " gc.create_date, gc.last_update_date, gct.gift_certificate_id, gct.tag_id, t.name AS tag_name FROM" +
            " gift_certificate gc JOIN gift_certificate_tag gct ON gc.id = gct.gift_certificate_id JOIN tag t " +
            "ON gct.tag_id = t.id ";


    public static final String FIND_CERTIFICATES_BY_ID = FIND_ALL_CERTIFICATES + "WHERE gc.id = ?";

    public static final String UPDATE_CERTIFICATE = "UPDATE gift_certificate SET name = ?," +
            " description = ?, price = ?, duration = ?, last_update_date = ? WHERE id = ?;";


}
