package com.spring.mongo.api.repository;

import com.spring.mongo.api.entity.UserMaster;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserMasterRepository extends MongoRepository<UserMaster, String> {

    Optional<UserMaster> findByUserMasterPkOrganizationIdAndUserMasterPkUserId(Integer organizationId, Integer userId);

    Optional<UserMaster> findByEmail(String email);
}