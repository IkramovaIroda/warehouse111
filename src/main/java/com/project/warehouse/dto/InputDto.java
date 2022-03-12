package com.project.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputDto  {
    private LocalDate date;
    private Long warehouseId;
    private Long supplierId;
    private Long currencyId;
    private Integer factureNumber;
    private List<InputProductDto> inputProducts;

    public InputDto(LocalDate date, Long warehouseId, Long supplierId, Long currencyId, Integer factureNumber) {
        this.date = date;
        this.warehouseId = warehouseId;
        this.supplierId = supplierId;
        this.currencyId = currencyId;
        this.factureNumber = factureNumber;
    }
}
