package com.project.warehouse.repository;

import com.project.warehouse.entity.InputProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InputProductRepository extends JpaRepository<InputProduct, Long> {
}