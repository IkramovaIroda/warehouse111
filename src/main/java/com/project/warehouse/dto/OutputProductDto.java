package com.project.warehouse.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OutputProductDto{
    private Double amount;
    private Long inputId;
}
