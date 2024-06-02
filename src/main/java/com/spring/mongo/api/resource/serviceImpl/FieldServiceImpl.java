package com.spring.mongo.api.resource.serviceImpl;

import com.spring.mongo.api.entity.FieldMaster;
import com.spring.mongo.api.repository.FieldMasterRepository;
import com.spring.mongo.api.resource.request.FieldRequest;
import com.spring.mongo.api.resource.response.Response;
import com.spring.mongo.api.resource.service.FieldService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FieldServiceImpl implements FieldService {

    private final FieldMasterRepository fieldMasterRepository;

    @Override
    public Response addField(FieldRequest fieldRequest) {
        Optional<FieldMaster> fieldMasterOptional = fieldMasterRepository.findById(fieldRequest.getId());
        if (fieldMasterOptional.isEmpty()) {
            FieldMaster fieldMaster = getFieldMaster(fieldRequest);
            fieldMasterRepository.save(fieldMaster);
            return new Response("Added Field.", fieldMaster.getId(), HttpStatus.OK);
        }
        return null;
    }

    private static FieldMaster getFieldMaster(FieldRequest fieldRequest) {
        FieldMaster fieldMaster = new FieldMaster();
        fieldMaster.setId(fieldRequest.getId());
        fieldMaster.setLabel(fieldRequest.getLabel());
        fieldMaster.setField(fieldRequest.getField());
        fieldMaster.setType(fieldRequest.getType());
        fieldMaster.setIsRequired(fieldRequest.getIsRequired());
        fieldMaster.setRequest(fieldRequest.getRequest());
        fieldMaster.setPlaceholder(fieldRequest.getPlaceholder());
        fieldMaster.setScreenId(fieldRequest.getScreenId());
        return fieldMaster;
    }

    @Override
    public Response findAllFieldsByType(String type) {
        List<FieldRequest> fieldRequestArrayList = new ArrayList<>();
        List<FieldMaster> fieldMasterList = fieldMasterRepository.findAllByType(type);
        for (FieldMaster fieldMaster : fieldMasterList) {
            FieldRequest fieldRequest = getFieldRequest(fieldMaster);
            fieldRequestArrayList.add(fieldRequest);
        }
        return new Response("Field master list found.", fieldRequestArrayList, HttpStatus.OK);
    }

    @Override
    public Response findAllFieldsByScreenId(Long screenId) {
        List<FieldRequest> fieldRequestArrayList = new ArrayList<>();
        List<FieldMaster> fieldMasterList = fieldMasterRepository.findAllByScreenId(screenId);
        for (FieldMaster fieldMaster : fieldMasterList) {
            FieldRequest fieldRequest = getFieldRequest(fieldMaster);
            fieldRequestArrayList.add(fieldRequest);
        }
        return new Response("Field master list found.", fieldRequestArrayList, HttpStatus.OK);
    }

    private static FieldRequest getFieldRequest(FieldMaster fieldMaster) {
        FieldRequest fieldRequest = new FieldRequest();
        fieldRequest.setId(fieldMaster.getId());
        fieldRequest.setLabel(fieldMaster.getLabel());
        fieldRequest.setField(fieldMaster.getField());
        fieldRequest.setType(fieldMaster.getType());
        fieldRequest.setIsRequired(fieldMaster.getIsRequired());
        fieldRequest.setRequest(fieldMaster.getRequest());
        fieldRequest.setPlaceholder(fieldMaster.getPlaceholder());
        fieldRequest.setScreenId(fieldMaster.getScreenId());
        return fieldRequest;
    }

    @Override
    public Response updateField(FieldRequest fieldRequest) {
        Optional<FieldMaster> fieldMasterOptional = fieldMasterRepository.findById(fieldRequest.getId());
        if (fieldMasterOptional.isPresent()) {
            FieldMaster fieldMaster = fieldMasterOptional.get();
            fieldMaster.setLabel(fieldRequest.getLabel());
            fieldMaster.setField(fieldRequest.getField());
            fieldMaster.setType(fieldRequest.getType());
            fieldMaster.setIsRequired(fieldRequest.getIsRequired());
            fieldMaster.setRequest(fieldRequest.getRequest());
            fieldMaster.setPlaceholder(fieldRequest.getPlaceholder());
            fieldMaster.setScreenId(fieldRequest.getScreenId());
            fieldMasterRepository.save(fieldMaster);
            return new Response("Transaction completed successfully.", HttpStatus.OK);
        }
        return null;
    }
}