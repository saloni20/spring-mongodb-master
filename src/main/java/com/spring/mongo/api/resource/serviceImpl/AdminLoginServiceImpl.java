package com.spring.mongo.api.resource.serviceImpl;

import com.spring.mongo.api.entity.RoleMaster;
import com.spring.mongo.api.entity.UserMaster;
import com.spring.mongo.api.entity.UserMasterPK;
import com.spring.mongo.api.repository.UserMasterRepository;
import com.spring.mongo.api.repository.dao.AdminDaoImpl;
import com.spring.mongo.api.resource.dto.AdminRegisterDto;
import com.spring.mongo.api.resource.dto.LoginRequestDto;
import com.spring.mongo.api.resource.dto.LoginResponseDto;
import com.spring.mongo.api.resource.response.Response;
import com.spring.mongo.api.resource.service.AdminLoginService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class AdminLoginServiceImpl implements AdminLoginService {

    private final AdminDaoImpl adminDaoImpl;
    private final PasswordEncoder passwordEncoder;
    private final JwtHelper jwtService;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final UserMasterRepository userMasterRepository;

    @Override
    public Response login(LoginRequestDto loginRequestDto) {
        loginRequestDto.setEmail(loginRequestDto.getEmail().toLowerCase());
        this.authenticate(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        Response response = loadUserByUsername(loginRequestDto.getEmail().toLowerCase(), loginRequestDto.getOrgId());
        if (!response.getStatus().is2xxSuccessful()) {
            return response;
        } else {
            UserMaster userMaster = (UserMaster) response.getData();
            String jwt = jwtService.genrateJwtToken(userMaster);
            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setUsername(userMaster.getUsername());
            loginResponseDto.setOrgId(userMaster.getUserMasterPK().getOrgId());
            loginResponseDto.setUserId(userMaster.getUserMasterPK().getUserId());
            loginResponseDto.setToken(jwt);
            log.info("Response : {}", loginResponseDto);
            return new Response("Transaction completed successfully.", loginResponseDto, HttpStatus.OK);
        }
    }

    private Response loadUserByUsername(String email, Long orgId) throws UsernameNotFoundException {
        if (orgId != null && StringUtils.hasText(email)) {
            Optional<UserMaster> userMasterOptional = userMasterRepository.findByEmailAndUserMasterPKOrgId(email.toLowerCase(), orgId);
            if (userMasterOptional.isPresent()) {
                UserMaster userMaster = userMasterOptional.get();
                return new Response("Transaction completed successfully.", userMaster, HttpStatus.OK);
            } else {
                return new Response("No user found.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new Response("User id or org id is not present.", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Response registerUser(AdminRegisterDto adminRegisterDto) {
        UserMaster user = new UserMaster();
        user.setEmail(adminRegisterDto.getEmail());
        user.setFirstname(adminRegisterDto.getFirstname());
        user.setLastname(adminRegisterDto.getLastname());
        user.setPassword(passwordEncoder.encode(adminRegisterDto.getPassword()));
        user.setRole(RoleMaster.ROLE_ADMIN);
        UserMasterPK userMasterPK = new UserMasterPK();
        userMasterPK.setOrgId(adminRegisterDto.getOrgId());
        String userIdStart = "101";
        int count = 0;
//        Long id = Long.valueOf(adminDaoImpl.findMaxUserId());
        String userId = userIdStart + count++;
//        log.info("User : {}", id);

        user.setUserMasterPK(userMasterPK);
        Optional<UserMaster> userMaster = userMasterRepository.findByEmailAndUserMasterPKOrgId(adminRegisterDto.getEmail().toLowerCase(), adminRegisterDto.getOrgId());
        if (userMaster.isPresent()) {
            return new Response("User already available with given email address.", HttpStatus.BAD_REQUEST);
        } else {
            user.setEmail(adminRegisterDto.getEmail().toLowerCase());
            userMasterRepository.save(user);
            return new Response("Transaction completed successfully. User created with user id - " + user.getUserMasterPK().getUserId(), user.getUserMasterPK().getUserId(), HttpStatus.OK);
        }
    }

    @Override
    public void authenticate(String email, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        this.customAuthenticationProvider.authenticate(usernamePasswordAuthenticationToken);
    }
}