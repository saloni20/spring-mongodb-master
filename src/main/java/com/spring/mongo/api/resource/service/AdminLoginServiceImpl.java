package com.spring.mongo.api.resource.service;

import com.spring.mongo.api.entity.RoleMaster;
import com.spring.mongo.api.entity.UserMaster;
import com.spring.mongo.api.entity.UserMasterPK;
import com.spring.mongo.api.repository.UserMasterRepository;
import com.spring.mongo.api.resource.dto.AdminRegisterDto;
import com.spring.mongo.api.resource.dto.LoginRequestDto;
import com.spring.mongo.api.resource.dto.LoginResponseDto;
import com.spring.mongo.api.resource.serviceImpl.JwtHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class AdminLoginServiceImpl implements AdminLoginService {

    private final UserMasterRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtHelper jwtService;
    private final CustomUserDetailService customUserDetailService;
    private final AuthenticationManager authenticationManager;
    private final UserMasterRepository userMasterRepository;

    @Override
    public LoginResponseDto adminLogin(LoginRequestDto loginRequestDto) {
        this.authenticate(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        UserMaster user = customUserDetailService.loadUserByUsername(loginRequestDto.getEmail());
        String jwt = jwtService.genrateJwtToken(user);
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setUsername(user.getUsername());
        loginResponseDto.setToken(jwt);
        log.info("Response : {}", loginResponseDto);
        return loginResponseDto;
    }

    @Override
    public UserMaster saveAdmin(AdminRegisterDto adminRegisterDto) throws Exception {
        UserMaster user = new UserMaster();
        user.setEmail(adminRegisterDto.getEmail());
        user.setFirstname(adminRegisterDto.getFirstname());
        user.setLastname(adminRegisterDto.getLastname());
        user.setPassword(passwordEncoder.encode(adminRegisterDto.getPassword()));
        user.setRole(RoleMaster.ROLE_ADMIN);
        UserMasterPK userMasterPk = new UserMasterPK();
        userMasterPk.setUserId(adminRegisterDto.getUserId());
        userMasterPk.setOrganizationId(adminRegisterDto.getOrganizationId());
        user.setUserMasterPk(userMasterPk);
        Optional<UserMaster> userMaster = userMasterRepository.findByEmail(adminRegisterDto.getEmail());
        if (userMaster.isPresent()) {
            throw new Exception("User Already Available With Given Mail ID");
        }
        return userRepository.save(user);
    }

    @Override
    public void authenticate(String email, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }
}