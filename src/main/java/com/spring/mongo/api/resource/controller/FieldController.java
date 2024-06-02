package com.spring.mongo.api.resource.controller;

import com.spring.mongo.api.resource.request.FieldRequest;
import com.spring.mongo.api.resource.response.Response;
import com.spring.mongo.api.resource.service.FieldService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class FieldController {

    private final FieldService fieldService;

    @PostMapping("/addField")
    public Response addField(@RequestBody FieldRequest fieldRequest) {
        return fieldService.addField(fieldRequest);
    }

    @GetMapping("/findAllFieldsByType/{type}")
    public Response findAllFieldsByType(@PathVariable String type) {
        return fieldService.findAllFieldsByType(type);
    }

    @GetMapping("/findAllFieldsByScreenId/{screenId}")
    public Response findAllFieldsByScreenId(@PathVariable Long screenId) {
        return fieldService.findAllFieldsByScreenId(screenId);
    }

    @PostMapping("/updateField")
    public Response updateField(@RequestBody FieldRequest fieldRequest) {
        return fieldService.updateField(fieldRequest);
    }
}