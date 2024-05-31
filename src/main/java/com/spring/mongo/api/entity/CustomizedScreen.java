package com.spring.mongo.api.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "customized_screen")
public class CustomizedScreen {
    private Integer userId;
    private Integer orgId;
    private List<ScreenMaster> screenMasterList;
}
