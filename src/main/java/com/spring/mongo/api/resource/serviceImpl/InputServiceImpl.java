package com.spring.mongo.api.resource.serviceImpl;

import com.spring.mongo.api.entity.InputMaster;
import com.spring.mongo.api.repository.InputMasterRepository;
import com.spring.mongo.api.resource.request.InputMasterRequest;
import com.spring.mongo.api.resource.response.Response;
import com.spring.mongo.api.resource.service.InputService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class InputServiceImpl implements InputService {

    private final InputMasterRepository inputMasterRepository;

    @Override
    public Response addInputMaster(InputMasterRequest inputMasterRequest) {
        Optional<InputMaster> inputMasterOptional = inputMasterRepository.findById(inputMasterRequest.getId());
        if (inputMasterOptional.isEmpty()) {
            InputMaster inputMaster = new InputMaster();
            inputMaster.setId(inputMasterRequest.getId());
            inputMaster.setType(inputMasterRequest.getType());
            inputMasterRepository.save(inputMaster);
            return new Response("Added Input Master.", inputMaster.getId(), HttpStatus.OK);
        }
        return null;
    }

    @Override
    public Response findAllInputMaster() {
        List<InputMasterRequest> inputMasterRequestList = new ArrayList<>();
        List<InputMaster> inputMasterList = inputMasterRepository.findAll();
        for (InputMaster inputMaster : inputMasterList) {
            InputMasterRequest inputMasterRequest = new InputMasterRequest();
            inputMasterRequest.setId(inputMaster.getId());
            inputMasterRequest.setType(inputMaster.getType());
            inputMasterRequestList.add(inputMasterRequest);
        }
        return new Response("Input master list found.", inputMasterRequestList, HttpStatus.OK);
    }
}