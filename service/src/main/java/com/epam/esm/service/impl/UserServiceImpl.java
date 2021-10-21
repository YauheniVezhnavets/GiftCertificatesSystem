package com.epam.esm.service.impl;


import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceDuplicateException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.TagDtoMapper;
import com.epam.esm.mapper.UserDtoMapper;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("userService")
@AllArgsConstructor
public class UserServiceImpl implements UserService<User> {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    @Override
    public Page<UserDto> findUsers(int currentPage, int pageSize) {
        Pageable pageAndResultPerPage = PageRequest.of(currentPage, pageSize);
        Page<User> users = userRepository.findAll(pageAndResultPerPage);
        List <UserDto> usersDto = users.stream().map(userDtoMapper::mapToDto).
                collect(Collectors.toList());
        return new PageImpl<>(usersDto);
    }


    @Override
    public UserDto findUser(long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException(userId, "User with this id not found"));
        return userDtoMapper.mapToDto(user);
    }

    @Override
    public User findUserByEmail(String email) throws ResourceNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException(email));
    }


    @Override
    @Transactional
    public void registerUser(UserDto userDto) {
        User user = userDtoMapper.map(userDto);
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isEmpty()){
            userRepository.save(user);
        }
        else {
            throw new ResourceDuplicateException();
        }
    }
}
