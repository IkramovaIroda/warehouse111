package com.project.warehouse.repository;

import com.project.warehouse.entity.Input;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InputRepository extends JpaRepository<Input, Long> {
    List<Input> findAllByActiveTrue();
}