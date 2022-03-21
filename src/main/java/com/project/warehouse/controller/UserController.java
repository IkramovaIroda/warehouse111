package com.project.warehouse.controller;

import com.project.warehouse.dto.UserDto;
import com.project.warehouse.entity.User;
import com.project.warehouse.repository.*;
import com.project.warehouse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("data/users")
@RequiredArgsConstructor
public class UserController {


    final UserService userService;


    final UserRepository userRepository;


    final WarehouseRepository warehouseRepository;

        @GetMapping
        public String getUser(Model model) {
            List<User> all = userRepository.findAllByActiveTrue();
            model.addAttribute("list", all);
            return "data/users";
        }

        @GetMapping("/addUser")
        public String add(Model model, UserDto userDto){
            model.addAttribute("userlist",userRepository.findAllByActiveTrue());
            model.addAttribute("userdto", userDto);
            return "data/users";
        }

        @PostMapping("/addUser")
        public String saveUser(@ModelAttribute UserDto userDto){
            userService.save(userDto);
            return "redirect:data/users";
        }


        @DeleteMapping("/delete/{id}")
        public String delete(@PathVariable Long id) {
            Optional<User> byId = userRepository.findById(id);
            if (byId.isEmpty()) return "404";
            User user = byId.get();
            user.setActive(false);
            userRepository.save(user);
            return "redirect:data/users";
        }



        @GetMapping("/editProduct/{id}")
        public String edit(Model model, @PathVariable Long id){

            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isEmpty()) return "Xatolik!";

            model.addAttribute("edited", userRepository.findById(id));
            model.addAttribute("userList", userRepository.findAllByActiveTrue());
            return "data/users/user-edit";
        }

        @PostMapping("/editProduct/{id}")
        public String editSave(@PathVariable Long id, @ModelAttribute UserDto userDto){
            userService.edit(id, userDto);
            return "redirect:/data/users";
        }

    }



