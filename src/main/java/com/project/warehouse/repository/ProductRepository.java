package com.project.warehouse.repository;

import com.project.warehouse.entity.Measurement;
import com.project.warehouse.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByActiveTrue();
}