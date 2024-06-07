package com.spring.mongo.api.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@Document(collection = "ScreenTemplateDetail")
public class ScreenTemplateDetails {
    @Id
    private ObjectId screenTemplateDetailId;

    @Field("orgId")
    private Long orgId;

    @Field("screenName")
    private String screenName;

    @Field("sequence")
    private int sequence;

    @Field("thumbnail")
    private String thumbnail;

    @Field("isMandatory")
    private String isMandatory;

    @Field("isDisabled")
    private String isDisabled;

    @Field("postScreens")
    private String postScreens;

    @Field("preScreens")
    private String preScreens;

    @Field("screenField")
    private String screenField;

    @Field("templateId")
    private String templateId;

    @Field("fieldsMap")
    private List<Object> fieldsMap;
}