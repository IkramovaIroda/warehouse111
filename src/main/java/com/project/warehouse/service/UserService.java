package com.project.warehouse.service;

import com.project.warehouse.dto.ApiResponse;
import com.project.warehouse.dto.UserDto;
import com.project.warehouse.entity.User;
import com.project.warehouse.entity.Warehouse;
import com.project.warehouse.repository.UserRepository;
import com.project.warehouse.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

        final
        UserRepository userRepository;

        final
        WarehouseRepository warehouseRepository;

    public ApiResponse add(User user) {
        User save = userRepository.save(user);
        return new ApiResponse("Saved", true, save);
    }

    public ApiResponse edit(Long id, UserDto userDto) {
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(userDto.getWarehouseId());
        if (warehouseOptional.isEmpty()) return new ApiResponse("Bunday Id Topilmadi", false, warehouseOptional);
        Warehouse warehouse = warehouseOptional.get();

        User user = new User();
        user.setId(warehouse.getId());

        User save = userRepository.save(user);
        return new ApiResponse("Saved", true, save);
    }

    public ApiResponse save(Long id, UserDto userDto) {
        Optional<User> userOptional = userRepository.findById(userDto.getWarehouseId());
        if (userOptional.isEmpty()) return new ApiResponse("Saqlandi", true, userOptional);
        User user = userOptional.get();

        User user1 = new User();
        User saved = userRepository.save(user);
        return new ApiResponse("saved",true,saved);
    }
}
