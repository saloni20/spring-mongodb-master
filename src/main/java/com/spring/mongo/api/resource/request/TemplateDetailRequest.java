package com.spring.mongo.api.resource.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TemplateDetailRequest {
    private Long orgId;
    private String templateId;
    private String templateField;
    private String templateName;
    private String templateType;
    private String icon;
}
