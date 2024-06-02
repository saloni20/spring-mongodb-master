package com.spring.mongo.api.resource.controller;

import com.spring.mongo.api.entity.UserMaster;
import com.spring.mongo.api.resource.dto.AdminRegisterDto;
import com.spring.mongo.api.resource.dto.LoginRequestDto;
import com.spring.mongo.api.resource.response.Response;
import com.spring.mongo.api.resource.service.AdminLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@CrossOrigin()
@Log4j2
public class AdminLoginController {

    private final AdminLoginService adminLoginService;



    @PostMapping("/register")
    public Response registerUser(@Valid @RequestBody AdminRegisterDto adminRegisterDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return new Response(errors.toString(), HttpStatus.BAD_REQUEST);
        }

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