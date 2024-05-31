package com.spring.mongo.api.repository;

import com.spring.mongo.api.entity.ScreenMaster;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScreenMasterRepository extends MongoRepository<ScreenMaster, Integer> {
}