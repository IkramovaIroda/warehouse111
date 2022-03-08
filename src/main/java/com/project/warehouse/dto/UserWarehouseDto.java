package com.project.warehouse.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserWarehouseDto implements Serializable {
    private final Long userId;
    private final Long warehouseId;
}
