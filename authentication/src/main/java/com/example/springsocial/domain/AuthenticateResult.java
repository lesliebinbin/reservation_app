package com.example.springsocial.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@JsonPropertyOrder({"status", "reason", "message"})
@AllArgsConstructor
@Getter
public class AuthenticateResult {
    private final int status;
    private final String reason;
    private final String message;
    private final List<String> authorities;
}
