package com.spring.mongo.api.repository;
import com.spring.mongo.api.entity.ScreenTemplateMaster;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ScreenTemplateMasterRepository extends MongoRepository<ScreenTemplateMaster, ObjectId> {

     List<ScreenTemplateMaster> findByOrgId(Integer orgId);

}
