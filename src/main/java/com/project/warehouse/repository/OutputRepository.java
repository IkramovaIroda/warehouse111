package com.project.warehouse.repository;

import com.project.warehouse.entity.Output;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutputRepository extends JpaRepository<Output, Long> {
    List<Output> findByActiveTrue();
}