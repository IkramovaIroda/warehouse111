package com.project.warehouse.dto;

import lombok.Data;

import java.util.List;

@Data
public class OutputDto {
    private Long currencyId;
    private String date;
    private Integer factureNumber;
    private Long warehouseId;
    private Long clientId;
    private List<OutputProductDto> productList;

    public OutputDto(Long currencyId, String date, Integer factureNumber, Long warehouseId, Long clientId) {
        this.currencyId = currencyId;
        this.date = date;
        this.factureNumber = factureNumber;
        this.warehouseId = warehouseId;
        this.clientId = clientId;
    }
}
