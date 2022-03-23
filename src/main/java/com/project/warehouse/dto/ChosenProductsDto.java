package com.project.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChosenProductsDto{
    private Long currencyId;
    private String date;
    private Integer factureNumber;
    private Long warehouseId;
    private Long clientId;
    private List<Long> productList;
}
