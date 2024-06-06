package com.spring.mongo.api.repository;

import com.spring.mongo.api.entity.ClientDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientDetailRepository extends MongoRepository<ClientDetails, Long> {
}