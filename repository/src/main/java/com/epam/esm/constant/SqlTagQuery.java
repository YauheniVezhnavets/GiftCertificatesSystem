package com.epam.esm.constant;

public class SqlTagQuery {

    private SqlTagQuery() { }

    public static final String CREATE_TAG = "INSERT INTO tag (name) values (?)";

    public static final String DELETE_TAG = "DELETE FROM tag WHERE id = ?";

    public static final String FIND_ALL_TAGS = "SELECT * FROM tag";

    public static final String FIND_TAG_BY_ID = "SELECT * FROM tag WHERE id = ?";

    public static final String FIND_TAG_BY_NAME = "SELECT * FROM tag WHERE name = ?";

    public static final String FIND_TAG_ID_BY_NAME = "SELECT id FROM tag WHERE name = ?";


}
