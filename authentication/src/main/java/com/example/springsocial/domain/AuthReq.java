package com.example.springsocial.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AuthReq {
    private String username;
    private String password;
}
