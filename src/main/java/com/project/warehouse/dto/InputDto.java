package com.project.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputDto {
    private String date;
    private Long warehouseId;
    private Long supplierId;
    private Long currencyId;
    private Integer factureNumber;
    private List<InputProductDto> inputProducts;

    public InputDto(String date, Long warehouseId, Long supplierId, Long currencyId, Integer factureNumber) {
        this.date = date;
        this.warehouseId = warehouseId;
        this.supplierId = supplierId;
        this.currencyId = currencyId;
        this.factureNumber = factureNumber;
    }
}
