package com.spring.mongo.api.resource.controller;

import com.spring.mongo.api.entity.UserMaster;
import com.spring.mongo.api.resource.dto.AdminRegisterDto;
import com.spring.mongo.api.resource.dto.LoginRequestDto;
import com.spring.mongo.api.resource.response.Response;
import com.spring.mongo.api.resource.service.AdminLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@CrossOrigin()
@Log4j2
public class AdminLoginController {

    private final AdminLoginService adminLoginService;

    @PostMapping("/register")
    public Response registerUser(@RequestBody AdminRegisterDto adminRegisterDto) {
        return adminLoginService.registerUser(adminRegisterDto);
    }

    @PostMapping("/login")
    public Response login(@RequestBody LoginRequestDto loginRequestDto) {
        return adminLoginService.login(loginRequestDto);
    }

    @GetMapping("/getAdmin")
    public String getadmin() {
        UserMaster userMaster = (UserMaster) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "Hi " + userMaster.getUsername();
    }
}