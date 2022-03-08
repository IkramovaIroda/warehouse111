package com.project.warehouse.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OutputProductDto implements Serializable {
    private final Long productId;
    private final BigDecimal amount;
    private final BigDecimal price;
    private final Long outputId;
}
