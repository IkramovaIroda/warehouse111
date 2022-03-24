package com.project.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputProductDto {
    private Long productId;
    private Double amount;
    private Double price;
    private String expireDate;
    private Long inputProductId;
}
