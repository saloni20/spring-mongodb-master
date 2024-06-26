package com.spring.mongo.api.resource.serviceImpl;

import com.spring.mongo.api.entity.ScreenTemplateDetails;
import com.spring.mongo.api.entity.ScreenTemplateMaster;
import com.spring.mongo.api.entity.TemplateDetail;
import com.spring.mongo.api.entity.TemplateMaster;
import com.spring.mongo.api.repository.ScreenTemplateDetailRepository;
import com.spring.mongo.api.repository.ScreenTemplateMasterRepository;
import com.spring.mongo.api.repository.TemplateDetailRepository;
import com.spring.mongo.api.repository.TemplateMasterRepository;
import com.spring.mongo.api.resource.dto.ScreenTemplateDetailsDto;
import com.spring.mongo.api.resource.dto.ScreenTemplateMasterDto;
import com.spring.mongo.api.resource.dto.TemplateMasterDto;
import com.spring.mongo.api.resource.request.TemplateDetailRequest;
import com.spring.mongo.api.resource.request.TemplateScreenRequest;
import com.spring.mongo.api.resource.response.Response;
import com.spring.mongo.api.resource.service.TemplateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
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
    private final ScreenTemplateDetailRepository screenTemplateDetailRepository;

    @Override
    public Response saveScreenMasterTemplateInformation(TemplateScreenRequest templateScreenRequest) {
        //check if already there is a list of screen templates available for logged-in user's orgID
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
        screenTemplateMaster.setTemplateId(screenTemplateMasterDto.getTemplateId());
        screenTemplateMaster.setOrgId(screenTemplateMasterDto.getOrgId());
        return screenTemplateMaster;
    }

    private ScreenTemplateDetails getScreenTemplateDetails(ScreenTemplateMasterDto screenTemplateMasterDto) {

        Optional<ScreenTemplateDetails> screenTemplateDetailsOptional = screenTemplateDetailRepository.findByScreenTemplateDetailId(screenTemplateMasterDto.getScreenId());
        ScreenTemplateDetails screenTemplateDetails;
        screenTemplateDetails = screenTemplateDetailsOptional.orElseGet(ScreenTemplateDetails::new);
        if (screenTemplateMasterDto.getSequence() != null)
            screenTemplateDetails.setSequence(screenTemplateMasterDto.getSequence());
        if (screenTemplateMasterDto.getPostScreens() != null)
            screenTemplateDetails.setPostScreens(screenTemplateMasterDto.getPostScreens());
        if (screenTemplateMasterDto.getPreScreens() != null)
            screenTemplateDetails.setPreScreens(screenTemplateMasterDto.getPreScreens());
        if (screenTemplateMasterDto.getScreenName() != null)
            screenTemplateDetails.setScreenName(screenTemplateMasterDto.getScreenName());
        if (screenTemplateMasterDto.getThumbnail() != null)
            screenTemplateDetails.setThumbnail(screenTemplateMasterDto.getThumbnail());
        if (screenTemplateMasterDto.getIsMandatory() != null)
            screenTemplateDetails.setIsMandatory(screenTemplateMasterDto.getIsMandatory());
        if (screenTemplateMasterDto.getIsDisabled() != null)
            screenTemplateDetails.setIsDisabled(screenTemplateMasterDto.getIsDisabled());
        if (screenTemplateMasterDto.getScreenField() != null)
            screenTemplateDetails.setScreenField(screenTemplateMasterDto.getScreenField());
        if (screenTemplateMasterDto.getOrgId() != null)
            screenTemplateDetails.setOrgId(screenTemplateMasterDto.getOrgId());
        return screenTemplateDetails;
    }

    private boolean savingTemplateDetail(ScreenTemplateMasterDto screenTemplateMasterDto) {
        Optional<TemplateDetail> templateDetailOptional = templateDetailRepository.findById(new ObjectId(screenTemplateMasterDto.getTemplateId()));
        if (templateDetailOptional.isPresent()) {
            TemplateDetail templateMaster = templateDetailOptional.get();
            TemplateDetail templateDetail = new TemplateDetail();
            templateDetail.setTemplateType(templateMaster.getTemplateType());
            templateDetail.setTemplateName(templateMaster.getTemplateName());
            templateDetail.setId(templateMaster.getId());
            templateDetail.setOrgId(screenTemplateMasterDto.getOrgId());
            templateDetail.setInsertedOn(LocalDateTime.now());
            templateDetailRepository.save(templateDetail);
            return true;
        }
        return false;
    }

    @Override
    public Response findAllTemplateMaster() {
        List<TemplateMaster> templateMasterList = templateMasterRepository.findAll();
        List<TemplateMasterDto> templateMasterDtoList = new ArrayList<>();
        for (TemplateMaster templateMaster : templateMasterList) {
            TemplateMasterDto templateMasterDto = new TemplateMasterDto();
            templateMasterDto.setTemplateId(String.valueOf(templateMaster.getId()));
            templateMasterDto.setTemplateField(templateMaster.getTemplateField());
            templateMasterDto.setTemplateType(templateMaster.getTemplateType());
            templateMasterDto.setTemplateName(templateMaster.getTemplateName());
            templateMasterDto.setIcon(templateMaster.getIcon());
            templateMasterDtoList.add(templateMasterDto);
        }

        return new Response("Transaction completed successfully.", templateMasterDtoList, HttpStatus.OK);
    }

    @Override
    public Response findScreenByOrgId(Long orgId) {
        List<ScreenTemplateMaster> screenTemplateMasterList = screenTemplateMasterRepository.findByOrgId(orgId);
        log.info("Fetching all screens with orgId {}", orgId);
        List<ScreenTemplateMasterDto> screenTemplateMasterDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(screenTemplateMasterList)) {
            for (ScreenTemplateMaster screenTemplateMaster : screenTemplateMasterList) {
                ScreenTemplateMasterDto screenTemplateMasterDto = getScreenTemplateMasterDto(screenTemplateMaster);
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
            if (screenTemplateMasterDto.getSequence() != null)
                screenTemplateMaster.setSequence(screenTemplateMasterDto.getSequence());
            if (screenTemplateMasterDto.getPostScreens() != null)
                screenTemplateMaster.setPostScreens(screenTemplateMasterDto.getPostScreens());
            if (screenTemplateMasterDto.getPreScreens() != null)
                screenTemplateMaster.setPreScreens(screenTemplateMasterDto.getPreScreens());
            if (screenTemplateMasterDto.getScreenName() != null)
                screenTemplateMaster.setScreenName(screenTemplateMasterDto.getScreenName());
            if (screenTemplateMasterDto.getThumbnail() != null)
                screenTemplateMaster.setThumbnail(screenTemplateMasterDto.getThumbnail());
            if (screenTemplateMasterDto.getIsMandatory() != null)
                screenTemplateMaster.setIsMandatory(screenTemplateMasterDto.getIsMandatory());
            if (screenTemplateMasterDto.getIsDisabled() != null)
                screenTemplateMaster.setIsDisabled(screenTemplateMasterDto.getIsDisabled());
            if (screenTemplateMasterDto.getScreenField() != null)
                screenTemplateMaster.setScreenField(screenTemplateMasterDto.getScreenField());
            if (screenTemplateMasterDto.getStatus() != null)
                screenTemplateMaster.setStatus(screenTemplateMasterDto.getStatus());
            screenTemplateMaster.setOrgId(screenTemplateMasterDto.getOrgId());
            if (savingTemplateDetail(screenTemplateMasterDto))
                screenTemplateMasterRepository.save(screenTemplateMaster);
            else return new Response("No such template found", HttpStatus.BAD_REQUEST);
        }
        return new Response("Transaction completed successfully.", HttpStatus.OK);
    }

    @Override
    public Response deleteScreenTemplate(String templateId) {
        List<ScreenTemplateDetails> screenTemplateDetailsList = screenTemplateDetailRepository.findByTemplateId(templateId);
        Optional<TemplateDetail> templateDetailOptional = templateDetailRepository.findById(new ObjectId(templateId));
        if (!CollectionUtils.isEmpty(screenTemplateDetailsList)) {
            screenTemplateDetailRepository.deleteAll(screenTemplateDetailsList);
        }
        if (templateDetailOptional.isPresent()) {
            TemplateDetail templateDetail = templateDetailOptional.get();
            templateDetailRepository.delete(templateDetail);
        }
        return new Response("Screen Templates Deleted Successfully", HttpStatus.OK);
    }

    @Override
    public Response findAllScreensByTemplateId(String objectId, Long orgId) {
        log.info("Fetching all screens with orgId {} and TemplateId {}", orgId, objectId);
        List<ScreenTemplateMaster> screenTemplateMasterList;
        if (orgId != null)
            screenTemplateMasterList = screenTemplateMasterRepository.findByOrgIdAndTemplateId(orgId, new ObjectId(objectId));
        else screenTemplateMasterList = screenTemplateMasterRepository.findByTemplateId(objectId);
        List<ScreenTemplateMasterDto> screenTemplateMasterDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(screenTemplateMasterList)) {
            for (ScreenTemplateMaster screenTemplateMaster : screenTemplateMasterList) {
                ScreenTemplateMasterDto screenTemplateMasterDto = getScreenTemplateMasterDto(screenTemplateMaster);
                screenTemplateMasterDtoList.add(screenTemplateMasterDto);
            }
        }
        return new Response("Transaction completed successfully.", screenTemplateMasterDtoList, HttpStatus.OK);
    }

    private static ScreenTemplateMasterDto getScreenTemplateMasterDto(ScreenTemplateMaster screenTemplateMaster) {
        ScreenTemplateMasterDto screenTemplateMasterDto = new ScreenTemplateMasterDto();
        screenTemplateMasterDto.setTemplateId(screenTemplateMaster.getTemplateId());
        screenTemplateMasterDto.setScreenField(screenTemplateMaster.getScreenField());
        screenTemplateMasterDto.setScreenName(screenTemplateMaster.getScreenName());
        screenTemplateMasterDto.setPostScreens(screenTemplateMaster.getPostScreens());
        screenTemplateMasterDto.setIsDisabled(screenTemplateMaster.getIsDisabled());
        screenTemplateMasterDto.setIsMandatory(screenTemplateMaster.getIsMandatory());
        screenTemplateMasterDto.setThumbnail(screenTemplateMaster.getThumbnail());
        screenTemplateMasterDto.setPreScreens(screenTemplateMaster.getPreScreens());
        screenTemplateMasterDto.setOrgId(screenTemplateMaster.getOrgId());
        screenTemplateMasterDto.setSequence(screenTemplateMaster.getSequence());
        return screenTemplateMasterDto;
    }

    private static ScreenTemplateMasterDto getScreenTemplateDetailDto(ScreenTemplateDetails screenTemplateDetails) {
        ScreenTemplateMasterDto screenTemplateMasterDto = new ScreenTemplateMasterDto();
        screenTemplateMasterDto.setTemplateId(screenTemplateDetails.getTemplateId());
        screenTemplateMasterDto.setScreenField(screenTemplateDetails.getScreenField());
        screenTemplateMasterDto.setScreenName(screenTemplateDetails.getScreenName());
        screenTemplateMasterDto.setPostScreens(screenTemplateDetails.getPostScreens());
        screenTemplateMasterDto.setIsDisabled(screenTemplateDetails.getIsDisabled());
        screenTemplateMasterDto.setIsMandatory(screenTemplateDetails.getIsMandatory());
        screenTemplateMasterDto.setThumbnail(screenTemplateDetails.getThumbnail());
        screenTemplateMasterDto.setPreScreens(screenTemplateDetails.getPreScreens());
        screenTemplateMasterDto.setOrgId(screenTemplateDetails.getOrgId());
        screenTemplateMasterDto.setSequence(screenTemplateDetails.getSequence());
        return screenTemplateMasterDto;
    }

    @Override
    public Response findAllTemplateDetailForOrg(Long orgId) {
        List<TemplateDetail> templateDetailList = templateDetailRepository.findByOrgIdOrderByInsertedOnDesc(orgId);
        List<TemplateMasterDto> templateMasterDtoList = new ArrayList<>();
        for (TemplateDetail templateDetail : templateDetailList) {
            TemplateMasterDto templateMasterDto = getTemplateMasterDto(templateDetail);
            templateMasterDtoList.add(templateMasterDto);
        }
        return new Response("Transaction completed successfully.", templateMasterDtoList, HttpStatus.OK);
    }

    private static TemplateMasterDto getTemplateMasterDto(TemplateDetail templateDetail) {
        TemplateMasterDto templateMasterDto = new TemplateMasterDto();
        templateMasterDto.setTemplateId(String.valueOf(templateDetail.getId()));
        templateMasterDto.setTemplateField(templateDetail.getTemplateField());
        templateMasterDto.setTemplateType(templateDetail.getTemplateType());
        templateMasterDto.setTemplateName(templateDetail.getTemplateName());
        templateMasterDto.setIcon(templateDetail.getIcon());
        templateMasterDto.setInsertedOn(String.valueOf(templateDetail.getInsertedOn()));
        return templateMasterDto;
    }

    @Override
    @Transactional
    public Response saveCustomTemplateDetail(TemplateDetailRequest templateDetailRequest) {
        if (templateDetailRequest.getOrgId() == null)
            return new Response("Organization Id is absent", HttpStatus.BAD_REQUEST);
        TemplateDetail templateDetail = new TemplateDetail();
        templateDetail.setTemplateField(templateDetailRequest.getTemplateField());
        templateDetail.setTemplateName(templateDetailRequest.getTemplateName());
        templateDetail.setTemplateType(templateDetailRequest.getTemplateType());
        templateDetail.setIcon(templateDetailRequest.getIcon());
        templateDetail.setOrgId(templateDetailRequest.getOrgId());
        templateDetail.setInsertedOn(LocalDateTime.now());
        templateDetail = templateDetailRepository.save(templateDetail);
        log.info("TemplateId : {} {}", templateDetail.getId(), templateDetailRequest.getTemplateId());
        if (templateDetailRequest.getTemplateId() != null) {
            List<ScreenTemplateMaster> screenTemplateMasterList = screenTemplateMasterRepository.findByTemplateId(templateDetailRequest.getTemplateId());
            List<ScreenTemplateDetails> screenTemplateMasterDtoList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(screenTemplateMasterList)) {
                for (ScreenTemplateMaster screenTemplateMaster : screenTemplateMasterList) {
                    ScreenTemplateDetails screenTemplateDetails = getScreenTemplateDetails(screenTemplateMaster);
                    screenTemplateDetails.setTemplateId(String.valueOf(templateDetail.getId()));
                    screenTemplateMasterDtoList.add(screenTemplateDetails);
                    screenTemplateDetailRepository.save(screenTemplateDetails);
                }
            }
        }
        return new Response("Transaction completed successfully.", String.valueOf(templateDetail.getId()), HttpStatus.OK);
    }

    private static ScreenTemplateDetails getScreenTemplateDetails(ScreenTemplateMaster screenTemplateMaster) {
        ScreenTemplateDetails screenTemplateDetails = new ScreenTemplateDetails();
        screenTemplateDetails.setScreenField(screenTemplateMaster.getScreenField());
        screenTemplateDetails.setScreenName(screenTemplateMaster.getScreenName());
        screenTemplateDetails.setPostScreens(screenTemplateMaster.getPostScreens());
        screenTemplateDetails.setIsDisabled(screenTemplateMaster.getIsDisabled());
        screenTemplateDetails.setIsMandatory(screenTemplateMaster.getIsMandatory());
        screenTemplateDetails.setThumbnail(screenTemplateMaster.getThumbnail());
        screenTemplateDetails.setPreScreens(screenTemplateMaster.getPreScreens());
        screenTemplateDetails.setOrgId(screenTemplateMaster.getOrgId());
        screenTemplateDetails.setSequence(screenTemplateMaster.getSequence());
        screenTemplateDetails.setFieldsMap(screenTemplateMaster.getFieldsMap());
        return screenTemplateDetails;
    }

    @Override
    public Response getScreenTemplateDetail(String screenId, Long orgId) {
        log.info("Fetching all screens with orgId {} and screenId {}", orgId, screenId);
        Optional<ScreenTemplateDetails> screenTemplateDetailsOptional = screenTemplateDetailRepository.findByOrgIdAndScreenTemplateDetailId(orgId, new ObjectId(screenId));
        ScreenTemplateDetailsDto screenTemplateDetailsDto = new ScreenTemplateDetailsDto();
        if (screenTemplateDetailsOptional.isPresent()) {
            ScreenTemplateDetails screenTemplateDetails = screenTemplateDetailsOptional.get();
            getScreenTemplateDetailsDto(screenTemplateDetailsDto, screenTemplateDetails);
        }
        return new Response("Transaction completed successfully.", screenTemplateDetailsDto, HttpStatus.OK);
    }

    private static ScreenTemplateDetailsDto getScreenTemplateDetailsDto(ScreenTemplateDetailsDto screenTemplateDetailsDto, ScreenTemplateDetails screenTemplateDetails) {
        screenTemplateDetailsDto.setId(screenTemplateDetails.getScreenTemplateDetailId().toHexString());
        screenTemplateDetailsDto.setTemplateId(screenTemplateDetails.getTemplateId());
        screenTemplateDetailsDto.setScreenField(screenTemplateDetails.getScreenField());
        screenTemplateDetailsDto.setScreenName(screenTemplateDetails.getScreenName());
        screenTemplateDetailsDto.setPostScreens(screenTemplateDetails.getPostScreens());
        screenTemplateDetailsDto.setIsDisabled(screenTemplateDetails.getIsDisabled());
        screenTemplateDetailsDto.setIsMandatory(screenTemplateDetails.getIsMandatory());
        screenTemplateDetailsDto.setThumbnail(screenTemplateDetails.getThumbnail());
        screenTemplateDetailsDto.setPreScreens(screenTemplateDetails.getPreScreens());
        screenTemplateDetailsDto.setOrgId(screenTemplateDetails.getOrgId());
        screenTemplateDetailsDto.setSequence(screenTemplateDetails.getSequence());
        screenTemplateDetailsDto.setFieldsMap(screenTemplateDetails.getFieldsMap());
        return screenTemplateDetailsDto;
    }

    @Override
    public Response updateScreenTemplateDetail(ScreenTemplateDetailsDto screenTemplateDetailsDto) {
        //check if already there is a list of screen templates available for logged-in user's orgID
        ScreenTemplateDetails screenTemplateDetails;
        Optional<ScreenTemplateDetails> screenTemplateDetailsOptional = screenTemplateDetailRepository.findByScreenTemplateDetailId(String.valueOf(screenTemplateDetailsDto.getId()));
        if (screenTemplateDetailsOptional.isPresent()) {
            log.info("update request received for screenId {}", screenTemplateDetailsDto.getId());
            screenTemplateDetails = screenTemplateDetailsOptional.get();
            updateScreenTemplateDetails(screenTemplateDetailsDto, screenTemplateDetails);
        } else {
            screenTemplateDetails = new ScreenTemplateDetails();
            updateScreenTemplateDetails(screenTemplateDetailsDto, screenTemplateDetails);
        }
        return new Response("Transaction completed successfully.", screenTemplateDetails.getScreenTemplateDetailId().toHexString(), HttpStatus.OK);
    }

    @Override
    public Response saveScreenDetailsTemplate(TemplateScreenRequest templateScreenRequest) {
/*        List<ScreenTemplateDetails> screenTemplateDetailsList = screenTemplateDetailRepository.findByOrgIdOrderBySequenceAsc(templateScreenRequest.getOrgId());
        if (!CollectionUtils.isEmpty(screenTemplateDetailsList)) {
            log.info("already screens and template saved for the current orgId");
            updateTemplateScreenMasterDetails(templateScreenRequest, screenTemplateDetailsList);
        }*/
        List<String> screenDetailsIdList = new ArrayList<>();
        List<ScreenTemplateMasterDto> screenTemplateMasterDtoList = templateScreenRequest.getScreenTemplateMasterDtoList();
        for (ScreenTemplateMasterDto screenTemplateMasterDto : screenTemplateMasterDtoList) {
            ScreenTemplateDetails screenTemplateDetails = getScreenTemplateDetails(screenTemplateMasterDto);
            screenTemplateDetails = screenTemplateDetailRepository.save(screenTemplateDetails);
            screenDetailsIdList.add(screenTemplateDetails.getScreenTemplateDetailId().toHexString());
            if (!savingTemplateDetail(screenTemplateMasterDto))
                return new Response("No such template found", HttpStatus.BAD_REQUEST);
        }
        return new Response("Transaction completed successfully.", screenDetailsIdList, HttpStatus.OK);
    }

    @Override
    public Response updateTemplateDetail(TemplateScreenRequest templateScreenRequest) {
        List<ScreenTemplateMasterDto> screenTemplateMasterDtoList = templateScreenRequest.getScreenTemplateMasterDtoList();
        Optional<TemplateDetail> templateDetailOptional = templateDetailRepository.findById(new ObjectId(templateScreenRequest.getTemplateId()));
        List<ScreenTemplateDetails> screenTemplateDetailsList = screenTemplateDetailRepository.findByTemplateId(templateScreenRequest.getTemplateId());
        if (!CollectionUtils.isEmpty(screenTemplateDetailsList)) {
            screenTemplateDetailRepository.deleteAll(screenTemplateDetailsList);
        }
        TemplateDetail templateDetail;
        if (templateDetailOptional.isPresent()) {
            templateDetail = templateDetailOptional.get();
            if (StringUtils.hasText(templateScreenRequest.getTemplateName()))
                templateDetail.setTemplateName(templateScreenRequest.getTemplateName());
            templateDetailRepository.save(templateDetail);
        }
        for (ScreenTemplateMasterDto screenTemplateMasterDto : screenTemplateMasterDtoList) {
            ScreenTemplateDetails screenTemplateDetails = createNewScreenTemplateDetail(screenTemplateMasterDto);
            screenTemplateDetailRepository.save(screenTemplateDetails);
        }
        return new Response("Transaction completed successfully.", HttpStatus.OK);

    }

    private ScreenTemplateDetails createNewScreenTemplateDetail(ScreenTemplateMasterDto screenTemplateMasterDto) {
        ScreenTemplateDetails screenTemplateDetails = new ScreenTemplateDetails();
        if (screenTemplateMasterDto.getSequence() != null)
            screenTemplateDetails.setSequence(screenTemplateMasterDto.getSequence());
        if (screenTemplateMasterDto.getPostScreens() != null)
            screenTemplateDetails.setPostScreens(screenTemplateMasterDto.getPostScreens());
        if (screenTemplateMasterDto.getPreScreens() != null)
            screenTemplateDetails.setPreScreens(screenTemplateMasterDto.getPreScreens());
        if (screenTemplateMasterDto.getScreenName() != null)
            screenTemplateDetails.setScreenName(screenTemplateMasterDto.getScreenName());
        if (screenTemplateMasterDto.getThumbnail() != null)
            screenTemplateDetails.setThumbnail(screenTemplateMasterDto.getThumbnail());
        if (screenTemplateMasterDto.getIsMandatory() != null)
            screenTemplateDetails.setIsMandatory(screenTemplateMasterDto.getIsMandatory());
        if (screenTemplateMasterDto.getIsDisabled() != null)
            screenTemplateDetails.setIsDisabled(screenTemplateMasterDto.getIsDisabled());
        if (screenTemplateMasterDto.getScreenField() != null)
            screenTemplateDetails.setScreenField(screenTemplateMasterDto.getScreenField());
        if (screenTemplateMasterDto.getOrgId() != null)
            screenTemplateDetails.setOrgId(screenTemplateMasterDto.getOrgId());
        if (screenTemplateMasterDto.getTemplateId() != null)
            screenTemplateDetails.setTemplateId(screenTemplateMasterDto.getTemplateId());
        return screenTemplateDetails;

    }

    private void updateScreenTemplateDetails(ScreenTemplateDetailsDto screenTemplateDetailsDto, ScreenTemplateDetails screenTemplateDetails) {
        if (screenTemplateDetailsDto.getSequence() > 0)
            screenTemplateDetails.setSequence(screenTemplateDetailsDto.getSequence());
        if (screenTemplateDetailsDto.getPostScreens() != null)
            screenTemplateDetails.setPostScreens(screenTemplateDetailsDto.getPostScreens());
        if (screenTemplateDetailsDto.getPreScreens() != null)
            screenTemplateDetails.setPreScreens(screenTemplateDetailsDto.getPreScreens());
        if (screenTemplateDetailsDto.getScreenName() != null)
            screenTemplateDetails.setScreenName(screenTemplateDetailsDto.getScreenName());
        if (screenTemplateDetailsDto.getThumbnail() != null)
            screenTemplateDetails.setThumbnail(screenTemplateDetailsDto.getThumbnail());
        if (screenTemplateDetailsDto.getIsMandatory() != null)
            screenTemplateDetails.setIsMandatory(screenTemplateDetailsDto.getIsMandatory());
        if (screenTemplateDetailsDto.getIsDisabled() != null)
            screenTemplateDetails.setIsDisabled(screenTemplateDetailsDto.getIsDisabled());
        if (screenTemplateDetailsDto.getScreenField() != null)
            screenTemplateDetails.setScreenField(screenTemplateDetailsDto.getScreenField());
        if (screenTemplateDetailsDto.getStatus() != null)
            screenTemplateDetails.setStatus(screenTemplateDetailsDto.getStatus());
        if (screenTemplateDetailsDto.getFieldsMap() != null)
            screenTemplateDetails.setFieldsMap(screenTemplateDetailsDto.getFieldsMap());
        if (screenTemplateDetailsDto.getTemplateId() != null)
            screenTemplateDetails.setTemplateId(screenTemplateDetailsDto.getTemplateId());
        screenTemplateDetailRepository.save(screenTemplateDetails);
    }

    public Response updateTemplateScreenMasterDetails(TemplateScreenRequest templateScreenRequest, List<ScreenTemplateDetails> screenTemplateDetailsList) {
        int i = 0;
        List<ScreenTemplateMasterDto> screenTemplateMasterDtoList = templateScreenRequest.getScreenTemplateMasterDtoList();
        for (ScreenTemplateMasterDto screenTemplateMasterDto : screenTemplateMasterDtoList) {
            ScreenTemplateDetails screenTemplateDetails = screenTemplateDetailsList.get(i++);
            if (screenTemplateMasterDto.getSequence() != null)
                screenTemplateDetails.setSequence(screenTemplateMasterDto.getSequence());
            if (screenTemplateMasterDto.getPostScreens() != null)
                screenTemplateDetails.setPostScreens(screenTemplateMasterDto.getPostScreens());
            if (screenTemplateMasterDto.getPreScreens() != null)
                screenTemplateDetails.setPreScreens(screenTemplateMasterDto.getPreScreens());
            if (screenTemplateMasterDto.getScreenName() != null)
                screenTemplateDetails.setScreenName(screenTemplateMasterDto.getScreenName());
            if (screenTemplateMasterDto.getThumbnail() != null)
                screenTemplateDetails.setThumbnail(screenTemplateMasterDto.getThumbnail());
            if (screenTemplateMasterDto.getIsMandatory() != null)
                screenTemplateDetails.setIsMandatory(screenTemplateMasterDto.getIsMandatory());
            if (screenTemplateMasterDto.getIsDisabled() != null)
                screenTemplateDetails.setIsDisabled(screenTemplateMasterDto.getIsDisabled());
            if (screenTemplateMasterDto.getScreenField() != null)
                screenTemplateDetails.setScreenField(screenTemplateMasterDto.getScreenField());
            if (screenTemplateMasterDto.getStatus() != null)
                screenTemplateDetails.setStatus(screenTemplateMasterDto.getStatus());
            screenTemplateDetails.setOrgId(screenTemplateMasterDto.getOrgId());
            if (savingTemplateDetail(screenTemplateMasterDto))
                screenTemplateDetailRepository.save(screenTemplateDetails);
            else return new Response("No such template found", HttpStatus.BAD_REQUEST);
        }
        return new Response("Transaction completed successfully.", HttpStatus.OK);
    }

    @Override
    public Response findAllScreensDetailsByTemplateId(String templateId, Long orgId) {
        log.info("Fetching all screens with orgId {} and TemplateId {}", orgId, templateId);
        List<ScreenTemplateDetails> screenTemplateDetailsList;
        if (orgId != null)
            screenTemplateDetailsList = screenTemplateDetailRepository.findByOrgIdAndTemplateId(orgId, templateId);
        else screenTemplateDetailsList = screenTemplateDetailRepository.findByTemplateId(templateId);
        List<ScreenTemplateMasterDto> screenTemplateMasterDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(screenTemplateDetailsList)) {
            for (ScreenTemplateDetails screenTemplateDetails : screenTemplateDetailsList) {
                ScreenTemplateMasterDto screenTemplateMasterDto = getScreenTemplateDetailDto(screenTemplateDetails);
                screenTemplateMasterDtoList.add(screenTemplateMasterDto);
            }
        }
        return new Response("Transaction completed successfully.", screenTemplateMasterDtoList, HttpStatus.OK);
    }
}