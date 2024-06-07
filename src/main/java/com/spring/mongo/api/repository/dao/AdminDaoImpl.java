package com.spring.mongo.api.repository.dao;

import com.spring.mongo.api.entity.UserMaster;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class AdminDaoImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public Integer findMaxUserId() {
        List<UserMaster> usersMasters = entityManager.createQuery("SELECT userId FROM UserMaster", UserMaster.class).getResultList();
        Optional<Integer> maxUserId = usersMasters.stream().map(userMaster -> userMaster.getUserMasterPK().getUserId())
                .max(Long::compare);
        return maxUserId.orElse(null);
    }
}