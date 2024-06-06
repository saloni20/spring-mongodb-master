package com.spring.mongo.api.repository;

import com.spring.mongo.api.entity.ApplicationDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplicationDetailRepository extends MongoRepository<ApplicationDetail, Long> {
}