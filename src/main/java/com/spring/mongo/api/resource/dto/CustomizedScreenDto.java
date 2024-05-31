package com.spring.mongo.api.resource.dto;

import com.spring.mongo.api.entity.ScreenMaster;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomizedScreenDto {
    private Integer userId;
    private Integer orgId;
    private List<ScreenMaster> screenMasterList;
}