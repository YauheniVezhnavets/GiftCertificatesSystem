package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entities.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.GiftCertificateDtoMapper;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService <User> {

    private final UserDao userDao;


    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public List<User> findUsers(int currentPage) {
        return userDao.findAll(currentPage);
    }


    @Override
    public User findUser(long userId) throws ResourceNotFoundException {
       return userDao.findById(userId).orElseThrow((() -> new ResourceNotFoundException(userId)));
    }
}
