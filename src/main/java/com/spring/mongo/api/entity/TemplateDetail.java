package com.spring.mongo.api.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Getter
@Setter
@Document(collection = "TemplateDetail")
public class TemplateDetail {

    @Id
    private ObjectId id;

    @Field("org_id")
    private Integer orgId;

    @Field("template_field")
    private String templateField;

    @Field("template_name")
    private String templateName;

    @Field("template_type")
    private String templateType;

    @Field("icon")
    private String icon;

}
