package com.spring.mongo.api.Security;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class ApiServiceImpl {

    private final RestTemplate restTemplate;

    public String makeGetRequest(String url) {
        return restTemplate.getForObject(url, String.class);
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String PORT = "8080";

    public String makePostRequest(String body, Map<String, String> headersMap) throws Exception {
        DecryptionServiceImpl decryptionService = new DecryptionServiceImpl();
        String requestData = decryptionService.decrypt(body);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        CommonRequest requestObject = objectMapper.readValue(requestData, CommonRequest.class);

        HttpHeaders headers = new HttpHeaders();
        for (Map.Entry<String, String> entry : headersMap.entrySet()) {
            headers.set(entry.getKey(), entry.getValue());
        }
        headers.set("Content-Type", "application/json");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(requestObject.getData(), headers);
        log.info("Request  http://localhost:{}", PORT + requestObject.getId());
        HttpMethod method;
        if (requestObject.getMethod().equals("GET")) {
            method = HttpMethod.valueOf("GET");
        } else {
            method = HttpMethod.valueOf("POST");
        }
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + PORT + requestObject.getId(), method, entity, String.class);
        return response.getBody();
    }
}