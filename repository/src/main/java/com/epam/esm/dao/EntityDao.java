package com.epam.esm.dao;

import com.epam.esm.entities.Identifiable;


import java.util.Optional;

/**
 * This interface represents Data Access Object pattern as a part of MVC pattern and provides general
 * operations for access database.
 * Based on the data obtained from {@link org.springframework.jdbc.core.JdbcTemplate} objects are
 * mapped for further processing
 *
 * @author Yauheni Vezhnavets
 * @param <T> has to implement {@link Identifiable} interface
 * @see Identifiable
 */

public interface EntityDao <T extends Identifiable> {


    /**
     * Inserts a new record in the table if the id of the given entity does not exist int the table
     *
     * @param entity {@link Identifiable} object
     */
    long create(T entity);

    /**
     *  Returns an Optional object contains an {@link Identifiable} object found in the database.
     *  An empty object returned if nothing would be found.
     *
     * @param id {@link Identifiable}
     * @return Optional of {@link Identifiable} object
     */

    Optional<T> findById(long id);

    /**
     * Deletes a record in the database by id of an {@link Identifiable} object
     *
     * @param entity {@link Identifiable}
     */

    void delete(T entity);
}




