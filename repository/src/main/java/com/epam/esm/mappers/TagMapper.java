package com.epam.esm.mappers;

import com.epam.esm.entities.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class TagMapper implements RowMapper <Tag> {

    private static final String ID = "id";
    private static final String NAME = "name";

    @Override
    public Tag mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        long id = resultSet.getLong(ID);
        String name = resultSet.getString(NAME);

        return new Tag(id,name);
    }
}
