package com.spring.mongo.api.resource.service;

import com.spring.mongo.api.resource.request.FieldRequest;
import com.spring.mongo.api.resource.response.Response;
import org.springframework.stereotype.Service;

@Service
public interface FieldService {
    Response addField(FieldRequest fieldRequest);

    Response findAllFieldsByType(String type);

    Response findAllFieldsByScreenId(Long screenId);

    Response updateField(FieldRequest fieldRequest);
}