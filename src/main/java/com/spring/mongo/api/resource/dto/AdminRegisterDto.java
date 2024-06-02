package com.spring.mongo.api.resource.dto;

import com.spring.mongo.api.resource.service.ValidPassword;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminRegisterDto {
    private String firstname;
    private String lastname;
    private String email;
    @ValidPassword
    private String password;
}