package com.project.warehouse.controller;

import com.project.warehouse.dto.UserDto;
import com.project.warehouse.dto.UserFrontDto;
import com.project.warehouse.entity.User;
import com.project.warehouse.repository.UserRepository;
import com.project.warehouse.repository.UserWarehouseRepository;
import com.project.warehouse.repository.WarehouseRepository;
import com.project.warehouse.service.AuthService;
import com.project.warehouse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users/user")
@RequiredArgsConstructor
public class UserController {

    final
    UserService userService;

    final
    UserRepository userRepository;
    final WarehouseRepository warehouseRepository;
    final
    UserWarehouseRepository userWarehouseRepository;
    final AuthService authService;

    @GetMapping
    public String getUser(Model model, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)){return "secured-page";}
        List<User> all = userRepository.findAllByActiveTrue();
        model.addAttribute("list", all);
        return "users/user";
    }

    @PostMapping("/addUser")
    public String saveUser(@ModelAttribute UserDto userDto, HttpServletRequest req, HttpServletResponse res){
        if (authService.deleteTokenIf(req, res)){return "secured-page";}
        userService.add(userDto);
        return "redirect:/users/user";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)){return "secured-page";}
        Optional<User> byId = userRepository.findById(id);
        if (byId.isEmpty()) return "error/404";
        User user = byId.get();
        user.setActive(false);
        userRepository.save(user);
        return "redirect:/users/user";
    }


    @PostMapping("/edit/{id}")
    public String editSave(@PathVariable Long id, @ModelAttribute UserDto userDto, HttpServletRequest req, HttpServletResponse res){
        if (authService.deleteTokenIf(req, res)){return "secured-page";}
        Optional<User> byId = userRepository.findById(id);
        if(byId.isEmpty() || !byId.get().getActive())return "error/404";
        userService.edit(id, userDto);
        return "redirect:/users/user";
    }

    @GetMapping("/{id}/warehouse")
    public String getUser(@PathVariable Long id, Model model){
        Optional<User> byId = userRepository.findById(id);
        if(byId.isEmpty())return "error/404";
        User user = byId.get();
        model.addAttribute("user",
                new UserFrontDto(user.getFirstName(), user.getLastName(), user.getPhoneNumber(),id));
        model.addAttribute("warehouses",warehouseRepository.findAllByActiveTrue());
        model.addAttribute("userWarehouses",
                userWarehouseRepository.findAllByUser_IdAndWarehouse_ActiveTrue(id));
        return "users/user-warehouse";
    }
    @PostMapping("/{id}/warehouse")
    public String saveWarehouses(UserDto userDto, @PathVariable Long id){
        userService.saveWarehouses(id, userDto);
        return "redirect:/users/user";
    }

}

