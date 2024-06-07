package com.spring.mongo.api.resource.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataDetailsDto {
    private String id;
    private String value;
    private String label;
    private String type;
    private String isRequired;
    private Integer maxLength;
    private List<Object> options;
}