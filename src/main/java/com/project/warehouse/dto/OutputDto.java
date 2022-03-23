package com.project.warehouse.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OutputDto implements Serializable {
    private final Long currencyId;
    private final String date;
    private final Integer factureNumber;
    private final Long warehouseId;
    private final Long clientId;
    private List<OutputProductDto> productList;

    public OutputDto(Long currencyId, String date, Integer factureNumber, Long warehouseId, Long clientId) {
        this.currencyId = currencyId;
        this.date = date;
        this.factureNumber = factureNumber;
        this.warehouseId = warehouseId;
        this.clientId = clientId;
    }
}
