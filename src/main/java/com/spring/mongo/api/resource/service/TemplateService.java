package com.spring.mongo.api.resource.service;

import com.spring.mongo.api.entity.ScreenTemplateMaster;
import com.spring.mongo.api.resource.dto.ScreenTemplateDetailsDto;
import com.spring.mongo.api.resource.request.TemplateDetailRequest;
import com.spring.mongo.api.resource.request.TemplateScreenRequest;
import com.spring.mongo.api.resource.response.Response;

import java.util.List;

public interface TemplateService {
    Response saveScreenMasterTemplateInformation(TemplateScreenRequest templateScreenRequest);

    Response findAllTemplateMaster();

    Response findScreenByOrgId(Long orgId);

    Response updateTemplateScreenMaster(TemplateScreenRequest templateScreenRequest, List<ScreenTemplateMaster> screenTemplateMasterList);

    Response deleteTemplateScreenById(Integer id);

    Response findAllScreensByTemplateId(String objectId, Long orgId);

    Response findAllScreensDetailsByTemplateId(String templateId, Long orgId);

    Response findAllTemplateDetailForOrg(Long orgId);

    Response saveCustomTemplateDetail(TemplateDetailRequest orgId);

    Response getScreenTemplateDetail(String screenId, Long orgId);

    Response updateScreenTemplateDetail(ScreenTemplateDetailsDto screenTemplateDetailsDto);

    Response saveScreenDetailsTemplate(TemplateScreenRequest templateScreenRequest);
}