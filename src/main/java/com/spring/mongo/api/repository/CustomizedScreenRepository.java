package com.spring.mongo.api.repository;

import com.spring.mongo.api.entity.CustomizedScreen;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomizedScreenRepository extends MongoRepository<CustomizedScreen, Integer> {
    Optional<CustomizedScreen> findByUserIdAndOrgId(Integer userId, Integer orgId);
}