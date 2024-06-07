package com.spring.mongo.api.resource.config;

import com.spring.mongo.api.entity.UserMaster;
import com.spring.mongo.api.resource.serviceImpl.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class UserModelListener extends AbstractMongoEventListener<UserMaster> {

    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public UserModelListener(SequenceGeneratorService sequenceGeneratorService) {
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

//    @Override
//    public void onBeforeConvert(BeforeConvertEvent<UserMaster> event) {
//        UserMaster user = event.getSource();
//        if (user.getUserMasterPK().getUserId() == null) {
//            user.getUserMasterPK().setUserId(Math.toIntExact(sequenceGeneratorService.generateSequenceForOrganization(Math.toIntExact(user.getUserMasterPK().getOrgId()))));
//        }
//    }
}