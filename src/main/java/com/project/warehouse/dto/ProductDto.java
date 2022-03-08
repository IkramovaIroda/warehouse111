package com.project.warehouse.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDto implements Serializable {
    private final Boolean active;
    private final String name;
    private final Long categoryId;
    private final Long measurementId;
}
