package com.spring.mongo.api.resource.service;

import com.spring.mongo.api.entity.UserMaster;
import com.spring.mongo.api.repository.UserMasterRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserMasterRepository userMasterRepository;

    @Override
    public UserMaster loadUserByUsername(String email) throws UsernameNotFoundException {
        return userMasterRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No user available."));
    }
}