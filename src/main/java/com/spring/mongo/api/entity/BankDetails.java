package com.spring.mongo.api.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "bankDetail")
public class BankDetails {
    private String bankAmountNumber;
    private String ifscCode;
    private String branch;
}