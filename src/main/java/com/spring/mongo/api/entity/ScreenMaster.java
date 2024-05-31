package com.spring.mongo.api.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "ScreenMaster")
public class ScreenMaster {
    @Id
    private Integer id;
    private String screenName;
    private Long sequence;
    private String thumbnail;
    private String isMandatory;
    private String isDisabled;
    private String postScreens;
    private String preScreens;
    private String isDraggable;
}
