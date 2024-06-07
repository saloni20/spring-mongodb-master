package com.spring.mongo.api.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "screen_data")
public class ScreenSavingData {

    @Id
    private String screenId;
    private String templateId;
    private List<RowData> data;
}