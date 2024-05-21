package com.spring.mongo.api.resource.service;

import com.spring.mongo.api.resource.request.InputMasterRequest;
import com.spring.mongo.api.resource.response.Response;
import org.springframework.stereotype.Service;

@Service
public interface InputService {
    Response addInputMaster(InputMasterRequest inputMasterRequest);

    Response findAllInputMaster();
}