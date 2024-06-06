package com.spring.mongo.api.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "application_detail")
public class ApplicationDetail {
    public static final String SEQUENCE_NAME = "application_detail";
    @Id
    private Long applicationId;
    private AddressDetails addressDetails;
    private ClientDetails clientDetails;
    private BankDetails bankDetails;
    private FamilyDetails familyDetails;
    private ProductDetails productDetails;
}