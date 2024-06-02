package com.spring.mongo.api.resource.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.mongo.api.resource.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    public JwtAuthenticationEntryPoint() {
        super();
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        if (e instanceof BadCredentialsException || e instanceof UsernameNotFoundException) {
            if (e.getMessage().toLowerCase().contains("password")) {
                Response wrongPasswordResponse = new Response("Incorrect password.", HttpStatus.UNAUTHORIZED);
                returnResponse(httpServletResponse, wrongPasswordResponse);
            } else if (e.getMessage().contains("User")) {
                Response wrongUsernameResponse = new Response("Please provide Registered Mail id.", HttpStatus.UNAUTHORIZED);
                returnResponse(httpServletResponse, wrongUsernameResponse);
            }
        } else {
            Response genericResponse = new Response("Authentication failed.", HttpStatus.UNAUTHORIZED);
            returnResponse(httpServletResponse, genericResponse);
        }
    }

    private void returnResponse(HttpServletResponse httpServletResponse, Response response) throws IOException {

        httpServletResponse.setStatus(response.getStatus().value());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(response));
    }
}