package com.example.springsocial.config;

import com.example.springsocial.domain.AuthenticateResult;
import com.example.springsocial.filters.CustomAuthenticaionFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.session.ChangeSessionIdAuthenticationStrategy;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity(debug = true)
@Profile(value = {"dev-local", "prod"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Qualifier("authServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
//    @Qualifier("customTokenServiceImpl")
//    private PersistentTokenRepository persistentTokenRepository;

    @Bean
    public PasswordEncoder encoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .addFilterAfter(customAuthFilter(), RememberMeAuthenticationFilter.class)
//                .addFilterAfter(customAuthFilter(), RememberMeAuthenticationFilter.class)
                .csrf()
                .disable()
                .sessionManagement()
                .disable()
                .formLogin()
                .disable()
                .rememberMe()
                .alwaysRemember(true)
                .authenticationSuccessHandler(successHandler())
                .tokenRepository(getTokenRepo())
                .and()
                .logout()
                .clearAuthentication(true)
                .and()
                .authorizeRequests()
                .antMatchers("/register/**")
                .permitAll()
                .antMatchers("/login/**")
                .permitAll()
                .antMatchers("/actuator/**")
                .permitAll()
                .antMatchers("/**")
                .authenticated();
    }

    @Bean
    public PersistentTokenRepository getTokenRepo() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setJdbcTemplate(jdbcTemplate);
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Bean
    public UsernamePasswordAuthenticationFilter customAuthFilter() throws Exception {
        UsernamePasswordAuthenticationFilter authenticationFilter = new CustomAuthenticaionFilter(mapper());
        authenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
        authenticationFilter.setUsernameParameter("username");
        authenticationFilter.setPasswordParameter("password");
        authenticationFilter.setAuthenticationManager(authenticationManagerBean());
        authenticationFilter.setAuthenticationSuccessHandler(successHandler());
        authenticationFilter.setAuthenticationFailureHandler((request, response, authentication) -> {
            response.setStatus(401);
            String responseStr =
                    mapper().writeValueAsString(new
                            AuthenticateResult(
                            401,
                            "auth failed",
                            "login failed", null));
            response.getWriter().write(responseStr);
        });
//        authenticationFilter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy());
        return authenticationFilter;
    }

    @Bean
    public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new CompositeSessionAuthenticationStrategy(Arrays.asList(
                new ChangeSessionIdAuthenticationStrategy()
        ));
    }

    public AuthenticationSuccessHandler successHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                response.setStatus(200);
                List<String> authorities = authentication
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
                String responseStr =
                        mapper().writeValueAsString(new
                                AuthenticateResult(
                                200,
                                "ok",
                                "login success", authorities));
                response.getWriter().write(responseStr);
            }
        };
    }


}
