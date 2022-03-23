package com.project.warehouse.repository;

import com.project.warehouse.entity.InputProduct;
import com.project.warehouse.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InputProductRepository extends JpaRepository<InputProduct, Long> {
    List<InputProduct> findAllByInput_Id(Long id);
    void deleteAllByInput_Id(Long id);
    List<InputProduct> findAllByInput_ActiveTrueAndExpireDateBefore(LocalDate expireDate);
    Integer countByExpireDateBeforeAndInput_ActiveTrue(LocalDate expireDate);
    List<InputProduct> findByInput_Warehouse(Warehouse warehouse);
    List<InputProduct> findByInputActiveAndInput_Warehouse(boolean input_active, Warehouse input_warehouse);
    List<InputProduct> findAllByInput_ActiveTrueAndInput_Warehouse_idAndInput_Currency_id
            (Long input_warehouse_id, Long input_currency_id);
    List<InputProduct> findAllByInput_ActiveTrueAndInput_Warehouse_idAndInput_Currency_idAndAmountNot
            (Long input_warehouse_id, Long input_currency_id, double amount);
}