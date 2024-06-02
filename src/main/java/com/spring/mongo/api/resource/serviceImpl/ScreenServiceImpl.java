package com.spring.mongo.api.resource.serviceImpl;

import com.spring.mongo.api.entity.FieldMaster;
import com.spring.mongo.api.entity.ScreenMaster;
import com.spring.mongo.api.entity.ScreenSavingData;
import com.spring.mongo.api.repository.FieldMasterRepository;
import com.spring.mongo.api.repository.ScreenMasterRepository;
import com.spring.mongo.api.repository.ScreenSaveDataRepository;
import com.spring.mongo.api.resource.request.FieldRequest;
import com.spring.mongo.api.resource.request.ScreenMasterRequest;
import com.spring.mongo.api.resource.response.Response;
import com.spring.mongo.api.resource.service.ScreenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ScreenServiceImpl implements ScreenService {

    private final ScreenMasterRepository screenMasterRepository;
    private final FieldMasterRepository fieldMasterRepository;
    private final ScreenSaveDataRepository screenSaveDataRepository;

    @Override
    public Response addScreenMaster(ScreenMasterRequest screenMasterRequest) {
        Optional<ScreenMaster> screenMasterOptional = screenMasterRepository.findById(screenMasterRequest.getId());
        if (screenMasterOptional.isEmpty()) {
            ScreenMaster screenMaster = new ScreenMaster();
            screenMaster.setId(screenMasterRequest.getId());
            screenMaster.setSequence(screenMasterRequest.getSequence());
            screenMaster.setPostScreens(screenMasterRequest.getPostScreens());
            screenMaster.setPreScreens(screenMasterRequest.getPreScreens());
            screenMaster.setScreenName(screenMasterRequest.getScreenName());
            screenMaster.setThumbnail(screenMasterRequest.getThumbnail());
            screenMaster.setIsMandatory(screenMasterRequest.getIsMandatory());
            screenMaster.setIsDisabled(screenMasterRequest.getIsDisabled());
            screenMaster.setScreenField(screenMasterRequest.getScreenField());
            screenMasterRepository.save(screenMaster);
            return new Response("Added Master Screen.", screenMaster.getId(), HttpStatus.OK);
        }
        return null;
    }

    @Override
    public Response findAllScreenMaster() {
        List<ScreenMasterRequest> screenMasterRequestList = new ArrayList<>();
        List<ScreenMaster> screenMasterList = screenMasterRepository.findAll();
        for (ScreenMaster screenMaster : screenMasterList) {
            ScreenMasterRequest screenMasterRequest = getScreenMasterRequest(screenMaster);
            List<FieldRequest> fieldRequestList = new ArrayList<>();
            List<FieldMaster> fieldMasterList = fieldMasterRepository.findAllByScreenId(Long.valueOf(screenMaster.getId()));
            for (FieldMaster fieldMaster : fieldMasterList) {
                FieldRequest fieldRequest = new FieldRequest();
                BeanUtils.copyProperties(fieldMaster, fieldRequest);
                fieldRequestList.add(fieldRequest);
            }
            screenMasterRequest.setFieldsMapList(fieldRequestList);
            screenMasterRequestList.add(screenMasterRequest);
        }
        return new Response("Screen master list found.", screenMasterRequestList, HttpStatus.OK);
    }

    private static ScreenMasterRequest getScreenMasterRequest(ScreenMaster screenMaster) {
        ScreenMasterRequest screenMasterRequest = new ScreenMasterRequest();
        screenMasterRequest.setId(screenMaster.getId());
        screenMasterRequest.setSequence(screenMaster.getSequence());
        screenMasterRequest.setPostScreens(screenMaster.getPostScreens());
        screenMasterRequest.setPreScreens(screenMaster.getPreScreens());
        screenMasterRequest.setScreenName(screenMaster.getScreenName());
        screenMasterRequest.setThumbnail(screenMaster.getThumbnail());
        screenMasterRequest.setIsMandatory(screenMaster.getIsMandatory());
        screenMasterRequest.setIsDisabled(screenMaster.getIsDisabled());
        screenMasterRequest.setScreenField(screenMaster.getScreenField());
        return screenMasterRequest;
    }

    @Override
    public Response findScreenById(Integer id) {
        Optional<ScreenMaster> screenMasterOptional = screenMasterRepository.findById(id);
        List<ScreenMasterRequest> screenMasterRequestList = new ArrayList<>();
        ScreenMasterRequest screenMasterRequest = new ScreenMasterRequest();
        if (screenMasterOptional.isPresent()) {
            ScreenMaster screenMaster = screenMasterOptional.get();
            screenMasterRequest.setId(screenMaster.getId());
            screenMasterRequest.setSequence(screenMaster.getSequence());
            screenMasterRequest.setPostScreens(screenMaster.getPostScreens());
            screenMasterRequest.setPreScreens(screenMaster.getPreScreens());
            screenMasterRequest.setScreenName(screenMaster.getScreenName());
            screenMasterRequest.setThumbnail(screenMaster.getThumbnail());
            screenMasterRequest.setIsMandatory(screenMaster.getIsMandatory());
            screenMasterRequest.setIsDisabled(screenMaster.getIsDisabled());
            screenMasterRequest.setScreenField(screenMaster.getScreenField());
            List<FieldRequest> fieldRequestList = new ArrayList<>();
            List<FieldMaster> fieldMasterList = fieldMasterRepository.findAllByScreenId(Long.valueOf(screenMaster.getId()));
            for (FieldMaster fieldMaster : fieldMasterList) {
                FieldRequest fieldRequest = new FieldRequest();
                BeanUtils.copyProperties(fieldMaster, fieldRequest);
                fieldRequestList.add(fieldRequest);
            }
            screenMasterRequest.setFieldsMapList(fieldRequestList);
            screenMasterRequestList.add(screenMasterRequest);
            return new Response("Transaction completed successfully.", screenMasterRequest, HttpStatus.OK);
        }
        return new Response("No data found.", HttpStatus.NOT_FOUND);
    }

    @Override
    public Response updateScreen(ScreenMasterRequest screenMasterRequest) {
        Optional<ScreenMaster> screenMasterOptional = screenMasterRepository.findById(screenMasterRequest.getId());
        if (screenMasterOptional.isPresent()) {
            ScreenMaster screenMaster = screenMasterOptional.get();
            screenMaster.setSequence(screenMasterRequest.getSequence());
            screenMaster.setPostScreens(screenMasterRequest.getPostScreens());
            screenMaster.setPreScreens(screenMasterRequest.getPreScreens());
            screenMaster.setScreenName(screenMasterRequest.getScreenName());
            screenMaster.setThumbnail(screenMasterRequest.getThumbnail());
            screenMaster.setIsMandatory(screenMasterRequest.getIsMandatory());
            screenMaster.setIsDisabled(screenMasterRequest.getIsDisabled());
            screenMaster.setScreenField(screenMasterRequest.getScreenField());
            screenMasterRepository.save(screenMaster);
            return new Response("Transaction completed successfully.", screenMaster, HttpStatus.OK);
        }
        return null;
    }

    @Override
    public Response deleteScreenById(Integer id) {
        Optional<ScreenMaster> screenMasterOptional = screenMasterRepository.findById(id);
        if (screenMasterOptional.isPresent()) {
            ScreenMaster screenMaster = screenMasterOptional.get();
            screenMasterRepository.delete(screenMaster);
        }
        return new Response("Transaction completed successfully.", HttpStatus.OK);
    }

    @Override
    public Response saveScreenData(ScreenSavingData screenSavingData) {
        screenSaveDataRepository.save(screenSavingData);
        return new Response("Transaction completed successfully.", screenSavingData.getScreenId(), HttpStatus.OK);
    }

    @Override
    public Response findScreenDataById(String screenId) {
        Optional<ScreenSavingData> dataOptional = screenSaveDataRepository.findById(screenId);
        return dataOptional.map(screenSavingData -> new Response("Data Fetched", screenSavingData, HttpStatus.OK)).orElseGet(() -> new Response("No such screen data found", null, HttpStatus.BAD_REQUEST));
    }
}