package com.spring.mongo.api.resource.serviceImpl;

import com.spring.mongo.api.entity.UserMaster;
import com.spring.mongo.api.repository.UserMasterRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserMasterRepository userMasterRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserMasterRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userMasterRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();


        Optional<UserMaster> user = userMasterRepository.findByEmail(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found.");
        }
        String encryptedPassword = user.get().getPassword();
        if (!passwordEncoder.matches(password, encryptedPassword)) {
            throw new BadCredentialsException("Incorrect password.");
        }
        return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

