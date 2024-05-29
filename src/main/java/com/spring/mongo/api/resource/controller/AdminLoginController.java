package com.spring.mongo.api.resource.controller;

import com.spring.mongo.api.entity.UserMaster;
import com.spring.mongo.api.resource.dto.AdminRegisterDto;
import com.spring.mongo.api.resource.dto.LoginRequestDto;
import com.spring.mongo.api.resource.dto.LoginResponseDto;
import com.spring.mongo.api.resource.service.AdminLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin()
@Log4j2
public class AdminLoginController {

    private final AdminLoginService adminLoginService;

    @PostMapping("/register")
    public ResponseEntity<UserMaster> registerUser(@RequestBody AdminRegisterDto adminRegisterDto) throws Exception {
        return new ResponseEntity<>(adminLoginService.saveAdmin(adminRegisterDto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public LoginResponseDto adminLogin(@RequestBody LoginRequestDto loginRequestDto) {
        return adminLoginService.adminLogin(loginRequestDto);
    }

    @GetMapping("/getAdmin")
    public String getadmin() {
        UserMaster userMaster = (UserMaster) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "Hi " + userMaster.getUsername();
    }
}