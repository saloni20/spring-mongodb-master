package com.spring.mongo.api.resource.controller;

import com.spring.mongo.api.resource.dto.ScreenTemplateDetailsDto;
import com.spring.mongo.api.resource.request.TemplateDetailRequest;
import com.spring.mongo.api.resource.request.TemplateScreenRequest;
import com.spring.mongo.api.resource.response.Response;
import com.spring.mongo.api.resource.service.TemplateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TemplateScreenController {

    private final TemplateService templateService;

    @PostMapping("/saveScreenMasterTemplate")
    public Response saveScreenMasterTemplateInformation(@RequestBody TemplateScreenRequest templateScreenRequest) {
        return templateService.saveScreenMasterTemplateInformation(templateScreenRequest);
    }

    @GetMapping("/get/{orgId}")
    public Response findScreenByOrgId(@PathVariable Integer orgId) {
        log.info("request initiated for user with orgId {}", orgId);
        return templateService.findScreenByOrgId(orgId);
    }

    @GetMapping("/findAll")
    public Response findAllTemplateMaster() {
        return templateService.findAllTemplateMaster();
    }

    @GetMapping("/getScreenByTemplateId/{templateId}")
    public Response getScreenByTemplateId(@PathVariable String templateId, @RequestParam(required = false) Integer orgId) {
        log.info("Request initiated for user with orgId {}", orgId);
        return templateService.findAllScreensByTemplateId(templateId, orgId);
    }

    @GetMapping("/getAllTemplateForOrg/{orgId}")
    public Response getAllTemplateForOrg(@PathVariable Integer orgId) {
        return templateService.findAllTemplateDetailForOrg(orgId);
    }

    @PostMapping("/saveCustomTemplate")
    public Response saveCustomTemplateDetail(@RequestBody TemplateDetailRequest templateDetailRequest) {
        return templateService.saveCustomTemplateDetail(templateDetailRequest);
    }

    @GetMapping("/getScreenTemplateDetail/{screenId}")
    public Response getScreenTemplateDetail(@PathVariable String screenId, @RequestParam(required = false) Integer orgId) {
        log.info("Request initiated for user with screenId {}", screenId);
        return templateService.getScreenTemplateDetail(screenId, orgId);
    }

    @PostMapping("/updateScreenTemplateDetail")
    public Response updateScreenTemplateDetail(@RequestBody ScreenTemplateDetailsDto screenTemplateDetailsDto) {
        return templateService.updateScreenTemplateDetail(screenTemplateDetailsDto);
    }
}