package com.spring.mongo.api.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@Document(collection = "userMaster_pk")
public class UserMasterPK implements Serializable {

    private Integer organizationId;
    private Integer userId;
}