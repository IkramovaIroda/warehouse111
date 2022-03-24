package com.project.warehouse.repository;

import com.project.warehouse.entity.Supplier;
import com.project.warehouse.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByActiveTrue();
    User findByPhoneNumberAndPasswordAndActiveTrue(String phoneNumber, String password);
}