package com.spring.mongo.api.resource.service;

import com.spring.mongo.api.resource.dto.DataDetailsDto;
import com.spring.mongo.api.resource.response.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DataDetailsService {
    Response addDataByType(List<DataDetailsDto> dataDetailsDtoList);

    Response filterDataByType(String type);
}