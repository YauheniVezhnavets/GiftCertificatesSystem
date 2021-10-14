package com.epam.esm.service.impl;


import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("userService")
@AllArgsConstructor
public class UserServiceImpl implements UserService<User> {


    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    @Override
    public List<User> findUsers(int currentPage) {
        List<User> users = userRepository.findAll();
        return users;
    }


    @Override
    public User findUser(long userId) throws ResourceNotFoundException {
        return userRepository.findById(userId).get();
    }

    @Override
    public Tag findMostUsedTagOfUserWithHighestCostOfAllOrders(long userId) throws ResourceNotFoundException {

        if (userRepository.findById(userId).isEmpty()) {
            throw new ResourceNotFoundException(userId);
        }

        Optional<Tag> optionalTag = tagRepository.findMostUsedTagOfUserWithHighestCostOfAllOrders(userId);
        if (optionalTag.isEmpty()) {
            throw new ResourceNotFoundException(userId);
        }
        return optionalTag.get();
    }
}
