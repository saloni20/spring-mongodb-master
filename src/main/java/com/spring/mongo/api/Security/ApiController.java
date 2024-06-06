package com.spring.mongo.api.Security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/invoke")
@CrossOrigin(origins = "*")
public class ApiController {

    @Autowired
    private ApiServiceImpl apiService;

    @GetMapping("/get")
    public String getApiResponse(@RequestBody String url) {
        return apiService.makeGetRequest(url);
    }

    @PostMapping
    public String postApiResponse(@RequestBody String body, HttpServletRequest request) throws Exception {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        return apiService.makePostRequest(body, headers);
    }
}
