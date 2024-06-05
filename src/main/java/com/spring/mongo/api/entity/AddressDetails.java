package com.spring.mongo.api.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "AddressDetails")
public class AddressDetails {
    private String pincode;
    private String taluka;
    private String street;
    private String village;

}
