package com.epam.esm.service.impl;

import com.epam.esm.entity.User;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service("UserDetailServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User with " + email + "not fond"));

       return userMapper.mapToSecurityUser(user);
    }
}
