package com.spring.mongo.api.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "FieldMaster")
public class FieldMaster {
    @Id
    private Integer id;
    private String label;
    private String field;
    private String isRequired;
    private String type;
    private String placeholder;
    private String request;
    private Long screenId;
}