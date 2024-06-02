package com.spring.mongo.api.repository;
import com.spring.mongo.api.entity.TemplateMaster;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface TemplateMasterRepository extends MongoRepository<TemplateMaster,ObjectId>{
}
