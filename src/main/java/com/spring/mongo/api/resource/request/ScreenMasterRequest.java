package com.spring.mongo.api.resource.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ScreenMasterRequest {
    private Integer id;
    private String screenName;
    private Long sequence;
    private String thumbnail;
    private String isMandatory;
    private String isDisabled;
    private String postScreens;
    private String preScreens;
    private String screenField;
    private List<FieldRequest> fieldsMapList;
}