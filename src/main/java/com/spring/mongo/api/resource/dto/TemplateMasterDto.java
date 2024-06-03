package com.spring.mongo.api.resource.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TemplateMasterDto {
    private String templateId;
    private String templateField;
    private String templateName;
    private String templateType;
    private String icon;
}
