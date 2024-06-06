package com.spring.mongo.api.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "family_Detail")
public class FamilyDetails {
    private String fatherName;
    private String motherName;
}