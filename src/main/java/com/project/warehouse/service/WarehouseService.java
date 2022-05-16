package com.project.warehouse.service;

import com.project.warehouse.dto.ApiResponse;
import com.project.warehouse.dto.WarehouseDto;
import com.project.warehouse.entity.Warehouse;
import com.project.warehouse.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    final
    WarehouseRepository warehouseRepository;

    public ApiResponse add(Warehouse warehouse) {
        Warehouse save = warehouseRepository.save(warehouse);
        return new ApiResponse("Saved", true, save);
    }

    public ApiResponse edit(Long id, WarehouseDto warehouseDto) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
        Warehouse warehouse = optionalWarehouse.get();
        warehouse.setName(warehouseDto.getName());
        warehouseRepository.save(warehouse);
        return new ApiResponse("Updated!", true);
    }

    public ApiResponse update(Long id, Warehouse warehouse) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
        Warehouse edited = optionalWarehouse.get();
        edited.setName(warehouse.getName());
        warehouseRepository.save(edited);
        return new ApiResponse("Edited", true);
    }

}
