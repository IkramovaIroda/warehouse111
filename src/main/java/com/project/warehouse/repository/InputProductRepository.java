package com.project.warehouse.repository;

import com.project.warehouse.entity.InputProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InputProductRepository extends JpaRepository<InputProduct, Long> {
    List<InputProduct> findAllByInput_Id(Long id);
    List<InputProduct> findAllByInput_ActiveTrueAndExpireDateBeforeAndAmountNot(LocalDate expireDate, Double amount);
    Integer countByExpireDateBeforeAndInput_ActiveTrueAndAmountNot(LocalDate expireDate, Double amount);
    List<InputProduct> findAllByInput_ActiveTrueAndInput_Warehouse_idAndInput_Currency_idAndAmountNot
            (Long input_warehouse_id, Long input_currency_id, double amount);
    List<InputProduct> findByProduct_idAndPriceAndAmount(Long product_id, Double price, Double amount);
}