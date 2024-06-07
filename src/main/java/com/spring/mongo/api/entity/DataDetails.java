package com.spring.mongo.api.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "Data_details")
public class DataDetails {
    private ObjectId id;
    private String value;
    private String label;
    private String type;
    private String isRequired;
    private Integer maxLength;
    private List<Object> options;
}