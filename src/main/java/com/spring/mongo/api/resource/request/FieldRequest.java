package com.spring.mongo.api.resource.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldRequest {
    private Integer id;
    private String label;
    private String field;
    private String isRequired;
    private String type;
    private String placeholder;
    private String request;
    private Long screenId;
}