package com.spring.mongo.api.resource.serviceImpl;
import com.spring.mongo.api.entity.ScreenTemplateMaster;
import com.spring.mongo.api.entity.TemplateDetail;
import com.spring.mongo.api.entity.TemplateMaster;
import com.spring.mongo.api.repository.ScreenTemplateMasterRepository;
import com.spring.mongo.api.repository.TemplateDetailRepository;
import com.spring.mongo.api.repository.TemplateMasterRepository;
import com.spring.mongo.api.resource.dto.ScreenTemplateMasterDto;
import com.spring.mongo.api.resource.request.TemplateScreenRequest;
import com.spring.mongo.api.resource.response.Response;
import com.spring.mongo.api.resource.service.TemplateService;
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
public class TemplateServiceImpl implements TemplateService {

    private final ScreenTemplateMasterRepository screenTemplateMasterRepository;
    private final TemplateDetailRepository templateDetailRepository;
    private final TemplateMasterRepository templateMasterRepository;

    public TemplateServiceImpl(ScreenTemplateMasterRepository screenTemplateMasterRepository, TemplateDetailRepository templateDetailRepository, TemplateMasterRepository templateMasterRepository) {
        this.screenTemplateMasterRepository = screenTemplateMasterRepository;
        this.templateDetailRepository = templateDetailRepository;
        this.templateMasterRepository = templateMasterRepository;
    }

    @Override
    public Response saveScreenMasterTemplateInformation(TemplateScreenRequest templateScreenRequest) {

         //check if already there is a list of screen templates available for logged in user's orgID
        List<ScreenTemplateMaster> screenTemplateMasters = screenTemplateMasterRepository.findByOrgId(templateScreenRequest.getOrgId());
        if(!CollectionUtils.isEmpty(screenTemplateMasters)) {
            log.info("already screens and template saved for the current orgId");
            updateTemplateScreenMaster(templateScreenRequest,screenTemplateMasters);
        }
        List<ScreenTemplateMasterDto> screenTemplateMasterDtoList = templateScreenRequest.getScreenTemplateMasterDtoList();
        for(ScreenTemplateMasterDto screenTemplateMasterDto : screenTemplateMasterDtoList) {
            ScreenTemplateMaster    screenTemplateMaster = new ScreenTemplateMaster();
                screenTemplateMaster.setSequence(screenTemplateMasterDto.getSequence());
                screenTemplateMaster.setPostScreens(screenTemplateMasterDto.getPostScreens());
                screenTemplateMaster.setPreScreens(screenTemplateMasterDto.getPreScreens());
                screenTemplateMaster.setScreenName(screenTemplateMasterDto.getScreenName());
                screenTemplateMaster.setThumbnail(screenTemplateMasterDto.getThumbnail());
                screenTemplateMaster.setIsMandatory(screenTemplateMasterDto.getIsMandatory());
                screenTemplateMaster.setIsDisabled(screenTemplateMasterDto.getIsDisabled());
                screenTemplateMaster.setScreenField(screenTemplateMasterDto.getScreenField());
                screenTemplateMaster.setTemplateId(new ObjectId(screenTemplateMasterDto.getTemplateId()));
                if(savingTemplateDetail(screenTemplateMasterDto))
                    screenTemplateMaster = screenTemplateMasterRepository.save(screenTemplateMaster);
                else
                    return new Response("No such template found", HttpStatus.BAD_REQUEST);

        }
        return new Response("Records Saved Successfully:",HttpStatus.OK);
    }

    private boolean savingTemplateDetail(ScreenTemplateMasterDto screenTemplateMasterDto) {
        Optional<TemplateMaster> templateMasterOptional = templateMasterRepository.findById(new ObjectId(screenTemplateMasterDto.getTemplateId()));
        if (templateMasterOptional.isPresent()) {
            TemplateMaster templateMaster =  templateMasterOptional.get();
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
        return new Response("Transaction Successful",templateMasterRepository.findAll(),HttpStatus.OK);
    }

    @Override
    public Response findScreenByOrgId(Integer orgId) {
        List<ScreenTemplateMaster> screenTemplateMasterList = screenTemplateMasterRepository.findByOrgId(orgId);
        List<ScreenTemplateMasterDto> screenTemplateMasterDtoList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(screenTemplateMasterDtoList)) {
            for(ScreenTemplateMaster s:screenTemplateMasterList) {
                ScreenTemplateMasterDto screenTemplateMasterDto= new ScreenTemplateMasterDto();
                screenTemplateMasterDto.setTemplateId(s.getTemplateId().toHexString());
                screenTemplateMasterDto.setScreenField(s.getScreenField());
                screenTemplateMasterDto.setScreenName(s.getScreenName());
                screenTemplateMasterDto.setPostScreens(s.getPostScreens());
                screenTemplateMasterDto.setIsDisabled(s.getIsDisabled());
                screenTemplateMasterDto.setIsMandatory(s.getIsMandatory());
                screenTemplateMasterDto.setThumbnail(s.getThumbnail());
                screenTemplateMasterDto.setPreScreens(s.getPreScreens());
                screenTemplateMasterDtoList.add(screenTemplateMasterDto);
            }
        }
        return new Response("Transaction Successful",screenTemplateMasterDtoList,HttpStatus.OK);
    }

    @Override
    public Response updateTemplateScreenMaster(TemplateScreenRequest templateScreenRequest,List<ScreenTemplateMaster> screenTemplateMasterList) {
        int i=0;
        List<ScreenTemplateMasterDto> screenTemplateMasterDtoList = templateScreenRequest.getScreenTemplateMasterDtoList();
        for(ScreenTemplateMasterDto screenTemplateMasterDto : screenTemplateMasterDtoList) {
             ScreenTemplateMaster  screenTemplateMaster = screenTemplateMasterList.get(i++);
            screenTemplateMaster.setSequence(screenTemplateMasterDto.getSequence());
            screenTemplateMaster.setPostScreens(screenTemplateMasterDto.getPostScreens());
            screenTemplateMaster.setPreScreens(screenTemplateMasterDto.getPreScreens());
            screenTemplateMaster.setScreenName(screenTemplateMasterDto.getScreenName());
            screenTemplateMaster.setThumbnail(screenTemplateMasterDto.getThumbnail());
            screenTemplateMaster.setIsMandatory(screenTemplateMasterDto.getIsMandatory());
            screenTemplateMaster.setIsDisabled(screenTemplateMasterDto.getIsDisabled());
            screenTemplateMaster.setScreenField(screenTemplateMasterDto.getScreenField());
            if(savingTemplateDetail(screenTemplateMasterDto))
                screenTemplateMaster = screenTemplateMasterRepository.save(screenTemplateMaster);
            else
                return new Response("No such template found", HttpStatus.BAD_REQUEST);
        }
        return new Response("Screens Template Updated Successfully", HttpStatus.OK);
    }

    @Override
    public Response deleteTemplateScreenById(Integer id) {
        return null;
    }
}
