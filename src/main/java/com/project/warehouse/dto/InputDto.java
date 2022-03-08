package com.project.warehouse.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class InputDto implements Serializable {
    private final LocalDate date;
    private final Long warehouseId;
    private final Long supplierId;
    private final Long currencyId;
    private final Integer factureNumber;
}
