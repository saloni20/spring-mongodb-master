package com.spring.mongo.api.repository;

import com.spring.mongo.api.entity.ScreenTemplateDetails;
import com.spring.mongo.api.entity.ScreenTemplateMaster;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ScreenTemplateDetailRepository extends MongoRepository<ScreenTemplateDetails, ObjectId> {

/*    List<ScreenTemplateMaster> findByOrgId(Integer orgId);

    List<ScreenTemplateMaster> findByTemplateId(String objectId);

    List<ScreenTemplateMaster> findByOrgIdAndTemplateId(Integer orgId, ObjectId objectId);*/
}
