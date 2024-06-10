package com.spring.mongo.api.repository;

import com.spring.mongo.api.entity.UserMaster;
import com.spring.mongo.api.entity.UserMasterPK;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserMasterRepository extends MongoRepository<UserMaster, UserMasterPK> {

    Optional<UserMaster> findByEmail(String email);

    Optional<UserMaster> findByEmailAndUserMasterPK_orgId(String email, Long orgId);

    List<UserMaster> findAllByUserMasterPK_orgId(Long orgId);

    Optional<UserMaster> findByUserMasterPK_UserIdAndUserMasterPK_OrgId(Integer userId, Long orgId);
}