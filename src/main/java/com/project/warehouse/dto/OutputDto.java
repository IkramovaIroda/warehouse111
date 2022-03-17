package com.project.warehouse.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class OutputDto implements Serializable {
    private final Long currencyId;
    private final LocalDate date;
    private final Integer factureNumber;
    private final Long warehouseId;
    private final Long clientId;
    private List<OutputProductDto> outputProductDtoList;

    public OutputDto(Long currencyId, LocalDate date, Integer factureNumber, Long warehouseId, Long clientId) {
        this.currencyId = currencyId;
        this.date = date;
        this.factureNumber = factureNumber;
        this.warehouseId = warehouseId;
        this.clientId = clientId;
    }
}
