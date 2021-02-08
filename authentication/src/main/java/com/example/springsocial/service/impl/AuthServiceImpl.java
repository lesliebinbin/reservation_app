package com.example.springsocial.service.impl;

import com.example.springsocial.domain.AuthenticationForUser;
import com.example.springsocial.repositories.AuthDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthServiceImpl implements UserDetailsService {
    private AuthDao authDao;

    public AuthServiceImpl(AuthDao authDao) {
        this.authDao = authDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        AuthenticationForUser auth = authDao.findByUsername(username);
        if (Objects.isNull(auth))
            throw new UsernameNotFoundException("User '" + username + "' not " +
                    "found");
        return auth;
    }
}
