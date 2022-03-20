package com.project.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto  {
    private String name;
    private Long categoryId;
    private Long measurementId;
//    private final  Long photo_id;
//    private Long code;
}
