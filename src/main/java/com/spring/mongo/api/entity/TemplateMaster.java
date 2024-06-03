package com.spring.mongo.api.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Document(collection = "TemplateMaster")
public class TemplateMaster {
    @Id
    public ObjectId id;

    @Field("template_field")
    private String templateField;

    @Field("template_name")
    private String templateName;

    @Field("template_type")
    private String templateType;

    @Field("icon")
    private String icon;
}