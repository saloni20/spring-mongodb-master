package com.spring.mongo.api.resource.config;

import com.spring.mongo.api.entity.DatabaseSequence;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class SequenceGeneratorService {

    private final MongoOperations mongoOperations;

    public Long generateSequence(String seqName) {
        DatabaseSequence counter = mongoOperations.findAndModify(Query.query(Criteria.where("_id").is(seqName)), new Update().inc("seq", 1), FindAndModifyOptions.options().returnNew(true).upsert(true), DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }

    public Long generateSequenceForOrganization(Integer organizationId) {
        String seqName = "user_sequence_" + organizationId;
        return generateSequence(seqName);
    }
}