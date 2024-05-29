package com.spring.mongo.api.resource.service;

import com.spring.mongo.api.entity.UserMaster;
import com.spring.mongo.api.repository.UserMasterRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserMasterRepository userMasterRepository;

    @Override
    public UserMaster loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserMaster> userMaster = userMasterRepository.findByEmail(email);
        return userMasterRepository.findByUserMasterPkOrganizationIdAndUserMasterPkUserId(userMaster.get().getUserMasterPk().getOrganizationId(), userMaster.get().getUserMasterPk().getOrganizationId()).orElseThrow(() -> new UsernameNotFoundException("no user available"));
    }
}