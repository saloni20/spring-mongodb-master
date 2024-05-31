package com.spring.mongo.api.resource.controller;

import com.spring.mongo.api.resource.dto.CustomizedScreenDto;
import com.spring.mongo.api.resource.response.Response;
import com.spring.mongo.api.resource.service.ScreenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api")
@CrossOrigin
public class CustomizeScreenController {
    private final ScreenService screenService;

    @PostMapping("/saveUserScreen")
    public Response saveUserScreen(@RequestBody CustomizedScreenDto customizedScreenDto) {
        return screenService.saveUserScreen(customizedScreenDto);
    }
}