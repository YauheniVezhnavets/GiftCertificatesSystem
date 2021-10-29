package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;



/**
 * This interface represents Service implementation that connected controller with Data Access Object.
 *
 * @author Yauheni Vezhnavets
 * @param <T> has to implement {@link User} interface
 * @see User
 */


public interface UserService<T extends User> {


    /**
     * This method return all existing users.
     *
     * @return list of{@link UserDto}
     */
    Page <UserDto> findUsers(int currentPage, int pageSize);


    /**
     * This method return user by his id.
     *
     * @return {@link UserDto}
     * @throws  {@link ResourceNotFoundException} in case if user not found with searched id.
     */
    UserDto findUser(long userId) throws ResourceNotFoundException;


    /**
     * This method return user by his email.
     *
     * @return {@link User}
     * @throws  {@link ResourceNotFoundException} in case if user not found with searched id.
     */

    User findUserByEmail(String email) throws ResourceNotFoundException;



    /**
     * This method register new User.
     *
     */
    void registerUser(UserDto userDto);
}
