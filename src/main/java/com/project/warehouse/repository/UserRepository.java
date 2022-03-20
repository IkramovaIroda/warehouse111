package com.project.warehouse.repository;

import com.project.warehouse.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByPhoneNumberAndPasswordAndActiveTrue(String phoneNumber, String password);
}