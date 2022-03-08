package com.project.warehouse.repository;

import com.project.warehouse.entity.OutputProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutputProductRepository extends JpaRepository<OutputProduct, Long> {
}