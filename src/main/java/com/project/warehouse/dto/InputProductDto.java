package com.project.warehouse.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InputProductDto implements Serializable {
    private final Long productId;
    private final Double amount;
    private final Double price;
    private final LocalDate expireDate;
    private final Long inputId;
}
