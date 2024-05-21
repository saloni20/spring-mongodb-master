package com.spring.mongo.api.resource.response;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author Ajay
 */
@NoArgsConstructor
public class Response {

    private int code;
    private String message;
    private HttpStatus status;
    private Object data;
    private Long totalCount;

    public Response(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
        this.code = status.value();
    }

    public Response(String message, Object responseObject, HttpStatus status) {
        this.message = message;
        this.status = status;
        this.code = status.value();
        this.data = responseObject;
    }

    public Response(String message, Object data, Long totalCount, HttpStatus status) {
        this.code = status.value();
        this.message = message;
        this.status = status;
        this.data = data;
        this.totalCount = totalCount;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}