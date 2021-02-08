package com.example.springsocial.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class AuthenticationForUser implements UserDetails {
  private static final long serialVersionUID = 1L;
  @NotNull
  @NotEmpty
  private final String username;
  @NotNull
  @NotEmpty
  private final String password;
  @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
  private List<String> authorities;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private Long userId;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (Objects.isNull(authorities)) {
      return Arrays.asList(new SimpleGrantedAuthority("USER"));
    } else {
      List<SimpleGrantedAuthority> multipleAuth = authorities.stream().map(SimpleGrantedAuthority::new)
          .collect(Collectors.toList());
      return multipleAuth;
    }
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
