package com.spring.mongo.api.resource.service;

import com.spring.mongo.api.entity.ScreenTemplateMaster;
import com.spring.mongo.api.resource.request.TemplateScreenRequest;
import com.spring.mongo.api.resource.response.Response;

import java.util.List;

public interface TemplateService {
    Response saveScreenMasterTemplateInformation(TemplateScreenRequest templateScreenRequest);

    Response findAllTemplateMaster();

    Response findScreenByOrgId(Integer orgId);

    Response updateTemplateScreenMaster(TemplateScreenRequest templateScreenRequest, List<ScreenTemplateMaster> screenTemplateMasterList);

    Response deleteTemplateScreenById(Integer id);

}