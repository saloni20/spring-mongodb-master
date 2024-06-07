package com.spring.mongo.api.resource.controller;

import com.spring.mongo.api.resource.dto.DataDetailsDto;
import com.spring.mongo.api.resource.response.Response;
import com.spring.mongo.api.resource.service.DataDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin()
public class DataDetailsController {

    private final DataDetailsService dataDetailsService;

    @PostMapping("/addDataByType")
    public Response addDataByType(@RequestBody List<DataDetailsDto> dataDetailsDtoList) {
        return dataDetailsService.addDataByType(dataDetailsDtoList);
    }

    @GetMapping("/getByfilter/{type}")
    public Response getDataByfilter(@PathVariable String type) {
        return dataDetailsService.filterDataByType(type);
    }
}