package com.project.warehouse.repository;

import com.project.warehouse.entity.Input;
import com.project.warehouse.entity.InputProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InputProductRepository extends JpaRepository<InputProduct, Long> {
    List<InputProduct> findByInput(Input input);
    List<InputProduct> findByInput_Id(Long id);
    List<InputProduct> findAllByExpireDateBefore(LocalDate expireDate);
}