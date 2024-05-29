package com.spring.mongo.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RowData {
    private int row;
    private List<ColumnData> columns;
}