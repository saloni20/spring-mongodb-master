package com.spring.mongo.api.resource.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class DatabaseSequence {

    @Id
    private String id;

    private long seq;

    @Override
    public String toString() {
        return "DatabaseSequence [id=" + id + ", seq=" + seq + "]";
    }

}