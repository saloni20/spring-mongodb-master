package com.spring.mongo.api.resource.controller;

import com.spring.mongo.api.resource.request.ScreenMasterRequest;
import com.spring.mongo.api.resource.response.Response;
import com.spring.mongo.api.resource.service.ScreenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
public class ScreenController {

    private final ScreenService screenService;

    @PostMapping("/addScreenMaster")
    public Response addScreenMaster(@RequestBody ScreenMasterRequest screenMasterRequest) {
        return screenService.addScreenMaster(screenMasterRequest);
    }

    @GetMapping("/findAllScreenMaster")
    public Response findAllScreenMaster() {
        return screenService.findAllScreenMaster();
    }

    @GetMapping("/findScreenById/{id}")
    public Response findScreenById(@PathVariable Integer id) {
        return screenService.findScreenById(id);
    }

    @PostMapping("/updateScreen")
    public Response updateScreen(@RequestBody ScreenMasterRequest screenMasterRequest) {
        return screenService.updateScreen(screenMasterRequest);
    }

    @DeleteMapping("/deleteScreen/{id}")
    public Response deleteScreenById(@PathVariable Integer id) {
        return screenService.deleteScreenById(id);
    }
}