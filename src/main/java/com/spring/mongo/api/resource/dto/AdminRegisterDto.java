package com.spring.mongo.api.resource.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminRegisterDto {
    private Integer organizationId;
    private Integer userId;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}