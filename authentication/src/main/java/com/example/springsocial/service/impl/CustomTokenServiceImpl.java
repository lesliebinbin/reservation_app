package com.example.springsocial.service.impl;

import com.example.springsocial.domain.CustomToken;
import com.example.springsocial.repositories.CustomTokenDao;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

//@Component
public class CustomTokenServiceImpl implements PersistentTokenRepository {
    private CustomTokenDao dao;

    public CustomTokenServiceImpl(CustomTokenDao dao) {
        this.dao = dao;
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        dao.save(new CustomToken(null, token.getUsername(), token.getSeries(), token.getTokenValue(), token.getDate()));
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        CustomToken token = dao.findBySeries(series);
        dao.save(new CustomToken(token.getId(), token.getUsername(), series, tokenValue, lastUsed));
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return dao.findBySeries(seriesId);
    }

    @Override
    public void removeUserTokens(String username) {
        CustomToken token = dao.findByUsername(username);
        if (!Objects.isNull(token)) {
            dao.delete(token);
        }
    }
}
