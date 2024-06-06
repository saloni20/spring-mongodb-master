package com.spring.mongo.api.resource.service;

import com.spring.mongo.api.entity.ApplicationDetail;
import com.spring.mongo.api.entity.ClientDetails;
import com.spring.mongo.api.resource.request.ApplicationRequest;
import com.spring.mongo.api.resource.response.Response;

public interface ApplicationDetailService {

    Response  saveNewClient(ClientDetails clientDetails);
    Response saveApplication(ApplicationDetail applicationDetail);
    Response getClientById(Long clientId);
    Response getApplicationId(Long applicationId);

}
