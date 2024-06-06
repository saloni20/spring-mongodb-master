package com.spring.mongo.api.resource.request;

import com.spring.mongo.api.entity.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationRequest {
    private Long clientId;
    private AddressDetails addressDetails;
    private ClientDetails clientDetails;
    private BankDetails bankDetails;
    private FamilyDetails familyDetails;
    private ProductDetails productDetails;
}