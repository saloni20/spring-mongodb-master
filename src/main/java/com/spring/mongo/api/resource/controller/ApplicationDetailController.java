package com.spring.mongo.api.resource.controller;

import com.spring.mongo.api.entity.ApplicationDetail;
import com.spring.mongo.api.entity.ClientDetails;
import com.spring.mongo.api.resource.response.Response;
import com.spring.mongo.api.resource.service.ApplicationDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApplicationDetailController {

    private final ApplicationDetailService applicationDetailService;

    @PostMapping("/saveClient")
    public Response saveClient(@RequestBody ClientDetails clientDetails) {
        return applicationDetailService.saveNewClient(clientDetails);
    }

    @PostMapping("/saveApplication")
    public Response saveClient(@RequestBody ApplicationDetail applicationRequest) {
        return applicationDetailService.saveApplication(applicationRequest);
    }

    @GetMapping("/getApplicationDetail/{appId}")
    public Response getApplication(@PathVariable Long appId) {
        return applicationDetailService.getApplicationId(appId);
    }

    @GetMapping("/getClientDetail/{clientId}")
    public Response getClient(@PathVariable Long clientId) {
        return applicationDetailService.getClientById(clientId);
    }
}