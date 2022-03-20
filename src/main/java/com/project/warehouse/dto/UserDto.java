package com.project.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {

    private boolean active;
    private Long code;
    private String first_name;
    private String last_name;
    private String password;
    private String phone_number;
    private Long warehouseId;
}
