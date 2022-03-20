package com.project.warehouse.service;

import com.project.warehouse.dto.ApiResponse;
import com.project.warehouse.entity.User;
import com.project.warehouse.repository.UserRepository;
import com.project.warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

        @Autowired
        UserRepository userRepository;

        @Autowired
    WarehouseRepository warehouseRepository;

        public ApiResponse add(User user) {
            User save = userRepository.save(user);
            return new ApiResponse("Saved", true, save);
        }

//    public ApiResponse edit(Long id, UserDto userDto) {
//        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(userDto.getWarehouseId());
//        if (!warehouseOptional.isPresent()) return new ApiResponse("Akaajon bunaqa id yoq", false);
//        Warehouse warehouse = warehouseOptional.get();
//
//        User user = new User();
//        user.set(departmentDTO.getName());
//        department.setCompany(company);
//
//        Department save = departmentRepository.save(department);
//        return new ApiResponse("Saved", true, save);
//    }
}
