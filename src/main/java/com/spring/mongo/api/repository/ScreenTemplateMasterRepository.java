package com.spring.mongo.api.repository;

import com.spring.mongo.api.entity.ScreenTemplateMaster;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ScreenTemplateMasterRepository extends MongoRepository<ScreenTemplateMaster, ObjectId> {

    List<ScreenTemplateMaster> findByOrgId(Long orgId);

    List<ScreenTemplateMaster> findByTemplateId(String objectId);

    List<ScreenTemplateMaster> findByOrgIdAndTemplateId(Long orgId, ObjectId objectId);
}