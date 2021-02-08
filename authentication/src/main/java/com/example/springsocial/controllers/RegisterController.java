package com.example.springsocial.controllers;

import com.example.springsocial.domain.AuthenticateResult;
import com.example.springsocial.domain.AuthenticationForUser;
import com.example.springsocial.repositories.AuthDao;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/register")
public class RegisterController {
    //    @Autowired
//    @Qualifier("authServiceImpl")
//    private UserDetailsService userDetailsService;
//    @Autowired
//    private AuthDao dao;
    private AuthDao dao;
    private UserDetailsService userDetailsService;
    private RestTemplate template;

    public RegisterController(AuthDao dao, @Qualifier("authServiceImpl") UserDetailsService userDetailsService, @LoadBalanced RestTemplate template) {
        this.dao = dao;
        this.userDetailsService = userDetailsService;
        this.template = template;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public AuthenticateResult createNewUser(@Valid @RequestParam AuthenticationForUser user) {
        if (!Objects.isNull(userDetailsService.loadUserByUsername(user.getUsername())))
            return new AuthenticateResult(407,
                    "Username exists",
                    "This username has already been exists, please choose " +
                            "another one",
                    null);
        user.setAuthorities(Arrays.asList("USER"));
        @Valid AuthenticationForUser savedResult = dao.save(user);
        if (Objects.isNull(savedResult.getId()))
            return new AuthenticateResult(405,
                    "unexpected error",
                    "Registration failed due to unknown error, please try later",
                    null);
        return new AuthenticateResult(200,
                "Registration success!",
                "Registration success",
                savedResult.getAuthorities().stream().map(
                        GrantedAuthority::getAuthority).collect(Collectors.toList()));

    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String registrationInfo() {
        return "Welcome, nice to meet you.";
    }

    @RequestMapping(value = "/mysql", method = RequestMethod.GET)
    public String getMySqlInfo() {
        return template.getForEntity("http://mysql-db/api", String.class).getBody();
    }
}
