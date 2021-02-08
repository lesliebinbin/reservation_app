package com.example.springsocial.domain;

import org.springframework.data.annotation.Id;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import java.util.Date;

public class CustomToken extends PersistentRememberMeToken {
    @Id
    private String id;

    public CustomToken(String id, String username, String series, String tokenValue, Date date) {
        super(username, series, tokenValue, date);
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
