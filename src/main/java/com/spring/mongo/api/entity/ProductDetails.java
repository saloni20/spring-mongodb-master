package com.spring.mongo.api.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "ProductDetails")
public class ProductDetails {
    private String productName;
    private String productId;
    private Integer amount;
}
