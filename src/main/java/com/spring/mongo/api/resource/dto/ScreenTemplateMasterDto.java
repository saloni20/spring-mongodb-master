package com.spring.mongo.api.resource.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScreenTemplateMasterDto {
    private Integer orgId;
    private String screenName;
    private Integer sequence;
    private String thumbnail;
    private String isMandatory;
    private String isDisabled;
    private String postScreens;
    private String preScreens;
    private String screenField;
    private String templateId;
}