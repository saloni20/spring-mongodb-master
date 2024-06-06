package com.spring.mongo.api.entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "client_detail")
public class ClientDetails {
    public static final String SEQUENCE_NAME = "client_detail";
    @Id
    private Long clientId;
    private String firstName;
    private String LastName;
    private String mobileNumber;
    private String age;
    private String gender;
}
