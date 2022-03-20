package com.project.warehouse.controller;

import com.project.warehouse.entity.User;
import com.project.warehouse.repository.UserRepository;
import com.project.warehouse.repository.UserWarehouseRepository;
import com.project.warehouse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserWarehouseRepository userWarehouseRepository;

    @GetMapping
    public String getUserPage(Model model) {

        model.addAttribute("list", userRepository.findAll());

        return "user";
    }

    @GetMapping("/add")
    public String getSaveUser() {

        return "user-add";
    }

    @PostMapping("/add")
    public String saveUser(Model model, @ModelAttribute User user) {
        userService.add(user);
        return "redirect:/users";
    }

//    @PostMapping("/add")
//    public String saveUser(Model model, @ModelAttribute UserWarehouseDto dto) {
//        userService.add(dto);
//        return "redirect:/user";
//    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }


    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {

        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) return "Xatolik!";

        model.addAttribute("edited", userOptional.get());
        model.addAttribute("userWarehouseList", userWarehouseRepository.findAll());
        return "user-edit";
    }

//
//        @PostMapping("/edit/{id}")
//        public String editUser(@PathVariable Long id, @ModelAttribute UserDto userDto) {
//        ApiResponse response = userService.edit(id, userDto);
//        System.out.println(response);
//        return "redirect:/user";
//        }


}

