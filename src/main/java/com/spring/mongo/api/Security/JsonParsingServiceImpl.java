package com.spring.mongo.api.Security;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;

@Service
public class JsonParsingServiceImpl {
    private final ObjectMapper objectMapper;

    public JsonParsingServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public CommonRequest parseJson(String jsonString) throws Exception {
        return objectMapper.readValue(jsonString, CommonRequest.class);
    }
}