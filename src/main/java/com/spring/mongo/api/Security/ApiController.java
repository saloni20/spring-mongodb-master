package com.spring.mongo.api.Security;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/invoke")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ApiController {

    private final ApiServiceImpl apiService;

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