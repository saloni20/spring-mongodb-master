package com.spring.mongo.api.repository;

import com.spring.mongo.api.entity.UserMaster;
import com.spring.mongo.api.entity.UserMasterPK;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserMasterRepository extends MongoRepository<UserMaster, UserMasterPK> {

    Optional<UserMaster> findByEmail(String email);

    Optional<UserMaster> findByEmailAndUserMasterPKOrgId(String email, Long orgId);
}