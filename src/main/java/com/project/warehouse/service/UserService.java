package com.project.warehouse.service;

import com.project.warehouse.dto.ApiResponse;
import com.project.warehouse.dto.UserDto;
import com.project.warehouse.entity.User;
import com.project.warehouse.entity.UserWarehouse;
import com.project.warehouse.entity.Warehouse;
import com.project.warehouse.repository.UserRepository;
import com.project.warehouse.repository.UserWarehouseRepository;
import com.project.warehouse.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    final
    UserRepository userRepository;

    final
    WarehouseRepository warehouseRepository;
    final
    UserWarehouseRepository userWarehouseRepository;
    final
    AuthService authService;


    public ApiResponse edit(Long id, UserDto userDto) {
        Optional<User> byId1 = userRepository.findById(id);
        if(byId1.isEmpty())return null;
        User user = byId1.get();
        user.setFirstName(userDto.getFirst_name());
        user.setLastName(userDto.getLast_name());
        user.setPhoneNumber(userDto.getPhone_number());
        User save = userRepository.save(user);
        List<UserWarehouse> allByUser_id = userWarehouseRepository.findAllByUser_Id(id);
        for (UserWarehouse userWarehouse : allByUser_id) {
            List<Long> longs = userDto.getWarehouses().stream()
                    .filter(aLong -> Objects.equals(userWarehouse.getWarehouse().getId(), aLong)).toList();
            if(longs.size()==0){
                userWarehouseRepository.deleteById(userWarehouse.getId());
            }else {
                userDto.getWarehouses().remove(userWarehouse.getWarehouse().getId());
            }
        }

        for (Long warehouseId : userDto.getWarehouses()) {
            Optional<Warehouse> byId = warehouseRepository.findById(warehouseId);
            if(byId.isEmpty() || byId.get().getActive())continue;
            UserWarehouse userWarehouse=new UserWarehouse();
            userWarehouse.setUser(save);
            userWarehouse.setWarehouse(byId.get());
            userWarehouseRepository.save(userWarehouse);
        }
        return new ApiResponse("Saved", true, save);
    }

    public ApiResponse add(UserDto userDto) {
        User user=new User();
        user.setPassword(authService.encryptPassword(userDto.getPassword()));
        user.setLastName(userDto.getLast_name());
        user.setFirstName(userDto.getFirst_name());
        user.setPhoneNumber(userDto.getPhone_number());
        User saved = userRepository.save(user);
        for (Long warehouseId : userDto.getWarehouses()) {
            Optional<Warehouse> byId = warehouseRepository.findById(warehouseId);
            if(byId.isEmpty() || byId.get().getActive())continue;
            UserWarehouse userWarehouse=new UserWarehouse();
            userWarehouse.setUser(saved);
            userWarehouse.setWarehouse(byId.get());
            userWarehouseRepository.save(userWarehouse);
        }
        return new ApiResponse("saved",true,saved);
    }
}
