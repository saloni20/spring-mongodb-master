package com.spring.mongo.api.resource.service;

import com.spring.mongo.api.resource.dto.AdminRegisterDto;
import com.spring.mongo.api.resource.dto.LoginRequestDto;
import com.spring.mongo.api.resource.response.Response;
import org.springframework.stereotype.Service;

@Service
public interface AdminLoginService {
    Response login(LoginRequestDto loginRequestDto);

    Response registerUser(AdminRegisterDto adminRegisterDto);

    void authenticate(String email, String password);
}