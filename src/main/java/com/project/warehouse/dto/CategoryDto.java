package com.project.warehouse.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryDto {
    private String name;
    private String parentCategoryId;
}
