package com.project.warehouse.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class OutputDto implements Serializable {
    private final Long currencyId;
    private final LocalDate date;
    private final Integer factureNumber;
    private final Long warehouseId;
    private final Long clientId;
}
