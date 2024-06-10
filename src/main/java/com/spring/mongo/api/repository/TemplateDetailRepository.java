package com.spring.mongo.api.repository;

import com.spring.mongo.api.entity.TemplateDetail;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TemplateDetailRepository extends MongoRepository<TemplateDetail, ObjectId> {

    List<TemplateDetail> findByOrgIdOrderByInsertedOnDesc(Long orgId);

}