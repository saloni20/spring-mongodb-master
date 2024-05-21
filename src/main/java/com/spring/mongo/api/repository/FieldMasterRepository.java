package com.spring.mongo.api.repository;

import com.spring.mongo.api.entity.FieldMaster;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FieldMasterRepository extends MongoRepository<FieldMaster, Integer> {
    List<FieldMaster> findAllByType(String type);

    List<FieldMaster> findAllByScreenId(Long screenId);
}