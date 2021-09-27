package com.epam.esm.service;

import com.epam.esm.entities.User;
import com.epam.esm.exception.ResourceNotFoundException;

import java.util.List;


/**
 * This interface represents Service implementation that connected controller with Data Access Object.
 *
 * @author Yauheni Vezhnavets
 * @param <T> has to implement {@link User} interface
 * @see User
 */


public interface UserService <T extends User> {


    /**
     * This method return all existing users.
     *
     * @return list of{@link User}
     */
    List<User> findUsers();


    /**
     * This method return user by his id.
     *
     * @return {@link User}
     * @throws  {@link ResourceNotFoundException} in case if user not found with searched id.
     */
    User findUser(long userId) throws ResourceNotFoundException;

}
