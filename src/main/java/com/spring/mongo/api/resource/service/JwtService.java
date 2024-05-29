package com.spring.mongo.api.resource.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateJwtToken(UserDetails userDetails);

    String extractUserName(String token);

    boolean isTokenValidate(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);
}