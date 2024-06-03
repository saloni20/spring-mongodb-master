package com.spring.mongo.api.repository;

import com.spring.mongo.api.entity.ScreenTemplateMaster;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ScreenTemplateMasterRepository extends MongoRepository<ScreenTemplateMaster, ObjectId> {

    List<ScreenTemplateMaster> findByOrgId(Integer orgId);
    List<ScreenTemplateMaster> findByTemplateId(ObjectId objectId);
    List<ScreenTemplateMaster> findByOrgIdAndTemplateId(Integer orgId, ObjectId objectId);

}