package com.example.springsocial.repositories;

import com.example.springsocial.domain.CustomToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface CustomTokenDao extends MongoRepository<CustomToken, String> {
    CustomToken findBySeries(String series);

    CustomToken findByUsername(String username);
}
