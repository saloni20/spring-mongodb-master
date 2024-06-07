package com.spring.mongo.api.resource.config;

import com.spring.mongo.api.entity.UserMaster;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserModelListener extends AbstractMongoEventListener<UserMaster> {

    private final SequenceGeneratorService sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<UserMaster> event) {
        UserMaster user = event.getSource();
        if (user.getUserMasterPK().getUserId() == null) {
            user.getUserMasterPK().setUserId(Math.toIntExact(sequenceGenerator.generateSequenceForOrganization(Math.toIntExact(user.getUserMasterPK().getOrgId()))));
        }
    }
}