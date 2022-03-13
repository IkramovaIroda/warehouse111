package com.project.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputDto  {
    @NotEmpty
    private String date;
    @NotNull
    private Long warehouseId;
    @NotNull
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
