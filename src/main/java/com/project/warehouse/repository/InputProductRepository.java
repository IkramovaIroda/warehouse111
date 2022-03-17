package com.project.warehouse.repository;

import com.project.warehouse.entity.Input;
import com.project.warehouse.entity.InputProduct;
import com.project.warehouse.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InputProductRepository extends JpaRepository<InputProduct, Long> {
    List<InputProduct> findByInput(Input input);
    List<InputProduct> findByInputActive(boolean active);
    List<InputProduct> findByInput_Id(Long id);
    List<InputProduct> findAllByExpireDateBefore(LocalDate expireDate);
    Integer countByExpireDateBefore(LocalDate expireDate);
    List<InputProduct> findByInput_Warehouse(Warehouse warehouse);
    List<InputProduct> findByInputActiveAndInput_Warehouse(boolean input_active, Warehouse input_warehouse);
}