package com.spring.mongo.api.resource.controller;

import com.spring.mongo.api.resource.request.InputMasterRequest;
import com.spring.mongo.api.resource.response.Response;
import com.spring.mongo.api.resource.service.InputService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class InputController {

    private final InputService inputService;

    @PostMapping("/addInputMaster")
    public Response addInputMaster(@RequestBody InputMasterRequest inputMasterRequest) {
        return inputService.addInputMaster(inputMasterRequest);
    }

    @GetMapping("/findAllInputMaster")
    public Response findAllInputMaster() {
        return inputService.findAllInputMaster();
    }
}