package com.spring.mongo.api.resource.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TemplateMasterDto {
    private String templateId;
    private String templateField;
    private String templateName;
    private String templateType;
    private String icon;
    private String insertedOn;
}