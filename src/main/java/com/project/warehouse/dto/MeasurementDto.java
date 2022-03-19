package com.project.warehouse.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MeasurementDto implements Serializable {
    private final Boolean active;
    private final String name;
}
