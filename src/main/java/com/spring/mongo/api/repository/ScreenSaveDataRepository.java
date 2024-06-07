package com.spring.mongo.api.repository;

import com.spring.mongo.api.entity.ScreenSavingData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ScreenSaveDataRepository extends MongoRepository<ScreenSavingData, String> {

    List<ScreenSavingData> findByTemplateId(String templateId);

}