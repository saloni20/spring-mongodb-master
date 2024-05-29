package com.spring.mongo.api.resource.service;

import com.spring.mongo.api.entity.UserMaster;
import com.spring.mongo.api.resource.dto.AdminRegisterDto;
import com.spring.mongo.api.resource.dto.LoginRequestDto;
import com.spring.mongo.api.resource.dto.LoginResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface AdminLoginService {
    LoginResponseDto adminLogin(LoginRequestDto loginRequestDto);

    UserMaster saveAdmin(AdminRegisterDto adminRegisterDto) throws Exception;

    void authenticate(String email, String password);
}