package com.spring.mongo.api.Security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Service
public class ApiServiceImpl {

    @Autowired
    private RestTemplate restTemplate;

    public String makeGetRequest(String url) {
        return restTemplate.getForObject(url, String.class);
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static String PORT = "8080";

    public String makePostRequest(String body, Map<String, String> headersMap) throws Exception {
        DecryptionServiceImpl decryptionService = new DecryptionServiceImpl();
        String requestData = decryptionService.decrypt(body);

        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        CommonRequest requestObject = objectMapper.readValue(requestData, CommonRequest.class);

        HttpHeaders headers = new HttpHeaders();
        // headersMap.forEach(headers::set);
        for (Map.Entry<String, String> entry : headersMap.entrySet()) {
            headers.set(entry.getKey(), entry.getValue());
        }
        headers.set("Content-Type", "application/json");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(requestObject.getData(), headers);
        System.out.println("reques@@@:::::::::::  http://localhost:" + PORT + requestObject.getId());

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + PORT + requestObject.getId(),
                HttpMethod.POST, entity, String.class);
        return response.getBody();
    }
}
