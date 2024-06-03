package com.spring.mongo.api.resource.serviceImpl;

import com.spring.mongo.api.entity.ScreenTemplateMaster;
import com.spring.mongo.api.entity.TemplateDetail;
import com.spring.mongo.api.entity.TemplateMaster;
import com.spring.mongo.api.repository.ScreenTemplateMasterRepository;
import com.spring.mongo.api.repository.TemplateDetailRepository;
import com.spring.mongo.api.repository.TemplateMasterRepository;
import com.spring.mongo.api.resource.dto.ScreenTemplateMasterDto;
import com.spring.mongo.api.resource.dto.TemplateMasterDto;
import com.spring.mongo.api.resource.request.TemplateScreenRequest;
import com.spring.mongo.api.resource.response.Response;
import com.spring.mongo.api.resource.service.TemplateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final ScreenTemplateMasterRepository screenTemplateMasterRepository;
    private final TemplateDetailRepository templateDetailRepository;
    private final TemplateMasterRepository templateMasterRepository;

    @Override
    public Response saveScreenMasterTemplateInformation(TemplateScreenRequest templateScreenRequest) {
        //check if already there is a list of screen templates available for logged-in user's orgID
        List<ScreenTemplateMaster> screenTemplateMasters = screenTemplateMasterRepository.findByOrgId(templateScreenRequest.getOrgId());
        if (!CollectionUtils.isEmpty(screenTemplateMasters)) {
            log.info("already screens and template saved for the current orgId");
            updateTemplateScreenMaster(templateScreenRequest, screenTemplateMasters);
        }
        List<ScreenTemplateMasterDto> screenTemplateMasterDtoList = templateScreenRequest.getScreenTemplateMasterDtoList();
        for (ScreenTemplateMasterDto screenTemplateMasterDto : screenTemplateMasterDtoList) {
            ScreenTemplateMaster screenTemplateMaster = getScreenTemplateMaster(screenTemplateMasterDto);
            if (savingTemplateDetail(screenTemplateMasterDto))
                screenTemplateMasterRepository.save(screenTemplateMaster);
            else return new Response("No such template found", HttpStatus.BAD_REQUEST);
        }
        return new Response("Transaction completed successfully.", HttpStatus.OK);
    }

    private static ScreenTemplateMaster getScreenTemplateMaster(ScreenTemplateMasterDto screenTemplateMasterDto) {
        ScreenTemplateMaster screenTemplateMaster = new ScreenTemplateMaster();
        screenTemplateMaster.setSequence(screenTemplateMasterDto.getSequence());
        screenTemplateMaster.setPostScreens(screenTemplateMasterDto.getPostScreens());
        screenTemplateMaster.setPreScreens(screenTemplateMasterDto.getPreScreens());
        screenTemplateMaster.setScreenName(screenTemplateMasterDto.getScreenName());
        screenTemplateMaster.setThumbnail(screenTemplateMasterDto.getThumbnail());
        screenTemplateMaster.setIsMandatory(screenTemplateMasterDto.getIsMandatory());
        screenTemplateMaster.setIsDisabled(screenTemplateMasterDto.getIsDisabled());
        screenTemplateMaster.setScreenField(screenTemplateMasterDto.getScreenField());
        screenTemplateMaster.setTemplateId(new ObjectId(screenTemplateMasterDto.getTemplateId()));
        screenTemplateMaster.setOrgId(screenTemplateMasterDto.getOrgId());
        return screenTemplateMaster;
    }

    private boolean savingTemplateDetail(ScreenTemplateMasterDto screenTemplateMasterDto) {
        Optional<TemplateMaster> templateMasterOptional = templateMasterRepository.findById(new ObjectId(screenTemplateMasterDto.getTemplateId()));
        if (templateMasterOptional.isPresent()) {
            TemplateMaster templateMaster = templateMasterOptional.get();
            TemplateDetail templateDetail = new TemplateDetail();
            templateDetail.setTemplateType(templateMaster.getTemplateType());
            templateDetail.setTemplateName(templateMaster.getTemplateName());
            templateDetail.setId(templateMaster.getId());
            templateDetail.setOrgId(screenTemplateMasterDto.getOrgId());
            templateDetailRepository.save(templateDetail);
            return true;
        }
        return false;
    }

    @Override
    public Response findAllTemplateMaster() {
        List<TemplateMaster> templateMasterList = templateMasterRepository.findAll();
        List<TemplateMasterDto> templateMasterDtoList = new ArrayList<>();
        for(TemplateMaster t : templateMasterList) {
            TemplateMasterDto templateMasterDto = new TemplateMasterDto();
            templateMasterDto.setTemplateId(String.valueOf(t.getId()));
            templateMasterDto.setTemplateField(t.getTemplateField());
            templateMasterDto.setTemplateType(t.getTemplateType());
            templateMasterDto.setTemplateName(t.getTemplateName());
            templateMasterDto.setIcon(t.getIcon());
            templateMasterDtoList.add(templateMasterDto);
        }

        return new Response("Transaction completed successfully.",templateMasterDtoList, HttpStatus.OK);
    }

    @Override
    public Response findScreenByOrgId(Integer orgId) {
        List<ScreenTemplateMaster> screenTemplateMasterList = screenTemplateMasterRepository.findByOrgId(orgId);
        log.info("Fetching all screens with orgId {}",orgId);
        List<ScreenTemplateMasterDto> screenTemplateMasterDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(screenTemplateMasterList)) {
            for (ScreenTemplateMaster screenTemplateMaster : screenTemplateMasterList) {
                ScreenTemplateMasterDto screenTemplateMasterDto = new ScreenTemplateMasterDto();
                screenTemplateMasterDto.setTemplateId(screenTemplateMaster.getTemplateId().toHexString());
                screenTemplateMasterDto.setScreenField(screenTemplateMaster.getScreenField());
                screenTemplateMasterDto.setScreenName(screenTemplateMaster.getScreenName());
                screenTemplateMasterDto.setPostScreens(screenTemplateMaster.getPostScreens());
                screenTemplateMasterDto.setIsDisabled(screenTemplateMaster.getIsDisabled());
                screenTemplateMasterDto.setIsMandatory(screenTemplateMaster.getIsMandatory());
                screenTemplateMasterDto.setThumbnail(screenTemplateMaster.getThumbnail());
                screenTemplateMasterDto.setPreScreens(screenTemplateMaster.getPreScreens());
                screenTemplateMasterDto.setOrgId(screenTemplateMaster.getOrgId());
                screenTemplateMasterDto.setSequence(screenTemplateMaster.getSequence());
                screenTemplateMasterDtoList.add(screenTemplateMasterDto);
            }
        }
        return new Response("Transaction completed successfully.", screenTemplateMasterDtoList, HttpStatus.OK);
    }

    @Override
    public Response updateTemplateScreenMaster(TemplateScreenRequest templateScreenRequest, List<ScreenTemplateMaster> screenTemplateMasterList) {
        int i = 0;
        List<ScreenTemplateMasterDto> screenTemplateMasterDtoList = templateScreenRequest.getScreenTemplateMasterDtoList();
        for (ScreenTemplateMasterDto screenTemplateMasterDto : screenTemplateMasterDtoList) {
            ScreenTemplateMaster screenTemplateMaster = screenTemplateMasterList.get(i++);
            screenTemplateMaster.setSequence(screenTemplateMasterDto.getSequence());
            screenTemplateMaster.setPostScreens(screenTemplateMasterDto.getPostScreens());
            screenTemplateMaster.setPreScreens(screenTemplateMasterDto.getPreScreens());
            screenTemplateMaster.setScreenName(screenTemplateMasterDto.getScreenName());
            screenTemplateMaster.setThumbnail(screenTemplateMasterDto.getThumbnail());
            screenTemplateMaster.setIsMandatory(screenTemplateMasterDto.getIsMandatory());
            screenTemplateMaster.setIsDisabled(screenTemplateMasterDto.getIsDisabled());
            screenTemplateMaster.setScreenField(screenTemplateMasterDto.getScreenField());
            screenTemplateMaster.setOrgId(screenTemplateMasterDto.getOrgId());
            if (savingTemplateDetail(screenTemplateMasterDto))
                screenTemplateMasterRepository.save(screenTemplateMaster);
            else return new Response("No such template found", HttpStatus.BAD_REQUEST);
        }
        return new Response("Transaction completed successfully.", HttpStatus.OK);
    }

    @Override
    public Response deleteTemplateScreenById(Integer id) {
        return null;
    }

    @Override
    public Response findAllScreensByTemplateId(String objectId,Integer orgId) {
        log.info("Fetching all screens with orgId {} and TemplateId {}",objectId);
        List<ScreenTemplateMaster> screenTemplateMasterList=null;
        if(orgId!=null)
            screenTemplateMasterList = screenTemplateMasterRepository.findByOrgIdAndTemplateId(orgId,new ObjectId(objectId));
            else screenTemplateMasterList = screenTemplateMasterRepository.findByTemplateId(new ObjectId(objectId));
        List<ScreenTemplateMasterDto> screenTemplateMasterDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(screenTemplateMasterList)) {
            for (ScreenTemplateMaster screenTemplateMaster : screenTemplateMasterList) {
                ScreenTemplateMasterDto screenTemplateMasterDto = new ScreenTemplateMasterDto();
                screenTemplateMasterDto.setTemplateId(screenTemplateMaster.getTemplateId().toHexString());
                screenTemplateMasterDto.setScreenField(screenTemplateMaster.getScreenField());
                screenTemplateMasterDto.setScreenName(screenTemplateMaster.getScreenName());
                screenTemplateMasterDto.setPostScreens(screenTemplateMaster.getPostScreens());
                screenTemplateMasterDto.setIsDisabled(screenTemplateMaster.getIsDisabled());
                screenTemplateMasterDto.setIsMandatory(screenTemplateMaster.getIsMandatory());
                screenTemplateMasterDto.setThumbnail(screenTemplateMaster.getThumbnail());
                screenTemplateMasterDto.setPreScreens(screenTemplateMaster.getPreScreens());
                screenTemplateMasterDto.setOrgId(screenTemplateMaster.getOrgId());
                screenTemplateMasterDto.setSequence(screenTemplateMaster.getSequence());
                screenTemplateMasterDtoList.add(screenTemplateMasterDto);
            }
        }
        return new Response("Transaction completed successfully.", screenTemplateMasterDtoList, HttpStatus.OK);
    }
}