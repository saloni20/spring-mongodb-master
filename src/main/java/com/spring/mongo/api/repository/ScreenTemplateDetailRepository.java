package com.spring.mongo.api.repository;

import com.spring.mongo.api.entity.ScreenTemplateDetails;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ScreenTemplateDetailRepository extends MongoRepository<ScreenTemplateDetails, ObjectId> {

    List<ScreenTemplateDetails> findByTemplateId(String templateId);

    Optional<ScreenTemplateDetails> findById(String objectId);

    Optional<ScreenTemplateDetails> findByOrgIdAndId(Integer orgId, ObjectId objectId);
}