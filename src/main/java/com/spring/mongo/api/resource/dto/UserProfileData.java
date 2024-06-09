package com.spring.mongo.api.resource.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileData {
    private Integer userId;
    private String emailId;
    private String firstName;
    private String organization;
    private String mobileNumber;
    private String role;
}
