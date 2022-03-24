package com.project.warehouse.repository;

import com.project.warehouse.entity.UserWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserWarehouseRepository extends JpaRepository<UserWarehouse, Long> {
    List<UserWarehouse> findAllByUser_Id(Long user_id);
}