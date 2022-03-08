package com.project.warehouse.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InputProductDto implements Serializable {
    private final Integer productId;
    private final BigDecimal amount;
    private final BigDecimal price;
    private final LocalDate expireDate;
    private final Long inputId;
}
