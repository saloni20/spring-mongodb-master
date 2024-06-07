package com.spring.mongo.api.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class UserMasterPK implements Serializable {
    @Field("orgId")
    private Long orgId;
    @Field("userId")
    private Integer userId;
}