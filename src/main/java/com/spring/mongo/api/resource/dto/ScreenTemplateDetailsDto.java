package com.spring.mongo.api.resource.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScreenTemplateDetailsDto {
    private String id;
    private Long orgId;
    private String screenName;
    private int sequence;
    private String thumbnail;
    private String isMandatory;
    private String isDisabled;
    private String postScreens;
    private String preScreens;
    private String screenField;
    private String templateId;
    private List<Object> fieldsMap;
}