package com.spring.mongo.api.resource.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String token;
    private String username;
    private Integer orgId;
    private Long userId;
}