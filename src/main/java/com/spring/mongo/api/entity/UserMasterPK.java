package com.spring.mongo.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserMasterPK implements Serializable {
    private Integer organizationId;
    private Integer userId;
}