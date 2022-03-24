package com.project.warehouse.controller;

import com.project.warehouse.dto.UserDto;
import com.project.warehouse.entity.User;
import com.project.warehouse.repository.*;
import com.project.warehouse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    final
    UserWarehouseRepository userWarehouseRepository;

    @GetMapping
    public String getUser(Model model) {
        List<User> all = userRepository.findAllByActiveTrue();
        model.addAttribute("list", all);
        return "users/user";
    }


    @PostMapping("/addUser")
    public String saveUser(@ModelAttribute UserDto userDto){
        userService.add(userDto);
        return "redirect:/users/user";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isEmpty()) return "error/404";
        User user = byId.get();
        user.setActive(false);
        userRepository.save(user);
        return "redirect:/users/user";
    }


    @PostMapping("/edit/{id}")
    public String editSave(@PathVariable Long id, @ModelAttribute UserDto userDto){
        Optional<User> byId = userRepository.findById(id);
        if(byId.isEmpty() || !byId.get().getActive())return "error/404";
        userService.edit(id, userDto);
        return "redirect:/users/user";
    }

}

