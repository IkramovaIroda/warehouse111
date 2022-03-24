package com.project.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private String first_name;
    private String last_name;
    private String password, password_repeat;
    private String phone_number;
    private List<Long> warehouses=new ArrayList<>();
}
