package com.example.springsocial.repositories;

import com.example.springsocial.domain.AuthenticationForUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthDao extends CrudRepository<AuthenticationForUser, Long> {
    AuthenticationForUser findByUsername(String username);
}
