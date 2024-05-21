package com.spring.mongo.api.repository;

import com.spring.mongo.api.entity.InputMaster;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InputMasterRepository extends MongoRepository<InputMaster, Integer> {
}