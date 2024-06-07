package com.spring.mongo.api.repository;

import com.spring.mongo.api.entity.DataDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataDetailRepository extends MongoRepository<DataDetails, String> {
    List<DataDetails> findByType(String type);
}