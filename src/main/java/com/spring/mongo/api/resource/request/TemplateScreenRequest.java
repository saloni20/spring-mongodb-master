package com.spring.mongo.api.resource.request;

import com.spring.mongo.api.resource.dto.ScreenTemplateMasterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TemplateScreenRequest {
    private Integer orgId;
    private List<ScreenTemplateMasterDto> screenTemplateMasterDtoList;
}