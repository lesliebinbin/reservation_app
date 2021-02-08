package com.example.springsocial;

import com.example.springsocial.domain.AuthenticationForUser;
import com.example.springsocial.repositories.AuthDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthTest {
    @Autowired
    private AuthDao dao;

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate template;

    @Test
    public void testCreateAdmin() {
        AuthenticationForUser authenticationForUser = new AuthenticationForUser("maple", "woainanren1");
        authenticationForUser.setAuthorities(Arrays.asList("ADMIN"));
        dao.save(authenticationForUser);
    }

    @Test
    public void testGetAuthorityFromUser() {
        AuthenticationForUser leslie = dao.findByUsername("leslie");
        System.out.println(leslie.getAuthorities());
    }

    @Test
    public void testGetMysqlDb() {
    }
}
