package com.project.warehouse.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class InputDto implements Serializable {
    private final LocalDate date;
    private final Long warehouseId;
    private final Long supplierId;
    private final Long currencyId;
    private final Integer factureNumber;
    private List<InputProductDto> inputProductDtoList;

    public InputDto(LocalDate date, Long warehouseId, Long supplierId, Long currencyId, Integer factureNumber) {
        this.date = date;
        this.warehouseId = warehouseId;
        this.supplierId = supplierId;
        this.currencyId = currencyId;
        this.factureNumber = factureNumber;
    }
}
