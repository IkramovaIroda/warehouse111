package com.project.warehouse.controller;

import com.project.warehouse.entity.User;
import com.project.warehouse.repository.UserRepository;
import com.project.warehouse.repository.UserWarehouseRepository;
import com.project.warehouse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users/user")
@RequiredArgsConstructor
public class UserController {

    final
    UserService userService;

    final
    UserRepository userRepository;

    final
    UserWarehouseRepository userWarehouseRepository;

    @GetMapping
    public String getUserPage(Model model) {

        model.addAttribute("list", userRepository.findAll());

        return "users/user";
    }

    @PostMapping("/add")
    public String saveUser(Model model, @ModelAttribute User user) {
        userService.add(user);
        return "redirect:/users/user";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/users/user";
    }

//
//        @PostMapping("/edit/{id}")
//        public String editUser(@PathVariable Long id, @ModelAttribute UserDto userDto) {
//        ApiResponse response = userService.edit(id, userDto);
//        System.out.println(response);
//        return "redirect:/user";
//        }


}

