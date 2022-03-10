package com.project.warehouse.repository;

import com.project.warehouse.entity.UserWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWarehouseRepository extends JpaRepository<UserWarehouse, Long> {
}