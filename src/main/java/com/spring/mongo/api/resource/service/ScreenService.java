package com.spring.mongo.api.resource.service;

import com.spring.mongo.api.resource.request.ScreenMasterRequest;
import com.spring.mongo.api.resource.response.Response;
import org.springframework.stereotype.Service;

@Service
public interface ScreenService {
    Response addScreenMaster(ScreenMasterRequest screenMasterRequest);

    Response findAllScreenMaster();

    Response findScreenById(Integer id);

    Response updateScreen(ScreenMasterRequest screenMasterRequest);

    Response deleteScreenById(Integer id);
}